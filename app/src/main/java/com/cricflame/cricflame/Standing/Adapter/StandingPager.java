package com.cricflame.cricflame.Standing.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Ranking.fragments.BatsmanRankFragment;

/**
 * Created by yashlam on 1/26/2018.
 */

public class StandingPager extends FragmentStatePagerAdapter {
    private final int tabCount;

    public StandingPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                BatsmanRankFragment batsmanfrag = new BatsmanRankFragment();
                return  batsmanfrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
