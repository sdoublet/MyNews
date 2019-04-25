package com.example.doubl.mynews.MyNews.Activities;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.DatePicker;


import com.example.doubl.mynews.R;

import static android.support.test.espresso.Espresso.onView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;


import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;


public class SearchToolbarActivityTest {
@Rule
    public ActivityTestRule<SearchToolbarActivity>activityActivityTestRule=  new ActivityTestRule<>(SearchToolbarActivity.class);

@Test
    public void searchButtonVisible(){
    onView(withId(R.id.checkbox_arts)).perform(click());
    onView(withId(R.id.search_articles_button)).check(matches(isDisplayed()));
}

@Test
    public  void searchButtonInvisible(){
    onView(withId(R.id.checkbox_arts)).perform(click());
    onView(withId(R.id.checkbox_business)).perform(click());
    onView(withId(R.id.search_articles_button)).check(matches(isDisplayed()));
    onView(withId(R.id.checkbox_arts)).perform(click());
    onView(withId(R.id.checkbox_business)).perform(click());
    onView(withId(R.id.search_articles_button)).check(matches(not(isDisplayed())));
}
@Test
    public void datePicker(){
    onView(withId(R.id.begin_date)).perform(click());
    onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
    onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1974,10,8));

}

}