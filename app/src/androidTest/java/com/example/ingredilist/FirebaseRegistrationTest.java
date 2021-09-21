package com.example.ingredilist;


import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ingredilist.R.id.dietTypeET;
import static com.example.ingredilist.R.id.emailET;
import static com.example.ingredilist.R.id.nameET;
import static com.example.ingredilist.R.id.passwordET;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FirebaseRegistrationTest {

    @Rule
    public ActivityScenarioRule<firebaseRegistration>
            FirebaseRegistrationTestRule =
            new ActivityScenarioRule<firebaseRegistration>(firebaseRegistration.class);

    @Test
    public void pressCreateButtonOnEmptyNameET_SetErrorMessageOnEmptyNameET() throws Exception{

        Espresso.onView(withId(R.id.passwordET)).perform(replaceText("Test123"));
        Espresso.onView(withId(R.id.emailET)).perform(typeText("Test@testing.com"));
        Espresso.onView(withId(R.id.dietTypeET)).perform(typeText("TestType"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseCreateAccButton)).perform(click());

        Espresso.onView(withId(nameET))
                .check(matches(hasErrorText("Please Enter Your Name")));

    }

    @Test
    public void pressCreateButtonOnEmptyPasswordET_SetErrorMessageOnEmptyPasswordET() throws Exception{

        Espresso.onView(withId(R.id.nameET)).perform(replaceText("Testing"));
        Espresso.onView(withId(R.id.emailET)).perform(typeText("Test@testing.com"));
        Espresso.onView(withId(R.id.dietTypeET)).perform(typeText("TestType"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseCreateAccButton)).perform(click());

        Espresso.onView(withId(passwordET))
                .check(matches(hasErrorText("Please Enter a Valid Password")));
    }

    @Test
    public void pressCreateButtonOnPasswordLessThan6Chars_SetErrorMessageOnPasswordET() throws Exception{

        Espresso.onView(withId(R.id.nameET)).perform(replaceText("Testing"));
        Espresso.onView(withId(R.id.emailET)).perform(typeText("Test@testing.com"));
        Espresso.onView(withId(R.id.dietTypeET)).perform(typeText("TestType"));
        Espresso.onView(withId(R.id.passwordET)).perform(replaceText("Test"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseCreateAccButton)).perform(click());

        Espresso.onView(withId(passwordET))
                .check(matches(hasErrorText("Please Enter a Password of 6 Characters or Longer")));
    }

    @Test
    public void pressCreateButtonOnEmptyEmailET_SetErrorMessageOnEmptyEmailET() throws Exception{

        Espresso.onView(withId(R.id.nameET)).perform(replaceText("Testing"));
        Espresso.onView(withId(R.id.passwordET)).perform(typeText("Test123"));
        Espresso.onView(withId(R.id.dietTypeET)).perform(typeText("TestType"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseCreateAccButton)).perform(click());

        Espresso.onView(withId(emailET))
                .check(matches(hasErrorText("Please Enter a Valid Email Address")));
    }

    @Test
    public void pressCreateButtonOnEmptyDietTypeET_SetErrorMessageOnEmptyDietTypeET() throws Exception{

        Espresso.onView(withId(R.id.nameET)).perform(replaceText("Testing"));
        Espresso.onView(withId(R.id.passwordET)).perform(typeText("Test123"));
        Espresso.onView(withId(emailET)).perform(typeText("Testing@testing.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseCreateAccButton)).perform(click());

        Espresso.onView(withId(dietTypeET))
                .check(matches(hasErrorText("Please Enter Your Diet Type (e.g. Vegan)")));
    }

    @Test
    public void inputCorrectValidRegistrationDetails_NavigatesToProfileSettingsActivityOnSuccess() throws Exception{
        //test user can type in eee and eee as email and password and still should work.
        Espresso.onView(withId(R.id.nameET)).perform(replaceText("Testing"));
        Espresso.onView(withId(R.id.passwordET)).perform(typeText("Test1234"));
        Espresso.onView(withId(emailET)).perform(typeText("Testing@testing.com"));
        Espresso.onView(withId(R.id.dietTypeET)).perform(typeText("TestType"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseCreateAccButton)).perform(click());
        Espresso.onView(withId(R.id.uploadNewMealBtn)).check(matches(isDisplayed()));
        //FIND OUT HOW TO DO FIRESTORE TESTING AND FIREBASE AUTH TESTING WITH ESPRESSO
    }

    @Test
    public void pressAlreadyHaveAccountBtn_NavigatesToFirebaseLoginActivity() throws Exception{
        Espresso.onView(withId(R.id.loginAccButton)).perform(click());
        Espresso.onView(withId(R.id.notRegisteredButton)).check(matches(isDisplayed()));
    }




}


