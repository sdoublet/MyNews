package com.example.doubl.mynews.MyNews.Activities;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.doubl.mynews.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    /**
     * Test display of all components on main screen
     */
    @Test
    public void uiDisplayed() {
        onView(withId(R.id.drawer_layout_activity_main)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_most_popular_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()));


    }

    /**
     * Test opening and closing navigation drawer
     */
    @Test
    public void navigationDrawerOpen() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }


    @Test
    public void navigationDrawerClose() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.nav_view)).check(matches((not(isDisplayed()))));
    }

    /**
     * Test click on item in navigation drawer
     */
    @Test
    public void navigationDrawerClickItem() {

        onView(withId(R.id.drawer_layout_activity_main)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.travel));

    }

    /**
     * Test click on search button in toolbar
     */
    @Test
    public void toolbarSearchClick() {
        onView(withId(R.id.search_button)).perform(click());
        onView(withId(R.id.search_toolbar_layout)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.drawer_layout_activity_main)).check(matches(isDisplayed()));
    }

    /**
     * Test click on toolbar menu and click on item
     * Test press back to return on main screen
     */
    @Test
    public void toolbarMenuActivityMainClick() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.about)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.drawer_layout_activity_main)).check(matches(isDisplayed()));
    }

    /**
     * Test scroll and click on article in recyclerView
     */
    @Test
    public void clickOnArticle(){
        onView(withId(R.id.drawer_layout_activity_main)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_most_popular_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_most_popular_fragment)).perform(RecyclerViewActions.scrollToPosition(15));
        try {
            onView(withId(R.id.recycler_view_most_popular_fragment)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        }catch (PerformException e){
            System.out.println(""+e);
        }
    }

    /**
     * Test refresh by swipeDown
     */
    @Test
    public void refresh(){
        onView(withId(R.id.recycler_view_most_popular_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.swipe_refresh_most_popular)).perform(swipeDown());
        onView(withId(R.id.recycler_view_most_popular_fragment)).check(matches(isDisplayed()));
    }

    /**
     * Test left and right swipe on fragments
     */
    @Test
    public void swipeFragment(){
        onView(withId(R.id.drawer_layout_activity_main)).perform(swipeLeft());
        onView(withId(R.id.drawer_layout_activity_main)).perform(swipeRight());
    }

}