package com.example.feedbacktestlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.view.View;

public class Screenshot {

    public Screenshot() {
    	
    }
    
    public void takeScreenShot(View view ,String path) {
    	view.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		
		OutputStream fout = null;
		File imageFile = new File(path);
		

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
    }

}
