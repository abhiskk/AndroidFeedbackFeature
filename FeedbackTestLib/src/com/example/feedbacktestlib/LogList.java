package com.example.feedbacktestlib;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class LogList extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_list);
//		super.setTheme(R.style.AppBaseTheme);

		Display d = getWindowManager().getDefaultDisplay();
		
		Log.e("Logcat ","lite " + d.getHeight());
		
		if(d.getHeight()>800) {
		
			WindowManager.LayoutParams params = getWindow().getAttributes();
			
			params.height = 800;
			
			this.getWindow().setAttributes(params);
		
		}
		TextView tv = (TextView)findViewById(R.id.logText);
        	if( Preview.systemLogCheck )
           		tv.setText(FeedbackActivity.DeviceData.systemLog);
        	else
            		tv.setText(FeedbackActivity.DeviceData.eventsLog);
	}

}
