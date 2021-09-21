package com.example.ingredilist;
// this screen is simply used to allow the user to access either the login screen or register screen by a button to access the firebase and firestore side of this application
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

public class SocialFragment extends Fragment {
    Button registerButton;
    Button loginButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        String name = sp.getString("Username","");


        registerButton = view.findViewById(R.id.registerButton);
        loginButton = view.findViewById(R.id.loginButton);
// register button when pressed takes the user to the registration screen for firebase auth and firestore.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),firebaseRegistration.class);
                startActivity(in);
            }
        });
// Login button when pressed takes the user to the login screen
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),FirebaseLogin.class);
                startActivity(in);
            }
        });





        return view;



    }
}
