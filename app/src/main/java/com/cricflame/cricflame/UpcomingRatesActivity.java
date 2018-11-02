package com.cricflame.cricflame;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cricflame.cricflame.Fragment.MatchRateRecord;
import com.cricflame.cricflame.Fragment.UpcomingMatchesFragment;

public class UpcomingRatesActivity extends AppCompatActivity {

    private TabLayout tblUpcoming;
    private ViewPager vpUpcoming;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_rates);


        tblUpcoming = (TabLayout) findViewById(R.id.tbl_upcoming);
        vpUpcoming = (ViewPager) findViewById(R.id.vp_upcoming);

        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tblUpcoming.addTab(tblUpcoming.newTab().setText("MATCHES"));
        tblUpcoming.addTab(tblUpcoming.newTab().setText("RATE RECORD"));
        tblUpcoming.setTabGravity(TabLayout.GRAVITY_FILL);

        UpcomingPagerAdapter adapter = new UpcomingPagerAdapter(getSupportFragmentManager(), tblUpcoming.getTabCount());

        vpUpcoming.setAdapter(adapter);
        vpUpcoming.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tblUpcoming));
        tblUpcoming.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpUpcoming.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public class UpcomingPagerAdapter extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public UpcomingPagerAdapter(FragmentManager fm, int tabCount) {
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
                    UpcomingMatchesFragment tab1 = new UpcomingMatchesFragment();
                    return tab1;
                case 1:
                    MatchRateRecord tab2 = new MatchRateRecord();
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
}
