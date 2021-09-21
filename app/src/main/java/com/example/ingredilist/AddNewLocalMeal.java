package com.example.ingredilist;
//This class is for the screen where the user can add a new local meal to their SQLite database
import androidx.appcompat.app.AppCompatActivity;

//Basic imports to get contents from the xml layout file and to use intents to move within the application.
//Bundle import used for users name as shared preference.
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddNewLocalMeal extends AppCompatActivity {

    String loggedInUser;
    EditText mealName_input;
    EditText mealIngredients_input;
    Button addMeal_button;
    EditText editTextEmailAddress;
    EditText emailSubject;
    Button emailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_local_meal);
        Bundle bundle = getIntent().getExtras();
        loggedInUser = bundle.getString("usersName");
        mealName_input = findViewById(R.id.MealNameInput);
        mealIngredients_input = findViewById(R.id.IngredientsInput);
        addMeal_button = findViewById(R.id.addbutton);

        // add meal button adds the meal to the SQLite database using a created object of the LocalMealsDBHelper class where the addNewLocalMeal function is placed.
        addMeal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalMealsDBHelper myDB = new LocalMealsDBHelper(AddNewLocalMeal.this);
                if((mealName_input.getText().toString().trim()).isEmpty()){
                    mealName_input.setError("Please Enter a Valid Meal Name");
                    return;
                }
                if((mealIngredients_input.getText().toString().trim()).isEmpty()){
                    mealIngredients_input.setError("Please Enter Valid Ingredients");
                    return;
                }


                myDB.addNewLocalMeal(loggedInUser.toString().trim(),mealName_input.getText().toString().trim(), mealIngredients_input.getText().toString().trim());
                Intent intent = new Intent(AddNewLocalMeal.this, myMeals.class);
                startActivity(intent);
                finish();
            }
        });

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        emailSubject = findViewById(R.id.emailSubject);

        //email button runs the sendemail function listed below after this onCreate method.
        emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((editTextEmailAddress.getText().toString().trim()).isEmpty()){
                    editTextEmailAddress.setError("Please Enter a Valid Email Address");
                    return;
                }
                if((emailSubject.getText().toString().trim()).isEmpty()){
                    emailSubject.setError("Please Enter a Valid Email Subject");
                    return;
                }
                sendEmail();
            }
        });
    }

    //this function takes the email addresses and the subject line that the user inputs and, after they choose an email client
    //using an intent to take the user outside of the current application.
    //displays the recepients email addresses and subject line the user inputted and automatically generates the email message as the meal name and ingredients being sent
    private void sendEmail(){

        String[] emailAddress = new String[] {editTextEmailAddress.getText().toString()};
        String subject = emailSubject.getText().toString();
        String emailMessage = mealName_input.getText().toString() + ": " + mealIngredients_input.getText().toString();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT, emailMessage);

        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i,"What App Would You Like to Email From?"));

    }
}