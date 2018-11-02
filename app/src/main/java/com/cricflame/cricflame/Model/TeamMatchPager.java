package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Team_Match_Fragment;
import com.cricflame.cricflame.Fragment.Team_News_Fragment;
import com.cricflame.cricflame.Fragment.Team_Venue_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class TeamMatchPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public TeamMatchPager(FragmentManager fm, int tabCount) {
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
                Team_Match_Fragment tab1 = new Team_Match_Fragment();
                return tab1;
            case 1:
                Team_Venue_Fragment tab2 = new Team_Venue_Fragment();
                return tab2;
            case 2:
                Team_News_Fragment tab3 = new Team_News_Fragment();
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