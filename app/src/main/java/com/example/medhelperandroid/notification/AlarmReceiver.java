package com.example.medhelperandroid.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.medhelperandroid.NotificationActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationActivity.class);
        // Здесь FLAG_ACTIVITY_NEW_TASK используется для открытия Activity из контекста, который не является Activity.
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

