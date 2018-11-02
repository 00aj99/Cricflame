package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Player_Batting;
import com.cricflame.cricflame.Fragment.Player_Bowling;
import com.cricflame.cricflame.Fragment.Player_Carrier;
import com.cricflame.cricflame.Fragment.Player_Info;
import com.cricflame.cricflame.Fragment.Player_News;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class PlayerPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PlayerPager(FragmentManager fm, int tabCount) {
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
                Player_Info tab1 = new Player_Info();
                return tab1;
            case 1:
                Player_Batting tab2 = new Player_Batting();
                return tab2;
            case 2:
                Player_Bowling tab3 = new Player_Bowling();
                return tab3;
            case 3:
                Player_Carrier tab4 = new Player_Carrier();
                return tab4;
            case 4:
                Player_News tab5 = new Player_News();
                return tab5;
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