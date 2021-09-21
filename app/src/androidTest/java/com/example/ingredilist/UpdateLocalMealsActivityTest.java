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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ingredilist.R.id.dialog_button;
import static com.example.ingredilist.R.id.usersNameTextView;


@RunWith(AndroidJUnit4.class)
public class UpdateLocalMealsActivityTest {
    @Rule
    public ActivityScenarioRule<UpdateLocalMealsActivity>
            UpdateLocalMealsActivityTestRule =
            new ActivityScenarioRule<UpdateLocalMealsActivity>(UpdateLocalMealsActivity.class);




    @Test
    public void pressDeleteButtonOnSelectedMeal_AlertDialogAppearsForConfirmationOfDeletion() throws Exception{
        Espresso.onView(withId(R.id.delete_button)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withText("Yes")).check(matches(isDisplayed()));
    }

    @Test
    public void pressDeleteButtssonOnSelectedMeal_PressYesOnAlertDiaglog() throws Exception{
        Espresso.onView(withId(R.id.delete_button)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withText("Yes")).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withId(R.id.usersNameTextView))
                .check(matches(isDisplayed()));
    }


    @Test
    public void pressDeleteButtssonOnSelectedMeal_PressNoOnAlertDiaglog() throws Exception{
        Espresso.onView(withId(R.id.delete_button)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withText("No")).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withId(R.id.delete_button))
                .check(matches(isDisplayed()));
    }



}
