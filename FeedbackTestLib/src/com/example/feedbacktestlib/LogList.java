package com.example.feedbacktestlib;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class LogList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_list);
		TextView tv = (TextView)findViewById(R.id.logText);
        	if( Preview.systemLogCheck )
           		tv.setText(FeedbackActivity.DeviceData.systemLog);
        	else
            		tv.setText(FeedbackActivity.DeviceData.eventsLog);
	}

}
