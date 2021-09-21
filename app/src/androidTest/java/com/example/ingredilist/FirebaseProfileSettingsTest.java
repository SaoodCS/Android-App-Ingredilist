package com.example.ingredilist;


import android.app.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ingredilist.R.id.passwordET;
import static com.example.ingredilist.R.id.scrollIndicatorDown;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class FirebaseProfileSettingsTest {
    @Rule
    public ActivityScenarioRule<FirebaseProfileSettings>
    FirebaseProfileSettingsTestRule =
            new ActivityScenarioRule<FirebaseProfileSettings>(FirebaseProfileSettings.class);

    //test to see if users details have updated by: changing them, pressing update, recreating the activity
    @Test
   public void pressUpdateOnValidUserNameAndDietTypeInputs_DetailsUpdatedInEditTextsOnRecreate() throws Exception{

       Espresso.onView(withId(R.id.usersNameET)).perform(replaceText("eee"));
       Espresso.onView(withId(R.id.usersDietTypeET)).perform(replaceText("Test"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.updateButton)).perform(click());
        FirebaseProfileSettingsTestRule.getScenario().recreate();
        Espresso.onView(withId(R.id.usersNameET))
                .check(matches(withText("eee")));
        Espresso.onView(withId(R.id.usersDietTypeET))
                .check(matches(withText("Test")));

   }

    @Test
    public void pressCloseMenuOptionInScrollView_ClosesScrollViewMenuAndReformatsScreenItems() throws Exception{
        Espresso.onView(withId(R.id.fbMenuIcon2)).perform(click());
        Espresso.onView(withId(R.id.closeMenuTVLink2)).perform(scrollTo(),click());
        //Espresso.onView(withId(R.id.closeMenuTVLink2)).perform(click());
        Espresso.onView(withId(R.id.scrollnavigation2)).check(matches(not(isDisplayed())));
    }

    @Test
    public void pressMealFeedOptionInScrollView_NavigatesToMealsFeedScreen() throws Exception{
        Espresso.onView(withId(R.id.fbMenuIcon2)).perform(click());
        Espresso.onView(withId(R.id.profileFeedTVLink2)).perform(scrollTo(),click());
        //Espresso.onView(withId(R.id.closeMenuTVLink2)).perform(click());
        Espresso.onView(withId(R.id.recyclerViewFeed)).check(matches(isDisplayed()));

    }

    @Test
    public void pressBackToLocalOptionInScrollView_NavigatesToMyMealsLocalFragment() throws Exception{
        Espresso.onView(withId(R.id.fbMenuIcon2)).perform(click());
        Espresso.onView(withId(R.id.backToLocalTVLink2)).perform(scrollTo(),click());
        //Espresso.onView(withId(R.id.closeMenuTVLink2)).perform(click());
        Espresso.onView(withId(R.id.usersNameTextView)).check(matches(isDisplayed()));

    }

    @Test
    public void pressLogoutOptionInScrollView_LogsOutUserAndNavigatesThemBackToLoginActivity() throws Exception{
        Espresso.onView(withId(R.id.fbMenuIcon2)).perform(click());
        Espresso.onView(withId(R.id.LogoutTVLink2)).perform(scrollTo(),click());
        Espresso.onView(withId(R.id.firebaseLoginButton)).check(matches(isDisplayed()));

    }

    @Test
    public void pressUploadNewMealButton_NavigatesUserToUploadNewMealScreen() throws Exception{
        Espresso.onView(withId(R.id.uploadNewMealBtn)).perform(click());
        Espresso.onView(withId(R.id.uploadMealBtn)).check(matches(isDisplayed()));


    }


}
