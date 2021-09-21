package com.example.ingredilist;
// Activity displays the meal that the user pressed on and allows them to view or delete it from their meals SQLite database.
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateLocalMealsActivity extends AppCompatActivity {

    TextView mealNameInput;
    TextView ingredientsInput;
    Button delete_button;

    String id, mealName, mealIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_local_meals);

        mealNameInput = findViewById(R.id.MealNameInput2);
        ingredientsInput = findViewById(R.id.IngredientsInput2);
        delete_button = findViewById(R.id.delete_button);
        //calling the get and set local meals function and then calling the update meals function to update the meals
        getAndSetIntentData();
        // Setting the heading of the update screen as the name of the meal in the top action bar
        ActionBar topbar = getSupportActionBar();
        if(topbar !=null){
            topbar.setTitle(mealName);
        }



        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deletionAlert();
            }
        });


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("mealName") && getIntent().hasExtra("mealIngredients")){
            //Getting Locally Saved Meal Data from the intent:
            id = getIntent().getStringExtra("id");
            mealName = getIntent().getStringExtra("mealName");
            mealIngredients = getIntent().getStringExtra("mealIngredients");

            //Setting Locally Saved Meal Data to the intent:
            mealNameInput.setText(mealName);
            ingredientsInput.setText(mealIngredients);


        }else{
            Toast.makeText(this,"NO DATA", Toast.LENGTH_SHORT).show();
        }
    }

    void deletionAlert(){
        //alert dialog to confirm the user wants to delete the selected meal. on clicing yes their meal is deleted, on clicking no the dialog closes and that's it.
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Deleting: " + mealName);
        dialogBuilder.setMessage("Are you sure you want to delete: " + mealName + "?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocalMealsDBHelper localMealsDBHelper = new LocalMealsDBHelper(UpdateLocalMealsActivity.this);
                localMealsDBHelper.deleteCurrentMeal(id);
                Intent intent = new Intent(UpdateLocalMealsActivity.this, myMeals.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //Fragment frg = null;
                //frg = getSupportFragmentManager().findFragmentByTag("MyMealsFragment");
                //final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //ft.detach(frg);
                //ft.attach(frg);
                //ft.commit();
                //finish();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.create().show();
    }

}