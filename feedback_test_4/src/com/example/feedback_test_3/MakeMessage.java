package com.example.feedback_test_3;

import android.app.ActivityManager;
import android.util.Log;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MakeMessage extends MainActivity {

    private String feedbackBody;

    public MakeMessage(String feedbackBodyValue)
    {
        feedbackBody = feedbackBodyValue;
    }

    public String message() {
        StringBuilder feedbackBodyStringBuilder = new StringBuilder();
        feedbackBodyStringBuilder.append("Sent by : ");

        if(state.contains(StateParameters.sendAsAnonymous))
            feedbackBodyStringBuilder.append("Anonymous");
        else
            feedbackBodyStringBuilder.append(DeviceData.userId);

        feedbackBodyStringBuilder.append("\n\nMessage : ");
        feedbackBodyStringBuilder.append(feedbackBody);

        feedbackBodyStringBuilder.append("\n\nData:\nPackage name : " + DeviceData.packageName +"\nPackage version : "
                + DeviceData.packageVersion + "\nCurrent Date : "+DeviceData.currentDate+"\nDevice : "+DeviceData.device+"\nSdk Version : "+DeviceData.sdkVersion+
                "\nBuild Id : "+DeviceData.buildId+"\nBuild Release : "+DeviceData.buildRelease+"\nBuild Type : "+DeviceData.buildType+"\nBuild fingerprint : "+DeviceData.buildFingerPrint
                +"\nBrand : "+DeviceData.brand+"\nPhone type : "+DeviceData.phoneType+"\nNetwork Type : "+DeviceData.networkType+"\n");

        return feedbackBodyStringBuilder.toString();

    }

    void writeToFile(File f,String data)    {
        try{
            BufferedWriter in = new BufferedWriter(new FileWriter(f));
            in.write(data);
            in.close();
        }
        catch (IOException e){
            Log.e("error", e.getMessage(), e);
        }
    }

    void populateZipFile() {
        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(MainActivity.baseDir + File.separator + "zipTest1.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            String[] files = {MainActivity.systemLogFileName , MainActivity.eventsLogFileName , MainActivity.runningAppFileName};

            for(int i=0;i<files.length;i++)
            {
                ZipEntry ze = new ZipEntry(files[i]);
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(MainActivity.baseDir + File.separator + files[i]);

                int len;

                while((len = in.read(buffer)) > 0) {
                    zos.write(buffer,0,len);
                }

                in.close();
            }

            zos.closeEntry();

            zos.close();

        }   catch (IOException ex){
            ex.printStackTrace();
        }
    }

    String getRunningApps() {
        StringBuilder temp = new StringBuilder();
        for(ActivityManager.RunningAppProcessInfo iterator : MainActivity.DeviceData.runningApps)
        {
            temp.append("\n");
            temp.append(iterator.processName);
        }
        return temp.toString();
    }

}
