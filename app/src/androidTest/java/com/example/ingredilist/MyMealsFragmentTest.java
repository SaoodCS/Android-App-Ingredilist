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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ingredilist.R.id.usersNameTextView;

//ADD LOCAL MEALS ACTIVITY TESTS ALSO ADDED IN THIS CLASS.

@RunWith(AndroidJUnit4.class)
public class MyMealsFragmentTest {
    @Rule
    public ActivityScenarioRule<myMeals> myMealsTestRule =
            new ActivityScenarioRule<>(myMeals.class);



    @Test
    public void myMealsFragmentUsersNameDisplayed() {
        Espresso.onView(withId(usersNameTextView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void pressAddButton_NavigatesUserToAddLocalMealActivity() throws Exception {
        Espresso.onView(withId(R.id.addbutton)).perform(click());
        Espresso.onView(withId(R.id.IngredientsInput))
                .check(matches(isDisplayed()));
    }

    //tests for add local meals:
    @Test
    public void pressAddButtonAndAddAMeal_NavigatesUserToMyMealsFragmentOnceMealAdded() throws Exception {
        Espresso.onView(withId(R.id.addbutton)).perform(click());
        Espresso.onView(withId(R.id.IngredientsInput))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.MealNameInput)).perform(typeText("Test"));
        Espresso.onView(withId(R.id.IngredientsInput)).perform(typeText("Testing"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.addbutton)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withId(usersNameTextView)).check(matches(isDisplayed()));

    }

    @Test
    public void PressEmailButtonOnValidInputs_OpensEmailClient() throws Exception {
        Espresso.onView(withId(R.id.addbutton)).perform(click());
        Espresso.onView(withId(R.id.IngredientsInput))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.MealNameInput)).perform(typeText("Test"));
        Espresso.onView(withId(R.id.IngredientsInput)).perform(typeText("Testing"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextEmailAddress)).perform(typeText("Test@test.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.emailSubject)).perform(typeText("TestingEmailClient"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.emailButton)).perform(click());
        Thread.sleep(1500);
        Espresso.onView(withText("What App Would You Like to Email From?")).check(matches(isDisplayed()));

    }

    @Test
    public void pressBackButton_NavigatesUserToMyMealsFragment() throws Exception {
        Espresso.onView(withId(R.id.addbutton)).perform(click());
        Espresso.onView(withId(R.id.IngredientsInput))
                .check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.usersNameTextView))
                .check(matches(isDisplayed()));

    }


}
