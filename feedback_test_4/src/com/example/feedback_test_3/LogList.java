package com.example.feedback_test_3;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class LogList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_list);
		TextView tv = (TextView)findViewById(R.id.textview1);
        if( Preview.systemLogCheck )
            tv.setText(MainActivity.DeviceData.systemLog);
        else
            tv.setText(MainActivity.DeviceData.eventsLog);
	}

}
