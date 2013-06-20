package com.example.feedbacktestlib;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import java.io.*;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GetData extends AsyncTask<Void, Integer, Void> {

    private final ProgressBar progress;
    private final RelativeLayout relativeLayout;
    private Context mainContext;
    PackageInfo manager;
    TelephonyManager tManager;
    ActivityManager activityManager;

    public GetData( final ProgressBar progress,final RelativeLayout relativeLayout ,Context cValue,PackageInfo mValue,TelephonyManager tValue,ActivityManager aValue) {
        this.progress = progress;
        this.relativeLayout = relativeLayout;
        mainContext = cValue;
        manager = mValue;
        tManager = tValue;
        activityManager = aValue;
    }

    @Override
    protected Void doInBackground(final Void... params) {

        FeedbackActivity.DeviceData.packageName = mainContext.getPackageName();

        FeedbackActivity.DeviceData.packageVersion = manager.versionName;

        Calendar c =Calendar.getInstance();

        FeedbackActivity.DeviceData.currentDate = String.valueOf(c.get(Calendar.DATE)) + "-" + String.valueOf(c.get(Calendar.MONTH)) + "-" + String.valueOf(c.get(Calendar.YEAR));

        FeedbackActivity.DeviceData.device = android.os.Build.MODEL;

        FeedbackActivity.DeviceData.sdkVersion = String.valueOf(Build.VERSION.SDK_INT);

        FeedbackActivity.DeviceData.buildRelease = Build.VERSION.RELEASE;

        FeedbackActivity.DeviceData.buildType = Build.TYPE;

        FeedbackActivity.DeviceData.buildFingerPrint = Build.FINGERPRINT;

        FeedbackActivity.DeviceData.buildId = Build.ID;

        FeedbackActivity.DeviceData.brand = Build.BRAND;

        FeedbackActivity.DeviceData.phoneType = getPhoneType(tManager.getPhoneType());

        FeedbackActivity.DeviceData.networkType = getNetworkType(tManager.getNetworkType());

        getLogs();

        FeedbackActivity.DeviceData.userId = "abcd@xyz.com";

        FeedbackActivity.DeviceData.runningApps = activityManager.getRunningAppProcesses();

        writeToFile(FeedbackActivity.systemLogFile,FeedbackActivity.DeviceData.systemLog);

        writeToFile(FeedbackActivity.eventsLogFile,FeedbackActivity.DeviceData.eventsLog);

        getRunningApps();

        populateZipFile();

        deleteFiles();

        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {
        progress.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        FeedbackActivity.state.add(FeedbackActivity.StateParameters.dataLoad);
    }

    void writeToFile(File f,String data) {
        try{
            BufferedWriter in = new BufferedWriter(new FileWriter(f));
            in.write(data);
            in.close();
        }
        catch (IOException e){
            Log.e("error",e.getMessage(),e);
        }
    }

    String getPhoneType(int phoneTypeVal) {
        switch (phoneTypeVal)
        {
            case TelephonyManager.PHONE_TYPE_NONE:
                return "None";
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM";
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA";
            default:
                return "SIP";
        }
    }

    String getNetworkType(int networkVal) {
        switch (networkVal)
        {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO rev. B";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            default:
                return "Unknown";
        }
    }

    void getLogs() {
        try {

            Process process = Runtime.getRuntime().exec("logcat -v time -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder tempSystemLog = new StringBuilder();
            String line;
            while(null != (line = bufferedReader.readLine())) {
                tempSystemLog.append(line);
                tempSystemLog.append("\n");
            }
            String tempSystemLogString = (tempSystemLog.toString());
            int ind = Math.max(0,tempSystemLogString.length()-200000);
            if(ind>0)
            {
                while(!(tempSystemLogString.charAt(ind) == '\n'))
                    ind++;
                if(tempSystemLogString.charAt(ind) == '\n' && ind != tempSystemLogString.length()-1)
                    ind++;
            }
            
        	FeedbackActivity.DeviceData.systemLog = tempSystemLogString.substring(ind);
        	
//        	FeedbackActivity.DeviceData.systemLog = "something";
        	
            Process processE = Runtime.getRuntime().exec("logcat -b events -v time -d ");
            BufferedReader bufferedReaderE = new BufferedReader(new InputStreamReader(processE.getInputStream()));
            StringBuilder tempEventsLog = new StringBuilder();
            String lineE;
            while(null != (lineE = bufferedReaderE.readLine())) {
                tempEventsLog.append(lineE);
                tempEventsLog.append("\n");
            }
            String tempEventsLogString = (tempEventsLog.toString());
            int indE = Math.max(0,tempEventsLogString.length()-100000);
            if(indE>0)
            {
                while(!(tempEventsLogString.charAt(indE) == '\n'))
                    indE++;
                if(tempEventsLog.charAt(indE) == '\n' && indE != tempEventsLogString.length()-1)
                    indE++;
            }
            FeedbackActivity.DeviceData.eventsLog = tempEventsLogString.substring(indE);

        } catch (IOException e) {
            Log.e("Logcat ", e.getMessage(), e);
            FeedbackActivity.DeviceData.systemLog = "";
            FeedbackActivity.DeviceData.eventsLog = "";
        }
    }

    void getRunningApps() {
        StringBuilder temp = new StringBuilder();
        for(ActivityManager.RunningAppProcessInfo iterator : FeedbackActivity.DeviceData.runningApps)
        {
            temp.append("\n");
            temp.append(iterator.processName);
        }
        writeToFile(FeedbackActivity.runningAppFile,temp.toString());
    }

    void populateZipFile() {
        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(FeedbackActivity.baseDir + File.separator + FeedbackActivity.zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            String[] files = {FeedbackActivity.systemLogFileName , FeedbackActivity.eventsLogFileName , FeedbackActivity.runningAppFileName};

            for(int i=0;i<files.length;i++)
            {
                ZipEntry ze = new ZipEntry(files[i]);
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(FeedbackActivity.baseDir + File.separator + files[i]);

                int len;

                while((len = in.read(buffer)) > 0) {
                    zos.write(buffer,0,len);
                }

                in.close();
            }

            zos.closeEntry();

            zos.close();

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    void deleteFiles() {
        FeedbackActivity.systemLogFile.delete();
        FeedbackActivity.eventsLogFile.delete();
        FeedbackActivity.runningAppFile.delete();
//        FeedbackActivity.screenShotFile.delete();
    }

}
