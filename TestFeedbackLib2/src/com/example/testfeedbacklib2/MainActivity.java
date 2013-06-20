package com.example.testfeedbacklib2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.example.feedbacktestlib.FeedbackActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

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
		Bitmap bitmap;
		View v1 = v.getRootView();
		v1.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v1.getDrawingCache());
		v1.setDrawingCacheEnabled(false);
		
		String mPath = getFilesDir().getAbsolutePath() + File.separator + "something1.jpeg";
		
		OutputStream fout = null;
		File imageFile = new File(mPath);
		

		try {
		    fout = new FileOutputStream(imageFile);
		    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
		    fout.flush();
		    fout.close();
		
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Intent intent = new Intent(this,FeedbackActivity.class);
		intent.putExtra("screenShotFilePath", "something1.jpeg");
		startActivity(intent);
		
	}
	
	public void change(View v)
	{
		TextView temp = (TextView)findViewById(R.id.temp1);
		temp.setText("nothing");
	}

}
