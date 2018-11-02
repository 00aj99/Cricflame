package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Match_Info;
import com.cricflame.cricflame.Fragment.Scoreboard_Fragment;
import com.cricflame.cricflame.Fragment.Squads_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class PagerMatchDetail extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerMatchDetail(FragmentManager fm, int tabCount) {
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
                Match_Info tab1 = new Match_Info();
                return tab1;
            case 1:
                Squads_Fragment tab2 = new Squads_Fragment();
                return tab2;
            case 2:
                Scoreboard_Fragment tab3 = new Scoreboard_Fragment();
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