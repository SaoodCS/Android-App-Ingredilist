package com.example.ingredilist;
// this screen allows the user to view the profile they registered within firebase and edit the details of their profile that was stored in a document in firestore.
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//all the relevant firebase imports so the code runs correctly
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
//Hashmap for adding a document with data to a firestore collection
import java.util.HashMap;
import java.util.Map;

public class FirebaseProfileSettings extends AppCompatActivity {

    private static final String TAG = "TAG";

    //menu variables for accessing the social feed of meals, logging out of your account, going back to the locals fragments, and to close the menu opened from clicking the menu icon.
    ImageView fbMenuIcon;
    ScrollView scrollnavigation;
    TextView profileFeedTVLink;
    TextView LogoutTVLink;
    TextView backToLocalTVLink;
    TextView closeMenuTVLink;
    private FirebaseAuth mAuth;
    TextView userFeedTV;
    ImageView logoImageView;


    TextView userEmailTV;
    EditText usersNameET;
    EditText usersDietTypeET;
    Button updateButton;
    Button uploadNewMealBtn;
    FirebaseFirestore db;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_profile_settings);

        profileFeedTVLink = findViewById(R.id.profileFeedTVLink2);
        LogoutTVLink = findViewById(R.id.LogoutTVLink2);
        backToLocalTVLink = findViewById(R.id.backToLocalTVLink2);
        closeMenuTVLink = findViewById(R.id.closeMenuTVLink2);
        userFeedTV = findViewById(R.id.userFeedTV);
        logoImageView = findViewById(R.id.logoImageView);
        userEmailTV = findViewById(R.id.userEmailTV);
        usersNameET = findViewById(R.id.usersNameET);
        usersDietTypeET = findViewById(R.id.usersDietTypeET);
        updateButton = findViewById(R.id.updateButton);

        // takes the user to the upload a new meal to firestore screen
        uploadNewMealBtn = findViewById(R.id.uploadNewMealBtn);
        uploadNewMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirebaseProfileSettings.this, addMealToFeed.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


// instances needed for the user to update their firestore details saved:
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        getUserDataFromFirestore();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFirestoreProfileData();
            }
        });



        fbMenuIcon = findViewById(R.id.fbMenuIcon2);
        scrollnavigation = findViewById(R.id.scrollnavigation2);
        scrollnavigation.setVisibility(View.INVISIBLE);
        fbMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollnavigation.setVisibility(View.VISIBLE);
                fbMenuIcon.setVisibility(View.INVISIBLE);
                userFeedTV.setVisibility(View.INVISIBLE);
                logoImageView.setVisibility(View.INVISIBLE);
            }
        });
// the following are all links to navigate around the application
        profileFeedTVLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirebaseProfileSettings.this, DisplayedFirestoreCreatedMeals.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        backToLocalTVLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirebaseProfileSettings.this, myMeals.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        closeMenuTVLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollnavigation.setVisibility(View.INVISIBLE);
                fbMenuIcon.setVisibility(View.VISIBLE);
                userFeedTV.setVisibility(View.VISIBLE);
                logoImageView.setVisibility(View.VISIBLE);
            }
        });

        LogoutTVLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(FirebaseProfileSettings.this, FirebaseLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }
//method to update the firestore current profile data using the instance of the user currently logged in.
    private void updateFirestoreProfileData() {
        String authenticatedEmail = userEmailTV.getText().toString().trim();
        String updatedName = usersNameET.getText().toString().trim();
        String updatedDiet = usersDietTypeET.getText().toString().trim();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("name", updatedName);
        user.put("email", authenticatedEmail);
        user.put("diet", updatedDiet);
        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }


// method to get the current logged in users data from firestore and display it on the screen
    private void getUserDataFromFirestore() {
        DocumentReference dRef = db.collection("users").document(userID);
        dRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userEmailTV.setText(value.getString("email"));
                usersNameET.setText(value.getString("name"));
                usersDietTypeET.setText(value.getString("diet"));

            }
        });
    }
}