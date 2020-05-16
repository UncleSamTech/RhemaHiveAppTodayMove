package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveSwipeViewAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

public class RhemaHiveSwipeViewActivity extends AppCompatActivity {
    RhemaHiveSwipeViewAdapter rhemaHiveSwipeViewAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_swipe_view);
        rhemaHiveSwipeViewAdapter = RhemaHiveInstanceManagerClass.getRhemaHiveSwipeViewAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.rhema_pager);
        tabLayout = findViewById(R.id.rhema_tab);

        viewPager.setAdapter(rhemaHiveSwipeViewAdapter);
        tabLayout.setupWithViewPager(viewPager,true);


    }
}
