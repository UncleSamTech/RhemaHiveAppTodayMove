package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class RhemaHiveMessageTabLayAdapter extends FragmentPagerAdapter {

    static final int NUM_ITEMS = 4;
    ViewPager rhemPager;
    public RhemaHiveMessageTabLayAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RhemaHiveMessageTabListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
