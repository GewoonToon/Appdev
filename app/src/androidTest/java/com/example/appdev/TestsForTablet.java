package com.example.appdev;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
//Was needed to always run the add to cart test first before deleting the item
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsForTablet {

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

    //Will only work if the cart was completely empty before, didnt find a way to set this up since isDisplayed wont return a boolean
    @Test
    public void canAddProductToCart(){
        onView(withId(R.id.shop)).perform(ViewActions.click());
        onView(withId(3)).perform(ViewActions.click());
        onView(withId(R.id.frDetailAddToCart)).perform(ViewActions.click());
        onView(withId(R.id.cart)).perform(ViewActions.click());
        onView(withId(R.id.recycler_view_cart)).check(matches(atPosition(0, hasDescendant(withText("Test3")))));

    }

    //Will only work if the cart was completely empty before
    @Test
    public void canDeleteProductFromCart(){
        onView(withId(R.id.cart)).perform(ViewActions.click());
        onView(withText("X")).perform(ViewActions.click());
        onView(withText("Test3")).check(doesNotExist());
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}