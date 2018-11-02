package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Browse_Commentary_Fragment;
import com.cricflame.cricflame.Fragment.Browse_Match_Info;
import com.cricflame.cricflame.Fragment.Browse_Scoreboard_Fragment;
import com.cricflame.cricflame.Fragment.Browse_Squad_Info;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class BrowseMatchDetailPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public BrowseMatchDetailPager(FragmentManager fm, int tabCount) {
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
                Browse_Match_Info tab1 = new Browse_Match_Info();
                return tab1;
            case 1:
                Browse_Squad_Info tab2 = new Browse_Squad_Info();
                return tab2;
            case 2:
                Browse_Scoreboard_Fragment tab3 = new Browse_Scoreboard_Fragment();
                return tab3;

            case 3:
                Browse_Commentary_Fragment tab4 = new Browse_Commentary_Fragment();
                return tab4;

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