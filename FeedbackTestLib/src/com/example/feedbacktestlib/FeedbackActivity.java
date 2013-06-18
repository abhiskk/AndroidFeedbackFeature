package com.example.feedbacktestlib;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class FeedbackActivity extends Activity {

	static String baseDir,systemLogFileName,eventsLogFileName,runningAppFileName,zipFileName;

    static File systemLogFile,eventsLogFile,runningAppFile;

    public enum DeviceData {
        Instance;
        public static String packageName,packageVersion,currentDate,device,sdkVersion,buildId,buildRelease,buildType,
                buildFingerPrint,brand,phoneType,networkType,systemLog,eventsLog,userId;
        public static List<RunningAppProcessInfo> runningApps;
    }

    public enum StateParameters {
        includeSystemDataCheck,includeSnapshotCheck,systemLogCheck,sendAsAnonymous,dataLoad
    }

    public static EnumSet<StateParameters> state = EnumSet.noneOf(StateParameters.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_feedback);
		

        hideKeyboardOnStart();

        nameFiles();

        addItemsToSpinner();

        final ProgressBar progress = (ProgressBar)findViewById(R.id.progressBar);
        final RelativeLayout layoutMain = (RelativeLayout)findViewById(R.id.layoutMain);
        
        if ( savedInstanceState != null && savedInstanceState.getBoolean("dataLoad"))
            progress.setVisibility(View.GONE);
        else
        {

            layoutMain.setVisibility(View.GONE);
            try{
                PackageInfo manager = getPackageManager().getPackageInfo(getPackageName(),0);
                new GetData(progress,layoutMain,getApplicationContext(),manager,(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE),(ActivityManager)this.getSystemService(ACTIVITY_SERVICE)).execute();
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Logcat ", e.getMessage(), e);
            }
        }
		
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("dataLoad",state.contains(StateParameters.dataLoad));
    }
	
	public void systemAndSnapshotButtonClick(View v) {
		
        int id = v.getId();
		if (id == R.id.buttonSystemData) {
			CheckBox checkSystemData = (CheckBox) findViewById(R.id.checkBoxSystemData);
			checkSystemData.setChecked(!checkSystemData.isChecked());
		} else if (id == R.id.buttonSnapShot) {
			CheckBox checkSnapShot = (CheckBox) findViewById(R.id.checkBoxSnapshot);
			checkSnapShot.setChecked(!checkSnapShot.isChecked());
		}

    }
	
	public void previewButtonClick(View v) {
        Intent intent = new Intent(this,Preview.class);

        CheckBox includeSystemData = (CheckBox)findViewById(R.id.checkBoxSystemData);
        if(includeSystemData.isChecked())
            state.add(StateParameters.includeSystemDataCheck);
        else
            state.remove(StateParameters.includeSystemDataCheck);

        CheckBox includeSnapshot = (CheckBox)findViewById(R.id.checkBoxSnapshot);
        if(includeSnapshot.isChecked())
            state.add(StateParameters.includeSnapshotCheck);
        else
            state.remove(StateParameters.includeSnapshotCheck);

        startActivity(intent);
    }
	
	public void sendButtonClick(View v) {
		
		final EditText Body = (EditText) findViewById(R.id.EditText1);

        Spinner sendAsSpinner = (Spinner)findViewById(R.id.sendAsSpinner);

        if(sendAsSpinner.getSelectedItem().toString().equals("Anonymous"))
            state.add(StateParameters.sendAsAnonymous);
        else
            state.remove(StateParameters.sendAsAnonymous);

        CheckBox includeSystemDataCheckBox = (CheckBox)findViewById(R.id.checkBoxSystemData);
        if(includeSystemDataCheckBox.isChecked())
            state.add(StateParameters.includeSystemDataCheck);
        else
            state.remove(StateParameters.includeSystemDataCheck);

        displaySendingMessage();
//
        MakeMessage sendFeedback = new MakeMessage(Body.getText().toString());

        final String feedbackBody = sendFeedback.send();

        Log.e("Logcat ", "here1");

        new Thread(new Runnable() {
            public void run() {

                try{
                    FeedbackSender sender = new FeedbackSender("abhishekkadiyan@gmail.com","****");

                    if( state.contains(StateParameters.includeSystemDataCheck) )
                    {
                        sender.addAttachment( baseDir + File.separator + zipFileName , zipFileName);
                    }

                    Log.e("Logcat ", "here2");

                    sender.sendMail("Feedback",feedbackBody,"abhishekkadiyan@gmail.com","abhishekkadiyan@gmail.com");

                    Log.e("Logcat ","here7");

                    systemLogFile.delete();
                    eventsLogFile.delete();
                    runningAppFile.delete();

                    Log.e("Logcat ", "here3");

                    (new File(baseDir + File.separator + zipFileName)).delete();

                    Log.e("Logcat ", "here4");

                }
                catch(Exception e)
                {
                    Log.e("error",e.getMessage(),e);
                }
            }
        }).start();



        finish();

}
	
	public void hideKeyboardOnStart() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void nameFiles() {

        baseDir = getFilesDir().getAbsolutePath();

        systemLogFileName = "SystemLog.txt";
        systemLogFile = new File(baseDir + File.separator + systemLogFileName);

        eventsLogFileName = "EventsLog.txt";
        eventsLogFile = new File(baseDir + File.separator + eventsLogFileName);

        runningAppFileName = "RunningApps.txt";
        runningAppFile = new File(baseDir + File.separator + runningAppFileName);

        zipFileName = "ZipTest.zip";
    }
    
    public void addItemsToSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.sendAsSpinner);

        List<String> list = addFieldsToSpinnerList();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

    }

    List<String> addFieldsToSpinnerList() {
        List<String> list = new ArrayList<String>();
        list.add("abcd@xyz.com");
        list.add("Anonymous");
        return list;
    }

    public void displaySendingMessage() {
        Toast.makeText(this,"Your feedback is being sent",Toast.LENGTH_SHORT).show();
    }

}