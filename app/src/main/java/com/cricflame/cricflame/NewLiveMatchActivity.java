package com.cricflame.cricflame;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.cricflame.cricflame.Fragment.LiveMatchFragment;
import com.cricflame.cricflame.Fragment.MatchInfoFragment;
import com.cricflame.cricflame.Fragment.ScoreCardFragment;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NewLiveMatchActivity extends AppCompatActivity {
    ViewPagerAdapter adapter;
    String flag1;
    String flag2;
    String key;
    String MatchStatus;
    LiveMatchFragment liveMatchFragment;
    MatchInfoFragment matchInfoFragment;
    public ViewPager pager;
    ScoreCardFragment scoreCardFragment;
    String status;
    TabLayout tabLayout;
    String team1;
    String team1_full;
    String team2;
    String team2_full;
    String Comment;
    String Score1,Score2,Over1,Over2,SessionType;
    int type;
    String MatchID, ScoreCardId,IsCricBuzz,EventId,MarketId,i1,i2,i3,oddsType;



    class LoadTabs implements TabLayout.OnTabSelectedListener {
        LoadTabs() {
        }

        public void onTabSelected(TabLayout.Tab tab) {
            NewLiveMatchActivity.this.pager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewLiveMatchActivity.this.matchInfoFragment;
                case 1:
                    return NewLiveMatchActivity.this.liveMatchFragment;
                case 2:
                    return NewLiveMatchActivity.this.scoreCardFragment;
                default:
                    return null;
            }
        }

        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MATCH INFO";
                case 1:
                    return "LIVE MATCH";
                case 2:
                    return "SCORECARD";
                default:
                    return null;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.key = getIntent().getExtras().getString("key");
        this.type = getIntent().getExtras().getInt("type");
        this.MatchStatus = getIntent().getExtras().getString("matchStatus");
        this.Comment = getIntent().getExtras().getString("comment");
        this.Score1 = getIntent().getExtras().getString("score1");
        this.Score2 = getIntent().getExtras().getString("score2");
        this.Over1 = getIntent().getExtras().getString("over1");
        this.Over2 = getIntent().getExtras().getString("over2");
        this.team1 = getIntent().getExtras().getString("team1");
        this.team2 = getIntent().getExtras().getString("team2");
        this.team1_full = getIntent().getExtras().getString("team1_full");
        this.team2_full = getIntent().getExtras().getString("team2_full");
        this.flag1 = getIntent().getExtras().getString("flag1");
        this.flag2 = getIntent().getExtras().getString("flag2");
        this.status = getIntent().getExtras().getString("status");
        this.MatchID = getIntent().getExtras().getString("id");
        this.ScoreCardId = getIntent().getExtras().getString("scorecardid");
        this.IsCricBuzz = getIntent().getExtras().getString("ISCricBuzz");
        this.EventId = getIntent().getExtras().getString("EventID");
        this.MarketId = getIntent().getExtras().getString("MarketId");
        this.i1 = getIntent().getExtras().getString("i1");
        this.i2 = getIntent().getExtras().getString("i2");
        this.i3 = getIntent().getExtras().getString("i3");
        this.oddsType = getIntent().getExtras().getString("oddsType");
        this.SessionType = getIntent().getExtras().getString("sessionType");
        setContentView((int) R.layout.activity_new_live_match);
        this.matchInfoFragment = new MatchInfoFragment();
        this.liveMatchFragment = new LiveMatchFragment();
        this.scoreCardFragment = new ScoreCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", this.key);
        bundle.putString("matchStatus",this.MatchStatus);
        bundle.putString("team1", this.team1);
        bundle.putString("team2", this.team2);
        bundle.putString("team1_full", this.team1_full);
        bundle.putString("team2_full", this.team2_full);
        bundle.putString("flag1", this.flag1);
        bundle.putString("flag2", this.flag2);
        bundle.putInt("type", this.type);
        bundle.putString("status", this.status);
        bundle.putString("comment", this.Comment);
        bundle.putString("score1", this.Score1);
        bundle.putString("score2", this.Score2);
        bundle.putString("over1", this.Over1);
        bundle.putString("over2", this.Over2);
        bundle.putString("id",this.MatchID);
        bundle.putString("ScoreCardId",this.ScoreCardId);
        bundle.putString("ISCricBuzz",IsCricBuzz);
        bundle.putString("EventID",this.EventId);
        bundle.putString("MarketId",this.MarketId);
        bundle.putString("i1",this.i1);
        bundle.putString("i2",this.i2);
        bundle.putString("i3",this.i2);
        bundle.putString("oddsType",oddsType);
        bundle.putString("sessionType",this.SessionType);
        this.matchInfoFragment.setArguments(bundle);
        this.liveMatchFragment.setArguments(bundle);
        this.scoreCardFragment.setArguments(bundle);
        this.tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        this.pager = (ViewPager) findViewById(R.id.viewPager);
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.pager.setAdapter(this.adapter);
        for (int i = 0; i < this.adapter.getCount(); i++) {
            this.tabLayout.addTab(this.tabLayout.newTab().setText(this.adapter.getPageTitle(i)));
        }
        this.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        this.tabLayout.setOnTabSelectedListener(new LoadTabs());
        this.pager.setOffscreenPageLimit(3);
        if (this.status.equals("0")) {
            this.pager.setCurrentItem(0);
        } else {
            this.pager.setCurrentItem(1);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
    }
}

