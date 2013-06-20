package com.example.testfeedbacklib2;

import java.io.File;

import com.example.feedbacktestlib.FeedbackActivity;
import com.example.feedbacktestlib.Screenshot;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String screenshotFileName = "something.jpeg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            menu.add("Send feedback");
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == 0)
            {
                    snap(findViewById(R.id.something1));
            }
            return true;
    }
	
	public void snap(View v) 
	{	
		(new Screenshot()).takeScreenShot(v.getRootView(), getFilesDir().getAbsolutePath() + File.separator + screenshotFileName);
		
		Intent intent = new Intent(this,FeedbackActivity.class);
		intent.putExtra("screenShotFilePath", screenshotFileName);
		startActivity(intent);
		
	}
	
	public void change(View v)
	{
		TextView textView = (TextView)findViewById(R.id.testText);
		textView.setText("(-_-)");
	}

}
