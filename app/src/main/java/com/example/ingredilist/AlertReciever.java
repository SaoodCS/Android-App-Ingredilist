package com.example.ingredilist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    @Override
    //reciever for the shopping alarm notification service that notifies the user when the alarm goes off. Receiver also defined in the manifest file.
    public void onReceive(Context context, Intent intent) {
        AlarmNotificationHelper alarmNotificationHelper = new AlarmNotificationHelper(context);
        NotificationCompat.Builder nb = alarmNotificationHelper.getChannelNotification();
        alarmNotificationHelper.getManager().notify(1, nb.build());
    }
}
