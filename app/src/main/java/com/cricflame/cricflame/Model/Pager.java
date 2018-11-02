package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.tabfragments.OneFragment;
import com.cricflame.cricflame.tabfragments.TwoFragment;
import com.cricflame.cricflame.tabfragments.ThreeFragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                OneFragment tab1 = new OneFragment();
                return tab1;
            case 1:
                TwoFragment tab2 = new TwoFragment();
                return tab2;
            case 2:
                ThreeFragment tab3 = new ThreeFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}