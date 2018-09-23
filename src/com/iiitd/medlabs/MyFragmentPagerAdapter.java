package com.iiitd.medlabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	 
    final int PAGE_COUNT = 3;
 
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int arg0) {
        Bundle data = new Bundle();
        switch(arg0){
 
            /** tab1 is selected */
            case 0:
                SearchFragment searchFragmentObj = new SearchFragment();
                return searchFragmentObj;
 
            /** tab2 is selected */
            case 1:
                NearbyFragment nearbyFragmentObj = new NearbyFragment();
                return nearbyFragmentObj;
                
            case 2:
                ProfileFragment profileFragmentObj = new ProfileFragment();
                return profileFragmentObj;
        }
        return null;
    }
 
    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}