package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Domestic_Match_Fragment;
import com.cricflame.cricflame.Fragment.International_Match_Fragment;
import com.cricflame.cricflame.Fragment.T20Match_Fragment;
import com.cricflame.cricflame.Fragment.Women_Match_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager2 extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager2(FragmentManager fm, int tabCount) {
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
                International_Match_Fragment tab1 = new International_Match_Fragment();
                return tab1;
            case 1:
                T20Match_Fragment tab2 = new T20Match_Fragment();
                return tab2;
            case 2:
                Domestic_Match_Fragment tab3 = new Domestic_Match_Fragment();
                return tab3;
            case 3:
                Women_Match_Fragment tab4 = new Women_Match_Fragment();
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