package com.example.doubl.mynews.Controller.Views.ViewPager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.doubl.mynews.Controller.Fragments.MostPopularFragment;
import com.example.doubl.mynews.Controller.Fragments.TechnologyFragment;
import com.example.doubl.mynews.Controller.Fragments.TopStoriesFragment;

public class PageAdapter extends FragmentPagerAdapter {


    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MostPopularFragment();
            case 1:
                return new TopStoriesFragment();
            case 2:
                return new TechnologyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Most Popular";
            case 1:
                return "Top Stories";
            case 2:
                return "Technology";
        }
    }
}
