package com.example.ingredilist;
//activity containing the bottom navigation car for all the fragments associated with the local side of this application.
//switch cases used to switch between fragments in the navigation bar
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class myMeals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meals);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyMealsFragment()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment currentFragment=null;
            switch(item.getItemId())
            {
                case R.id.myMeals:
                    currentFragment= new MyMealsFragment();
                    break;
                case R.id.socials:
                    currentFragment= new SocialFragment();
                    break;
                case R.id.shoppingList:
                    currentFragment= new ShoppingListFragment();
                    break;
                case R.id.cookingTimer:
                    currentFragment= new CookingTimerFragment();
                    break;
                case R.id.mySettings:
                    currentFragment= new MySettingsFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,currentFragment).commit();
            return true;
        }
    };
}