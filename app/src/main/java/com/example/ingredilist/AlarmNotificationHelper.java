package com.example.ingredilist;
// Helper for the Shopping Alarm class containing all the relevant methods to get and display a notification to the user when the alarm goes off. Relevant notification imports also added here.
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;


public class AlarmNotificationHelper extends ContextWrapper {
    public static final String channel1ID="channel1ID";
    public static final String channel1Name="Channel 1 Name";
    private NotificationManager mManager;
//creating the notifications channel by running the createChannels method below
    public AlarmNotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }
// creates the channel for the notification with vibration enabled so that the user is alerted by their phone when the alarm goes off.
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_HIGH);
        channel1.enableVibration(true);
        getManager().createNotificationChannel(channel1);
    }

    // getting the notification manager from the system with permissions.
    public NotificationManager getManager(){
        if (mManager == null){
            mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return mManager;
    }
// The notification that the user would receive when the alarm goes off in the getChannelNotification function below:
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setContentTitle("Ingredilist")
                .setContentText("Time To Buy Groceries!")
                .setSmallIcon(R.drawable.settingsicon);
    }
}
