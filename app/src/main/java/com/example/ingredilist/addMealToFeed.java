package com.example.ingredilist;
// class is for the screen that allows a user to add a meal to the firestore database which can then be viewed and read on the feed by all ingredilist firestore users.
//parent of this activity is the firebase profile activity.
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class addMealToFeed extends AppCompatActivity {

    //shareactionprovider for actionbar (involves the shareactionprovider toolbar, menu, and theme)
    private ShareActionProvider shareActionProvider;

    private static final String TAG ="TAG";
    EditText mealNameET;
    EditText ingredientsET;
    Button uploadMealBtn;
    FirebaseFirestore db;
    String userID;
    private FirebaseAuth mAuth;
    String usersName;
    String dietType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_to_feed);

        //toolbar to replace action bar for shareactionprovider
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mealNameET = findViewById(R.id.mealNameET);
        ingredientsET = findViewById(R.id.ingredientsET);
        //getting the currently logged in users uid so that each meal they create can be tagged with their name in the firestore document
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        usersName =  getUsersNameFromFirestore();
        dietType = getDietTypeFromFirestore();

// Button to add the meal to firestore once the user has typed in the meal details:
        uploadMealBtn = findViewById(R.id.uploadMealBtn);
        uploadMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealNameString= mealNameET.getText().toString().trim();
                String ingredientsString= ingredientsET.getText().toString().trim();

                addMealDetailsToFirestore(mealNameString, ingredientsString);

            }
        });
    }

    //inflating the menu with the shareactionprovider
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        //the message that'll be sent if the user decides to use the shareactionprovider

        setShareActionItem("Need new recipe ideas for meals? Get the Ingredilist application for Android and share your meals with the community today!");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //go back to profile after uploading a meal
            case R.id.backToProfile: {
                Intent intent = new Intent(this, FirebaseProfileSettings.class);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    // method thats called if the user uses the shareactionprovider (setup for the message sent)
    private void setShareActionItem(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }



    //method for adding the meal to firestore
    private void addMealDetailsToFirestore(String mealNameString, String ingredientsString) {

        if(mealNameString.isEmpty()){
            mealNameET.setError("Please Enter a Valid Meal Name.");
            return;
        }
        if(ingredientsString.isEmpty()){
            ingredientsET.setError("Please Enter a Valid Ingredients List.");
            return;
        }
// Create a new meal
        Map<String, Object> meal = new HashMap<>();
        meal.put("usersname", usersName);
        meal.put("mealname", mealNameString);
        meal.put("ingredients", ingredientsString);
        meal.put("diettype", dietType);

// Add a new document with a generated ID to the meals collection
        db.collection("meals")
                .add(meal)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //ensuring it's been added:
                        Log.d(TAG, "Meal added with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Meal Added Successfully!", Toast.LENGTH_SHORT).show();
// intent takes you to the meals feed so you can see your uploaded meal.
                        Intent intent = new Intent(getApplicationContext(), DisplayedFirestoreCreatedMeals.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }




// method for getting the users name from firestore to store in the meals firestore collection
    private String getUsersNameFromFirestore() {
        DocumentReference dRef = db.collection("users").document(userID);
        dRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                usersName = value.getString("name");
            }
        });
        return usersName;

    }
    // method for getting the users name from firestore to store in the meals firestore collection
    private String getDietTypeFromFirestore() {
        DocumentReference dRef = db.collection("users").document(userID);
        dRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                dietType = value.getString("diet");
            }
        });
        return dietType;
    }
}