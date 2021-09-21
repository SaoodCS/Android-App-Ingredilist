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
public class ContactsActivityTest {

    @Rule
    public ActivityScenarioRule<ContactsActivity>
    ContactsActivityTestRule =
            new ActivityScenarioRule<ContactsActivity>(ContactsActivity.class);

    @Test
    public void insertNameHere() throws Exception{
        Espresso.onView(withId(R.id.phoneNumber)).perform(typeText("0000"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.addContactButton)).perform(click());
        Espresso.onView(withId(R.id.PersonName)).check(matches(hasErrorText("Please Input a Valid Contact Name")));
    }

    @Test
    public void insertNameHere2() throws Exception{
        Espresso.onView(withId(R.id.PersonName)).perform(typeText("Test"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.addContactButton)).perform(click());
        Espresso.onView(withId(R.id.phoneNumber)).check(matches(hasErrorText("Please Input a Valid Phone Number")));
    }

    @Test
    public void pressGetContactsButton_DisplaysContactsInTextView() throws Exception{
        Espresso.onView(withId(R.id.PersonName)).perform(typeText("Testing"));
        Espresso.onView(withId(R.id.phoneNumber)).perform(typeText("0000"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.addContactButton)).perform(click());
        Espresso.onView(withId(R.id.getContactsButton)).perform(click());
        Espresso.onView(withId(R.id.nameTextView)).check(matches(not(withText(""))));
        Espresso.onView(withId(R.id.numberTextView)).check(matches(not(withText(""))));
        //Espresso.onView(withId(R.id.nameTextView)).check(matches(withText("Testing")));
        //Espresso.onView(withId(R.id.numberTextView)).check(matches(withText("0000")));

    }


    @Test
    public void pressDeleteButton_ClearsTextViewsOfDeletedContact() throws Exception {
        Espresso.onView(withId(R.id.PersonName)).perform(typeText("Testing"));
        Espresso.onView(withId(R.id.phoneNumber)).perform(typeText("0000"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.addContactButton)).perform(click());
        Espresso.onView(withId(R.id.getContactsButton)).perform(click());
        Espresso.onView(withId(R.id.nextButton)).perform(click());
        Espresso.onView(withId(R.id.deleteButton)).perform(click());
        Espresso.onView(withId(R.id.id)).check(matches(not(withText("2"))));

    }





}
