package com.example.ingredilist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

// this screen allows users to register to firebase using firebase authentication and stores any other registration details such as name and diet type
// (which will be displayed on their created meals social feed) as well.
public class firebaseRegistration extends AppCompatActivity {
    private static final String TAG = "TAG";
    EditText nameET;
    EditText emailET;
    EditText passwordET;
    Button firebaseCreateAccButton;
    Button loginAccButton;
    EditText dietTypeET;
    private DatabaseReference dRef;
    private FirebaseAuth mAuth;
    //FOR TESTING FIREBASE:
    FirebaseFirestore db;
    String userID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_registration);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        dietTypeET = findViewById(R.id.dietTypeET);
        //initialised instances
        dRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();







// login button to take the user from this screen to the firebase login screen if they already have an account
        loginAccButton = findViewById(R.id.loginAccButton);
        loginAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firebaseRegistration.this, FirebaseLogin.class);
                startActivity(intent);
            }
        });



// create account button for creating the user account in firebase auth and firestore
        firebaseCreateAccButton = findViewById(R.id.firebaseCreateAccButton);
        firebaseCreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dietTypeString= dietTypeET.getText().toString().trim();
                String nameString = nameET.getText().toString().trim();
                String emailString = emailET.getText().toString().trim();
                String passwordString = passwordET.getText().toString().trim();
// queries to ensure the user provides the correct registration details:
                FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser!=null){
                    mAuth.signOut();
                }

                if (emailString.isEmpty()) {
                    emailET.setError("Please Enter a Valid Email Address");
                    emailET.requestFocus();
                    return;
                }

                if (passwordString.isEmpty()) {
                    passwordET.setError("Please Enter a Valid Password");
                    passwordET.requestFocus();
                    return;
                }

                if (passwordString.length() < 6) {
                    passwordET.setError("Please Enter a Password of 6 Characters or Longer");
                    passwordET.requestFocus();
                    return;
                }

                if (nameString.isEmpty()) {
                    nameET.setError("Please Enter Your Name");
                    nameET.requestFocus();
                    return;
                }

                if (dietTypeString.isEmpty()) {
                    dietTypeET.setError("Please Enter Your Diet Type (e.g. Vegan)");
                    dietTypeET.requestFocus();
                    return;
                }
                //function to register the user once they've inputted registration details correctly:
                registerUser(emailString,passwordString,nameString,dietTypeString);
            }
        });

    }

    //method to add the users details to firestore after they've been authenticated by their email and password. stored in their userID doc so it can be accessed in their profile later
    private void addUserDetailsToFirestore(String nameString, String emailString, String dietTypeString) {
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("name", nameString);
        user.put("email", emailString);
        user.put("diet", dietTypeString);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot created with ID: " + userID);
                Intent intent = new Intent(firebaseRegistration.this, FirebaseProfileSettings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }



//method to register the user to the firebase authentication ( using the createUserWithEmailAndPassword authentication method)
    private void registerUser(String emailString, String passwordString, String nameString,String dietTypeString) {

            mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        addUserDetailsToFirestore(nameString, emailString, dietTypeString);


                    } else {
                        emailET.setError("Login either invalid or taken. Please try again");
                        emailET.requestFocus();
                    }
                }
            });


        }







    //FIREBASE CONNECTION TESTS:
    public void firestoreTest(){
        Map<String, Object> user = new HashMap<>();
        user.put("first", "jerry");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


}