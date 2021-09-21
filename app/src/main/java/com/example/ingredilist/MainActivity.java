package com.example.ingredilist;
// main activity is a basic login and registration screen. User can either register or login with an email address and password
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ShareActionProvider shareActionProvider;

    private EditText Username;
    private EditText Password;
    private Button Login;
    SharedPreferences sp;
    String userString;
    DBHelper DB;
    Button registrationButton;
// network broadcast event receiver informing the user of whether network functionality is available in the application or not
// depending on their device's wireless connection status.
    NetworkBCReceiver networkBCReceiver = new NetworkBCReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText)findViewById(R.id.userName);
        Password = (EditText)findViewById(R.id.userPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        DB = new DBHelper(this);

        registrationButton = findViewById(R.id.registerbtn);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Boolean insert = DB.insertData(Username.getText().toString().trim(),Password.getText().toString().trim());

                //Boolean checkuserpass = DB.checkUsernameAndPassword(UserName, UserPassword);
                validateRegistration(Username.getText().toString(), Password.getText().toString());




            }
        });




        DB.insertData("eee","eee");

        sp=getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userString = Username.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Username",userString);
                editor.commit();

                validate(Username.getText().toString(), Password.getText().toString());
            }
        });

    }










// method for ensuring the user isn't registering with details that have already been registered.
    private void validateRegistration(String UserName, String UserPassword) {
        Boolean checkuserpass = DB.checkUsernameAndPassword(UserName, UserPassword);
        if (checkuserpass == true){
            Username.setError("This Name and Password Already Match the Login Details in the Database. Please Press Login Instead.");
            return;
        }
        if (checkuserpass != true){
            Toast.makeText(getApplicationContext(), "User Registered Successfully, Now Login", Toast.LENGTH_SHORT).show();
            DB.insertData(Username.getText().toString().trim(),Password.getText().toString().trim());
        }
    }
// method for validating the users inputted login details against that in the SQLite Database.
    private void validate (String UserName, String UserPassword) {
        Boolean checkuserpass = DB.checkUsernameAndPassword(UserName, UserPassword);
        if (checkuserpass == true) {
            Intent intent = new Intent(MainActivity.this, myMeals.class);
            startActivity(intent);
            finish();
        } else {
            Username.setError("Incorrect Login Details");
            Password.setError("Incorrect Login Details");
        }
    }

    //on activity start if their is a network connection the user will receive a toast message and if their is not they'll receive an alert dialog
    //see Network BC Receiver for more details.
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkBCReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkBCReceiver);
    }


}


