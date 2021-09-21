package com.example.ingredilist;
//Timer service of setting a timer and allowing it to run in the background. (for the cooking timer activity an object of this class is required)
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class myTimerService extends Service {

    private static final String CHANNEL_ID = "NotificationChannelID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//Integer array storing the timer data so that this can later be displayed in a notification and so that the timer ends at 0
        final Integer[] timeRemaining = {intent.getIntExtra("TimeValue", 0)};
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                Intent intent1local = new Intent();
                intent1local.setAction("Timer");
                timeRemaining[0]--;
                NotificationUpdate(timeRemaining[0]);
                if (timeRemaining[0] <= 0){
                    timer.cancel();
                }
                //intent passed so that the time remaining can be used in the notification channel later too.
                intent1local.putExtra("TimeRemaining", timeRemaining[0]);
                sendBroadcast(intent1local);
                //timer set in seconds
            }
        }, 0,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void NotificationUpdate(Integer timeLeft){
        //timer is set as a notification so that the user can view the remaining time on their phone without having to check the activity.
        try {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            final Notification[] notification = {new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("My Stop Watch")
                    .setContentText("Time Remaining : " + timeLeft.toString())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .build()};
            startForeground(1, notification[0]);
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Timer Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}