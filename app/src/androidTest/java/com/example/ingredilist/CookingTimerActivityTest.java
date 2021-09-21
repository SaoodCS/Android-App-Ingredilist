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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CookingTimerActivityTest {

    @Rule
    public ActivityScenarioRule<CookingTimerActivity>
    CookingTimerActivityTestRule =
            new ActivityScenarioRule<CookingTimerActivity>(CookingTimerActivity.class);

    @Test
    public void pressBeginButtonWithoutTimeInput_ReturnsSetError()throws Exception{
        Espresso.onView(withId(R.id.beginButton)).perform(click());
        Espresso.onView(withId(R.id.SetTimer)).check(matches(hasErrorText("Please Enter a Valid Time in Seconds")));
    }



}
