package com.cricflame.cricflame.Model;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.Photo_Fragment;
import com.cricflame.cricflame.Fragment.Video_Fragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class GalleryPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;



    //Constructor to the class
    public GalleryPager(FragmentManager fm, int tabCount) {
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
                Photo_Fragment tab1 = new Photo_Fragment();
                return tab1;
            case 1:
                Video_Fragment tab2 = new Video_Fragment();
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