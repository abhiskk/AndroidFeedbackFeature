package com.example.feedback_test_3;

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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

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

//        try {
//            Thread.sleep(3000);
//        }   catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }

        MainActivity.DeviceData.packageName = mainContext.getPackageName();
        MainActivity.DeviceData.packageVersion = manager.versionName;
        Calendar c =Calendar.getInstance();
        MainActivity.DeviceData.currentDate = String.valueOf(c.get(Calendar.DATE)) + "-"  +
                String.valueOf(c.get(Calendar.MONTH)) + "-"
                + String.valueOf(c.get(Calendar.YEAR));

        MainActivity.DeviceData.device = android.os.Build.MODEL;

        MainActivity.DeviceData.sdkVersion = String.valueOf(Build.VERSION.SDK_INT);

        MainActivity.DeviceData.buildRelease = Build.VERSION.RELEASE;

        MainActivity.DeviceData.buildType = Build.TYPE;

        MainActivity.DeviceData.buildFingerPrint = Build.FINGERPRINT;

        MainActivity.DeviceData.buildId = Build.ID;

        MainActivity.DeviceData.brand = Build.BRAND;

        int phoneTypeVal = tManager.getPhoneType();
        switch (phoneTypeVal)
        {
            case TelephonyManager.PHONE_TYPE_NONE:
                MainActivity.DeviceData.phoneType = "None";
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                MainActivity.DeviceData.phoneType = "GSM";
                break;
            case TelephonyManager.PHONE_TYPE_CDMA:
                MainActivity.DeviceData.phoneType = "CDMA";
                break;
            default:
                MainActivity.DeviceData.phoneType = "SIP";
                break;
        }

        switch (tManager.getNetworkType())
        {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                MainActivity.DeviceData.networkType = "1xRTT";
                break;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                MainActivity.DeviceData.networkType = "CDMA";
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                MainActivity.DeviceData.networkType = "EDGE";
                break;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                MainActivity.DeviceData.networkType = "eHRPD";
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                MainActivity.DeviceData.networkType = "EVDO rev. 0";
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                MainActivity.DeviceData.networkType = "EVDO rev. A";
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                MainActivity.DeviceData.networkType = "EVDO rev. B";
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                MainActivity.DeviceData.networkType = "GPRS";
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                MainActivity.DeviceData.networkType = "HSDPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                MainActivity.DeviceData.networkType = "HSPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                MainActivity.DeviceData.networkType = "HSPA+";
                break;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                MainActivity.DeviceData.networkType = "HSUPA";
                break;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                MainActivity.DeviceData.networkType = "iDen";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                MainActivity.DeviceData.networkType = "LTE";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                MainActivity.DeviceData.networkType = "UMTS";
                break;
            case 0:
                MainActivity.DeviceData.networkType = "Unknown";
                break;
        }

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
            MainActivity.DeviceData.systemLog = tempSystemLogString.substring(ind);

            Process processE = Runtime.getRuntime().exec("logcat -b events -v time -d ");
            BufferedReader bufferedReaderE = new BufferedReader(new InputStreamReader(processE.getInputStream()));
            StringBuilder tempEventsLog = new StringBuilder();
            String lineE;
            while(null != (lineE = bufferedReaderE.readLine())) {
                tempEventsLog.append(lineE);
                tempEventsLog.append(lineE);
            }
            String tempEventsLogString = (tempEventsLog.toString());
            int indE = Math.max(0,tempEventsLogString.length()-100000);
            if(indE>0)
            {
                while(!(tempEventsLogString.charAt(indE) == '\n'))
                    indE++;
                if(tempEventsLog.charAt(ind) == '\n' && ind != tempEventsLogString.length()-1)
                    indE++;
            }
            MainActivity.DeviceData.eventsLog = tempEventsLogString.substring(indE);

        }   catch (IOException e) {
            Log.e("Logcat ", e.getMessage(), e);
            MainActivity.DeviceData.systemLog = "";
            MainActivity.DeviceData.eventsLog = "";
        }
        MainActivity.DeviceData.userId = "abcd@xyz.com";
        MainActivity.DeviceData.runningApps = activityManager.getRunningAppProcesses();
        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {
        progress.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        MainActivity.StateFlags.dataLoad = true;
    }

}






























