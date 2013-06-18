package com.example.feedback_test_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProcessList extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_process_list);

    Intent intent = getIntent();


    final ListView listview = (ListView) findViewById(R.id.listview);

  List<String> runningAppsList = new ArrayList<String>();

  for( ActivityManager.RunningAppProcessInfo runningAppsIterator : MainActivity.DeviceData.runningApps)
      runningAppsList.add(runningAppsIterator.processName);
    ArrayList<String> list = (ArrayList<String>) runningAppsList;
    listview.setAdapter(new ArrayAdapter<String>(this,R.layout.running_apps,list));
  }
}
