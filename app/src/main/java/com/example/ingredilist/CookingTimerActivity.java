package com.example.ingredilist;
// This activity is for setting the cooking timer which runs even while the acitivty is closed to prevent cooking accidents. The remaining time is also shown in the notifications.
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import static android.Manifest.permission.FOREGROUND_SERVICE;




public class CookingTimerActivity extends AppCompatActivity {

    private TextView currentTimer;
    private EditText setTimer;
    private Button beginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_timer);
        //requesting permission to run the timer as a foreground service that wont end on activity close or app end.
        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
        currentTimer = findViewById(R.id.currentTimer);
        setTimer = findViewById(R.id.SetTimer);
        beginButton = findViewById(R.id.beginButton);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Timer");
        //notification updating the user on the current time remaining if they open their notifications.
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
                currentTimer.setText(integerTime.toString());
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    //start button to begin the timer based on the time set by the user.
    //note that this activity ends on the first instance of pressing the start button, however only on the first instance does it end and the application itself still stays running. Thus on any attempt after the first it'll run fine.
    public void startButton(View view){
        if((setTimer.getText().toString().trim()).isEmpty()){
            setTimer.setError("Please Enter a Valid Time in Seconds");
            return;
        }
        Intent intentService = new Intent(this, myTimerService.class);
        Integer integerTimeSet = Integer.parseInt(setTimer.getText().toString());
        intentService.putExtra("TimeValue", integerTimeSet);
        startService(intentService);
    }



}


