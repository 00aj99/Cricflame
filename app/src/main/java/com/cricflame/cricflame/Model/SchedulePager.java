package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.PointsTable_Fragment;
import com.cricflame.cricflame.Fragment.Schedule_Venue;
import com.cricflame.cricflame.Fragment.International_Schedule_Fragment;
import com.cricflame.cricflame.Fragment.SquadFragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class SchedulePager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public SchedulePager(FragmentManager fm, int tabCount) {
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
                International_Schedule_Fragment tab1 = new International_Schedule_Fragment();
                return tab1;
            case 1:
               /* Schedule_Venue tab2 = new Schedule_Venue();
                return tab2;*/
                SquadFragment squadFragment = new SquadFragment();
                return squadFragment;
            case 2:
               /* Schedule_Venue tab2 = new Schedule_Venue();
                return tab2;*/
            PointsTable_Fragment pointsTable_fragment = new PointsTable_Fragment();
                return pointsTable_fragment;
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