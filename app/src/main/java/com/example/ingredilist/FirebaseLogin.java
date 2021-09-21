package com.example.ingredilist;
//firebase login screen used to log into firebase using authentication
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseLogin extends AppCompatActivity {
    Button notRegisteredButton;

    EditText emailET;
    EditText passwordET;
    Button firebaseLoginButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        firebaseLoginButton = findViewById(R.id.firebaseLoginButton);

        //takes the user to the registration screen when pressed
        notRegisteredButton = findViewById(R.id.notRegisteredButton);
        notRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirebaseLogin.this, firebaseRegistration.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        //when pressed logs the user into their account using the userLogin method displayed below
        mAuth = FirebaseAuth.getInstance();
        firebaseLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    // if the user is already logged in when this screen begins then they are taken directly to their profile settings activity instead of typing their login details in again.
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = mAuth.getCurrentUser();
            if(mUser==null){
                return;
            }
            else{
                Intent intent = new Intent(FirebaseLogin.this, FirebaseProfileSettings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

// method checks to see if the login details match that in the firebase authentication and if it does it signs them on with the authentication instance. if details are incorrect an error message is toasted.
    private void userLogin() {
        String emailString = emailET.getText().toString().trim();
        String passwordString = passwordET.getText().toString().trim();

        if(emailString.isEmpty()){
            emailET.setError("Please Enter a Valid Email Address");
            emailET.requestFocus();
            return;
        }

        if(passwordString.isEmpty()){
            passwordET.setError("Please Enter a Valid Password");
            passwordET.requestFocus();
            return;
        }

        //authentication account for the test user:
        if(emailString.equals("eee") ){
            emailString = "eee@eee.com";
        }
        if(passwordString.equals("eee")){
            passwordString = "eeeeee";
        }


        if(passwordString.length()<6){
            passwordET.setError("Please Enter a Password of 6 Characters or Longer");
            passwordET.requestFocus();
            return;
        }


//method to sign into account
        mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Intent intent = new Intent(FirebaseLogin.this, FirebaseProfileSettings.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);

               }
               else{
                   Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}
