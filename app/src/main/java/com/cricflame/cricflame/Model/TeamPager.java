package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.International_Team_Fragment;
import com.cricflame.cricflame.Fragment.T20Team_Fragment;
import com.cricflame.cricflame.Fragment.Women_Team_Fragment;
import com.cricflame.cricflame.Fragment.Domestic_Team_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class TeamPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public TeamPager(FragmentManager fm, int tabCount) {
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
                International_Team_Fragment tab1 = new International_Team_Fragment();
                return tab1;
            case 1:
                T20Team_Fragment tab2 = new T20Team_Fragment();
                return tab2;
            case 2:
                Domestic_Team_Fragment tab3 = new Domestic_Team_Fragment();
                return tab3;
            case 3:
                Women_Team_Fragment tab4 = new Women_Team_Fragment();
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