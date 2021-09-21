package com.example.ingredilist;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
// Settings fragment displays different buttons for the user to press and manipulate their user settings in various ways.
public class MySettingsFragment extends Fragment {
    Button userGuideBtn;
    Button alarmSettingsBtn;
    Button contactsButton;
    Button localLogoutBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mysettings, container, false);
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        String name = sp.getString("Username","");


        //Logout button logs the user out and navigates them to the login screen
        localLogoutBtn = view.findViewById(R.id.localLogoutBtn);
        localLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });

//takes the user to the ShoppingAlarmSettings fragment to set an alarm service
        alarmSettingsBtn = view.findViewById(R.id.alarmsettingsbtn);
        alarmSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),ShoppingAlarmSettings.class);
                startActivity(in);
            }
        });

        //takes the user to the contacts activity whhere they can view and manipulate the contacts content provider
        contactsButton = view.findViewById(R.id.contactsButton);
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),ContactsActivity.class);
                startActivity(in);
            }
        });
// takes the user to the userguide webview activity which walks through each screen of this application
        userGuideBtn = view.findViewById(R.id.userGuideButton);
        userGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),UserGuideActivity.class);
                startActivity(in);
            }
        });


        return view;



    }
}
