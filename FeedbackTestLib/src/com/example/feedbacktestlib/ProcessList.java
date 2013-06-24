package com.example.feedbacktestlib;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

import com.example.feedbacktestlib.FeedbackActivity.StateParameters;

import android.app.Activity;
import android.app.ActivityManager;
//import android.content.Context;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
//import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProcessList extends Activity {

	@SuppressWarnings("deprecation")
	@Override
  	protected void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		if(!FeedbackActivity.state.contains(StateParameters.tablet))
    			super.setTheme(R.style.AppBaseTheme);
    		setContentView(R.layout.activity_process_list);
//			

    		if(FeedbackActivity.state.contains(StateParameters.tablet)) {
				
				Display d = getWindowManager().getDefaultDisplay();
				
				if(d.getHeight()>800) {
				
					WindowManager.LayoutParams params = getWindow().getAttributes();
					
					params.height = 800;
					
					this.getWindow().setAttributes(params);
				
				}
    		}
			
    		final ListView listview = (ListView) findViewById(R.id.listview);

  		List<String> runningAppsList = new ArrayList<String>();

  		for( ActivityManager.RunningAppProcessInfo runningAppsIterator : FeedbackActivity.DeviceData.runningApps)
      			runningAppsList.add(runningAppsIterator.processName);
    		ArrayList<String> list = (ArrayList<String>) runningAppsList;
    		listview.setAdapter(new ArrayAdapter<String>(this,R.layout.running_apps,list));
  	}
}
