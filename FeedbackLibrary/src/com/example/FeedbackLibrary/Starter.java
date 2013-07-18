package com.example.FeedbackLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class Starter extends Activity {

    Context context;
    public static String emailAccount, emailPassword, receivingAccounts;

    public Starter(Context cValue, String eAValue, String ePValue, String rAValue) {
        context = cValue;
        emailAccount = eAValue;
        emailPassword = ePValue;
        receivingAccounts = rAValue;
    }

    public void start() {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

}
