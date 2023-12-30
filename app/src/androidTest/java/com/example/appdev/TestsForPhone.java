package com.example.appdev;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsForPhone {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.appdev", appContext.getPackageName());
    }

    @Test
    public void canSeeNavigation(){
        onView(withId(R.id.shop)).check(matches(isDisplayed()));
    }

    @Test
    public void canSeeProductList(){
        onView(withId(R.id.shop)).perform(ViewActions.click());
        onView(withId(3)).check(matches(isDisplayed()));
    }

    @Test
    public void canSeeProductDetails(){
        onView(withId(R.id.shop)).perform(ViewActions.click());
        onView(withId(3)).perform(ViewActions.click());
        onView(withId(R.id.prodDetails)).check(matches(isDisplayed()));
    }

    @Test
    public void canAddProductToCartAndDeleteProduct(){
        onView(withId(R.id.shop)).perform(ViewActions.click());
        onView(withId(3)).perform(ViewActions.click());
        onView(withId(R.id.frDetailAddToCart)).perform(ViewActions.click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.cart)).perform(ViewActions.click());
        onView(withText("Test3")).check(matches(isDisplayed()));

    }

    @Test
    public void canDeleteProductFromCart(){
        onView(withId(R.id.cart)).perform(ViewActions.click());
        onView(withText("X")).perform(ViewActions.click());
        onView(withText("Test3")).check(doesNotExist());
    }
}