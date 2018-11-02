package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 20/11/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.cricflame.cricflame.Fragment.Team1LiveScore;
import com.cricflame.cricflame.Fragment.Team2LiveScore;


public class LiveScorePager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public LiveScorePager(FragmentManager fm, int tabCount) {
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
                Team1LiveScore tab1 = new Team1LiveScore();
                return tab1;
            case 1:
                Team2LiveScore tab2 = new Team2LiveScore();
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