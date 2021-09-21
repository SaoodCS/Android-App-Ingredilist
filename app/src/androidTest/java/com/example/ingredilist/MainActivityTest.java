package com.example.ingredilist;


import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ingredilist.R.id.userName;
import static com.example.ingredilist.R.id.userPassword;
import static com.example.ingredilist.R.id.usersNameTextView;

//SOME UI TESTS WORK ONLY WHEN LOGGED OUT OR DATA IS WIPED
// THEREFORE FOR SOME YOU MAY NEED TO WIPE DEVICE DATA BEFORE RUNNING

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> MainActivityTestRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);





    @Test
    public void pressLoginButtonOnCorrectLogin_NavigatesUserToMyMealsFragmentWithSharedPreferenceWorking() throws Exception{
        Espresso.onView(withId(R.id.userName)).perform(typeText("eee"));
        Espresso.onView(withId(R.id.userPassword)).perform(typeText("eee"));
        Espresso.closeSoftKeyboard();
        //Espresso.onView(withId(R.id.registerbtn)).perform(click());
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withId(usersNameTextView))
                .check(matches(withText("eee's Saved Meals")));
    }

    @Test
    public void pressLoginButtonOnInvalidLogin_KeepsUserOnMainActivity() throws Exception{
        Espresso.onView(withId(R.id.userName)).perform(typeText("eeeg"));
        Espresso.onView(withId(R.id.userPassword)).perform(typeText("eeeg"));
        Espresso.closeSoftKeyboard();
        //Espresso.onView(withId(R.id.registerbtn)).perform(click());
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withId(userName))
                .check(matches(isDisplayed()));
    }

    @Test
    public void pressLoginButtonOnInvalidLogin_SetsOnScreenError() throws Exception{
        Espresso.onView(withId(R.id.userName)).perform(typeText("eeeg"));
        Espresso.onView(withId(R.id.userPassword)).perform(typeText("eeeg"));
        Espresso.closeSoftKeyboard();
        //Espresso.onView(withId(R.id.registerbtn)).perform(click());
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withId(userName))
                .check(matches(hasErrorText("Incorrect Login Details")));
        Espresso.onView(withId(userPassword))
                .check(matches(hasErrorText("Incorrect Login Details")));
    }

    @Test
    public void pressRegisterButtonOnTakenRegistration_SetsOnScreenError() throws Exception{
        Espresso.onView(withId(R.id.userName)).perform(typeText("eee"));
        Espresso.onView(withId(R.id.userPassword)).perform(typeText("eee"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerbtn)).perform(click());
        Espresso.onView(withId(userName))
                .check(matches(hasErrorText("This Name and Password Already Match the Login Details in the Database. Please Press Login Instead.")));
    }

    @Test
    public void pressRegisterButtonOnValidRegistration_RegistersUserAndNaivgatesToMealsFragmentWithSharedPreference() throws Exception{
        Espresso.onView(withId(R.id.userName)).perform(typeText("NewUserTest"));
        Espresso.onView(withId(R.id.userPassword)).perform(typeText("NewUserTest"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerbtn)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withId(usersNameTextView))
                .check(matches(withText("NewUserTest's Saved Meals")));
    }




}
