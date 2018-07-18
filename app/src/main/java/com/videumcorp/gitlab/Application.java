package com.videumcorp.gitlab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.videumcorp.gitlab.widget.MyIntentService;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Offline Firebase Database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
