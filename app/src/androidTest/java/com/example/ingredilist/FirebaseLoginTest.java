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
import static com.example.ingredilist.R.id.emailET;
import static com.example.ingredilist.R.id.passwordET;
import static com.example.ingredilist.R.id.userName;
import static com.example.ingredilist.R.id.userPassword;

@RunWith(AndroidJUnit4.class)
public class FirebaseLoginTest {

    @Rule
    public ActivityScenarioRule<FirebaseLogin>
    FirebaseLoginTestRue =
            new ActivityScenarioRule<FirebaseLogin>(FirebaseLogin.class);

    @Test
    public void inputCorrectLoginAndPressLoginDetails_NavigatesToProfileSettingsActivity() throws Exception{
        //test user can type in eee and eee as email and password and still should work.
        Espresso.onView(withId(R.id.emailET)).perform(typeText("eee"));
        Espresso.onView(withId(R.id.passwordET)).perform(typeText("eee"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseLoginButton)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withId(R.id.uploadNewMealBtn)).check(matches(isDisplayed()));
    }

//ALL OTHER TESTS HERE WORK ONLY WHEN LOGGED OUT OF FIREBASE. SIMILARLY OTHER ESPRESSO TEST CLASSES WORK IN CERTAIN CONDITIONS
// THEREFORE FOR SOME YOU MAY NEED TO WIPE DEVICE DATA BEFORE RUNNING
    @Test
    public void alreadyLoggedInOnActivityStart_NavigatesToProfileSettingsActivity() throws Exception{
        Espresso.onView(withId(R.id.userEmailTV)).check(matches(isDisplayed()));

    }


    @Test
    public void pressLoginButtonOnEmptyPassword_ReturnsSetErrorMessage() throws Exception{
        Espresso.onView(withId(R.id.emailET)).perform(typeText("eee"));
        //Espresso.onView(withId(R.id.userPassword)).perform(typeText("eeeg"));
        Espresso.closeSoftKeyboard();
        //Espresso.onView(withId(R.id.registerbtn)).perform(click());
        Espresso.onView(withId(R.id.firebaseLoginButton)).perform(click());
        Espresso.onView(withId(passwordET))
                .check(matches(hasErrorText("Please Enter a Valid Password")));
    }

    @Test
    public void pressLoginButtonOnEmptyEmail_ReturnsErrorMessage() throws Exception{
        Espresso.onView(withId(passwordET)).perform(typeText("eee"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseLoginButton)).perform(click());
        Espresso.onView(withId(emailET))
                .check(matches(hasErrorText("Please Enter a Valid Email Address")));
    }

    @Test
    public void pressLoginButtonOnPasswordLessThan6Characters_ReturnsErrorMessage() throws Exception{
        Espresso.onView(withId(emailET)).perform(typeText("eee"));
        Espresso.onView(withId(R.id.passwordET)).perform(typeText("ee"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.firebaseLoginButton)).perform(click());
        Espresso.onView(withId(passwordET))
                .check(matches(hasErrorText("Please Enter a Password of 6 Characters or Longer")));
    }

    @Test
    public void pressNotRegisteredButton_NavigatesToFirebaseRegistrationActivity() throws Exception{
        Espresso.onView(withId(R.id.notRegisteredButton)).perform(click());
        Espresso.onView(withId(R.id.loginAccButton)).check(matches(isDisplayed()));
    }


}
