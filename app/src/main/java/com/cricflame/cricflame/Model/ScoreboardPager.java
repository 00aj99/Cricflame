package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Team1ScoreBoard_Fragment;
import com.cricflame.cricflame.Fragment.Team2ScoreBoard_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class ScoreboardPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ScoreboardPager(FragmentManager fm, int tabCount) {
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
                Team1ScoreBoard_Fragment tab1 = new Team1ScoreBoard_Fragment();
                return tab1;
            case 1:
                Team2ScoreBoard_Fragment tab2 = new Team2ScoreBoard_Fragment();
                return tab2;

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