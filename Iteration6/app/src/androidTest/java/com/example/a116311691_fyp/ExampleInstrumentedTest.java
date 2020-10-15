package com.example.a116311691_fyp;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.a116311691_fyp", appContext.getPackageName());
    }

    //IS4448
    //Lab - mytipcalculator app
    //start
    @Rule
    public ActivityTestRule<RegisterApplicant> mActivityTestRule = new ActivityTestRule<>(RegisterApplicant.class);
    @Test
    public void simpleTest() {
        ViewInteraction editText = onView(
                withId(R.id.etUsername));
        editText.perform(scrollTo(), click());

        ViewInteraction editText2 = onView(
                withId(R.id.etUsername));
        editText2.perform(scrollTo(), replaceText("cloLynch"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                withId(R.id.etPassword));
        editText3.perform(scrollTo(), replaceText("pass"), closeSoftKeyboard());


        ViewInteraction editText4 = onView(
                withId(R.id.etFName));
        editText4.perform(scrollTo(), replaceText("Clodagh"), closeSoftKeyboard());

        ViewInteraction editText5 = onView(
                withId(R.id.etLName));
        editText5.perform(scrollTo(), replaceText("Lynch"), closeSoftKeyboard());

        ViewInteraction editText6 = onView(
                withId(R.id.etAge));
        editText6.perform(scrollTo(), replaceText("53"), closeSoftKeyboard());

        ViewInteraction editText7 = onView(
                withId(R.id.etAddress));
        editText7.perform(scrollTo(), replaceText("Model Farm Road"), closeSoftKeyboard());

        ViewInteraction editText8 = onView(
                withId(R.id.etEmail));
        editText8.perform(scrollTo(), replaceText("cloLynch@gmail.com"), closeSoftKeyboard());

        ViewInteraction editText9 = onView(
                withId(R.id.etPhoneNo));
        editText9.perform(scrollTo(), replaceText("0862172862"), closeSoftKeyboard());

        ViewInteraction spinner1 = onView((withId(R.id.spBreakReason)));
        spinner1.perform(scrollTo(), click());
        onData(hasToString("Children")).perform(click());

        ViewInteraction editText11 = onView(
                withId(R.id.etRolePref));
        editText11.perform(scrollTo(), replaceText("Nurse"), closeSoftKeyboard());

        ViewInteraction spinner2 = onView((withId(R.id.spLocation)));
        spinner2.perform(scrollTo(), click());
        onData(hasToString("Cork")).perform(click());

        ViewInteraction spinner3 = onView((withId(R.id.spIndustry)));
        spinner3.perform(scrollTo(), click());
        onData(hasToString("Hospitality")).perform(click());


        ViewInteraction button = onView(withId(R.id.btnRegister));
        button.perform(scrollTo(), click());

        /*
        ViewInteraction textView = onView(
                allOf(withId(R.id.total_to_pay_result), withText("56"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scroll_content),
                                        0),
                                7),
                        isDisplayed()));
        textView.check(matches(withText("56")));


         */
    }
    //end


}
