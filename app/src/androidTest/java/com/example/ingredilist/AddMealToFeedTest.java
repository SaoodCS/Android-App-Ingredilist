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
import static com.example.ingredilist.R.id.ingredientsET;
import static com.example.ingredilist.R.id.mealNameET;
import static com.example.ingredilist.R.id.testingTV;
import static com.example.ingredilist.R.id.userName;
import static com.example.ingredilist.R.id.usersNameTextView;

@RunWith(AndroidJUnit4.class)
public class AddMealToFeedTest {

    @Rule
    public ActivityScenarioRule<addMealToFeed> addMealsToFeedTestRule =
            new ActivityScenarioRule<>(addMealToFeed.class);

    @Test
    public void checkIfMealNameInputtedIsDisplayedOnScreen() throws Exception{
        Espresso.onView(withId(mealNameET))
                .check(matches(isDisplayed()));
    }

    @Test
    public void pressUploadButtonOnEmptyMealNameET_SetsMealNameETError() throws Exception {
        Espresso.onView(withId(ingredientsET)).perform(typeText("Test"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.uploadMealBtn)).perform(click());
        Espresso.onView(withId(mealNameET))
                .check(matches(hasErrorText("Please Enter a Valid Meal Name.")));

    }

    @Test
    public void pressUploadButtonOnEmptyIngredientsET_SetsIngredientsETError() throws Exception {
        Espresso.onView(withId(mealNameET)).perform(typeText("Test"));
        // Espresso.onView(withId(R.id.userPassword)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.uploadMealBtn)).perform(click());
        Espresso.onView(withId(ingredientsET))
                .check(matches(hasErrorText("Please Enter a Valid Ingredients List.")));

    }

    @Test
    public void pressUploadButtonOnValidMealNameAndIngredients_NavigatesUserToMealsFeed() throws Exception {
        Espresso.onView(withId(ingredientsET)).perform(typeText("Test"));
        Espresso.onView(withId(mealNameET)).perform(typeText("Test"));
        // Espresso.onView(withId(R.id.userPassword)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.uploadMealBtn)).perform(click());
        Espresso.onView(withId(testingTV))
                .check(matches(isDisplayed()));

    }


}
