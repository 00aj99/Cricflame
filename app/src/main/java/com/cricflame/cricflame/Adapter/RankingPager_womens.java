package com.cricflame.cricflame.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Fragment.ODIFragment;
import com.cricflame.cricflame.Fragment.T20Fragment;
import com.cricflame.cricflame.Fragment.TestFragment;

public class RankingPager_womens extends FragmentStatePagerAdapter {
    private final int tabCount;

    public RankingPager_womens(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ODIFragment odiFragment = new ODIFragment();
                return  odiFragment;
            case 1:
                T20Fragment t20Fragment = new T20Fragment();
                return  t20Fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}