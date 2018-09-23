package com.iiitd.medlabs;

import java.util.ArrayList;

import com.iiitd.medlabs.backend.Contact;
import com.iiitd.medlabs.backend.DatabaseHandler;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.util.Log;
import android.view.View;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {

private ViewPager mPager;
    
    ActionBar mActionbar;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mActionbar = getSupportActionBar();
        mActionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionbar.setSelectedNavigationItem(position);
            }
        };
 
        mPager.setOnPageChangeListener(pageChangeListener);
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(fm);
        mPager.setAdapter(fragmentPagerAdapter);
        mActionbar.setDisplayShowTitleEnabled(true);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
 
            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            }
 
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                mPager.setCurrentItem(tab.getPosition());
            }
 
            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
            }
        };
 
        Tab tab = mActionbar.newTab()
            .setText("Search")
            .setTabListener(tabListener);
 
        mActionbar.addTab(tab);
 
        tab = mActionbar.newTab()
            .setText("Nearby")
            .setTabListener(tabListener);
 
        mActionbar.addTab(tab);
     
        tab = mActionbar.newTab()
                .setText("Profile")
                .setTabListener(tabListener);
        mActionbar.addTab(tab);
        
    }
}