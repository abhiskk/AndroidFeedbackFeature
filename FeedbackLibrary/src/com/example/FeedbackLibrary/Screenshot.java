package com.example.FeedbackLibrary;

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

    public void takeScreenShot(View view, String basePath) {

        String path = basePath + File.separator + "FeedbackLibScreenshot.jpeg";
        String backupPath = basePath + File.separator + "FeedbackLibScreenshotBackup.jpeg";

        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        OutputStream fout;
        File imageFile = new File(path);

        OutputStream foutBackup;
        File backupFile = new File(backupPath);

        try {
            fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
            fout.flush();
            fout.close();

            foutBackup = new FileOutputStream(backupFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, foutBackup);
            foutBackup.flush();
            foutBackup.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
