package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.All_Matches_Fragment;
import com.cricflame.cricflame.Fragment.SummaryMatchFragment;
import com.cricflame.cricflame.Fragment.VenueFragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager1 extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager1(FragmentManager fm, int tabCount) {
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
                All_Matches_Fragment tab1 = new All_Matches_Fragment();
                return tab1;
            case 1:
                VenueFragment tab2 = new VenueFragment();
                return tab2;
            case 2:
                SummaryMatchFragment summary = new SummaryMatchFragment();
                return summary;
                /*News_Fragment tab3 = new News_Fragment();
                return tab3;*/
            case 3:

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