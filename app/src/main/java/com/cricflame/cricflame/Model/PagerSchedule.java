package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.AllTypeMatches_Fragment;
import com.cricflame.cricflame.Fragment.International_Schedule_Match_Fragment;
import com.cricflame.cricflame.Fragment.ODI_Schedule_Fragment;
import com.cricflame.cricflame.Fragment.T20_Schedule_Fragment;
import com.cricflame.cricflame.Fragment.Domestic_Schedule_Fragment;
import com.cricflame.cricflame.Fragment.Test_Schedule_Fragment;
import com.cricflame.cricflame.Fragment.Women_Schedule_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class PagerSchedule extends FragmentPagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerSchedule(FragmentManager fm, int tabCount) {
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
                AllTypeMatches_Fragment allTypeMatches_fragment = new AllTypeMatches_Fragment();
                return allTypeMatches_fragment;
            case 1:
                International_Schedule_Match_Fragment tab1 = new International_Schedule_Match_Fragment();
                return tab1;
            case 2:
                ODI_Schedule_Fragment odi_schedule_fragment = new ODI_Schedule_Fragment();
                return odi_schedule_fragment;
            case 3:
                T20_Schedule_Fragment tab2 = new T20_Schedule_Fragment();
                return tab2;
            case 4:
                Domestic_Schedule_Fragment tab3 = new Domestic_Schedule_Fragment();
                return tab3;
            case 5:
                Women_Schedule_Fragment tab4 = new Women_Schedule_Fragment();
                return tab4;
            case 6:
                Test_Schedule_Fragment test_schedule_fragment = new Test_Schedule_Fragment();
                return test_schedule_fragment;
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