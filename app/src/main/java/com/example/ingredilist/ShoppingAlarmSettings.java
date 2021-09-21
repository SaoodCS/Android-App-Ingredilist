package com.example.ingredilist;

// class is for shopping alarm settings to allow the user to set a shopping alarm service and start it.
//This class opens a time picker which allows the user to select a time to be alarmed to go shopping.

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class ShoppingAlarmSettings extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView currentAlarm1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_alarm_settings);
        currentAlarm1 = findViewById(R.id.current_alarm);

        Button setAlarmButton = (Button) findViewById(R.id.alarm_setter);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment shoppingTimePicker = new ShoppingTimePickerFragment();
                shoppingTimePicker.show(getSupportFragmentManager(), "alarm setter");
            }
        });

        Button cancelAlarmButton = (Button) findViewById(R.id.cancel_alarm);
        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCurrentAlarm();
            }
        });

    }

//setting up the timepicker calender
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND,0);
        updateTimeTextView(c);
        startAlarmManager(c);

    }
//display the time the user chose in the textview of the activity
    private void updateTimeTextView(Calendar c){
        String AlarmSetterPreface = "Current Shopping Time Alarm Set For: ";
        AlarmSetterPreface += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        currentAlarm1.setText(AlarmSetterPreface);
    }

//method to turn the alarm on, unless the user has chosen a time and date in the past.
    private void startAlarmManager(Calendar c) {
        if(c.before(Calendar.getInstance())){
            Toast.makeText(this,"INVALID ALARM SET IN PAST", Toast.LENGTH_SHORT).show();
            currentAlarm1.setText("The Set Alarm Time is Invalid");
        }
        else{
            AlarmManager shoppingAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            shoppingAlarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

//method to delete te current alarm that the user has set and inform them in a textview that it has been cancelled.
    private void deleteCurrentAlarm(){
        AlarmManager shoppingAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        shoppingAlarmManager.cancel(pendingIntent);
        currentAlarm1.setText("Current Alarm Cancelled");
    }

}