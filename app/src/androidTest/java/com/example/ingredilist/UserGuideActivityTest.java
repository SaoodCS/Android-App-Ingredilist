package com.example.ingredilist;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class UserGuideActivityTest {
    @Rule
    public ActivityScenarioRule<UserGuideActivity>
            UserGuideActivityTestRule =
            new ActivityScenarioRule<UserGuideActivity>(UserGuideActivity.class);


    @Test
    public void UserGuideActivityOnCreate_DisplaysUserGuideInWebview() throws Exception{
        Espresso.onView(withId(R.id.webView)).check(matches(isDisplayed()));

    }
}
