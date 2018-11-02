package com.cricflame.cricflame.Ranking.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricflame.cricflame.Ranking.fragments.AllRoundRankFragment;
import com.cricflame.cricflame.Ranking.fragments.BatsmanRankFragment;
import com.cricflame.cricflame.Ranking.fragments.BolwerRankFragment;
import com.cricflame.cricflame.Ranking.fragments.TeamRankFragment;

/**
 * Created by yashlam on 1/26/2018.
 */

public class RankingPager extends FragmentStatePagerAdapter {
    private final int tabCount;

    public RankingPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                BatsmanRankFragment batsmanfrag = new BatsmanRankFragment();
                return  batsmanfrag;

            case 1:
                BolwerRankFragment bowlerfrag = new BolwerRankFragment();
                return  bowlerfrag;

            case 2:
                AllRoundRankFragment allroundfrag = new AllRoundRankFragment();
                return  allroundfrag;

            case 3:
                TeamRankFragment teamfrag = new TeamRankFragment();
                return  teamfrag;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
