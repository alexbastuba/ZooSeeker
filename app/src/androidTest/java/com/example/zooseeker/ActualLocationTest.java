package com.example.zooseeker;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActualLocationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void actualLocationTest() {
        ViewInteraction materialCheckBox = onView(
                allOf(withId(R.id.node_checkBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_exhibitsRecyclerView),
                                        3),
                                0),
                        isDisplayed()));
        materialCheckBox.perform(click());

        ViewInteraction materialCheckBox2 = onView(
                allOf(withId(R.id.node_checkBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_exhibitsRecyclerView),
                                        6),
                                0),
                        isDisplayed()));
        materialCheckBox2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.main_directionsButton), withText("Get Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.go_btn), withText("GO"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.direction_mockButton), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.direction_mockLatitude),
                        childAtPosition(
                                allOf(withId(R.id.direction_linearLayout_mockLocation),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("32.74112858"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.direction_mockLongitude),
                        childAtPosition(
                                allOf(withId(R.id.direction_linearLayout_mockLocation),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("-117.16274043"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.direction_mockLocation), withText("Mock Location"),
                        childAtPosition(
                                allOf(withId(R.id.direction_linearLayout_mockLocation),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.direction_nextButton), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.direction_previousButton), withText("Prev"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.direction_exitButton), withText("Exit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton9.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}