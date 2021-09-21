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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ShoppingAlarmSettingsTest {
    @Rule
    public ActivityScenarioRule<ShoppingAlarmSettings>
            ShoppingAlarmSettingsTestRule =
            new ActivityScenarioRule<ShoppingAlarmSettings>(ShoppingAlarmSettings.class);

    @Test
    public void pressUpdateOnValidUserrrNameAndDietTypeInputs_DetailsUpdatedInEditTextsOnRecreate() throws Exception{


        Espresso.onView(withId(R.id.cancel_alarm)).perform(click());
        Espresso.onView(withId(R.id.current_alarm))
                .check(matches(withText("Current Alarm Cancelled")));

    }

}

