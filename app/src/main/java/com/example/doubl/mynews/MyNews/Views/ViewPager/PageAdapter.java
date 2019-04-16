package com.example.doubl.mynews.MyNews.Views.ViewPager;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.doubl.mynews.MyNews.Fragments.MostPopularFragment;
import com.example.doubl.mynews.MyNews.Fragments.OtherFragment;
import com.example.doubl.mynews.MyNews.Fragments.TopStoriesFragment;

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
                return new OtherFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return (3);
    }

    /**
     * Set the fragments's page title
     * @param position current
     * @return the page title
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        OtherFragment otherFragment = new OtherFragment();
        switch (position) {
            case 0:
                return "Most Popular";
            case 1:
                return "Top Stories";
            case 2:
                return otherFragment.sectionOtherFragment;
            default:
                return null;
        }
    }
}
