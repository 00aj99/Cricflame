package com.cricflame.cricflame;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cricflame.cricflame.Fragment.All_Matches_Fragment;
import com.cricflame.cricflame.Fragment.VenueFragment;

public class FeatureMatchSeries extends AppCompatActivity {

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_browse_series);

        if(new CheckProxy().isProxyDisabled()){

        }else {
            Toast.makeText(FeatureMatchSeries.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }

        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(com.cricflame.cricflame.R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("MATCHES"));
        tabLayout.addTab(tabLayout.newTab().setText("VENUE"));
        tabLayout.addTab(tabLayout.newTab().setText("SUMMARY"));
        tabLayout.addTab(tabLayout.newTab().setText("COMMENTARY"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(com.cricflame.cricflame.R.id.pager);
        Pager2 adapter = new Pager2(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class Pager2 extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager2(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    All_Matches_Fragment tab1 = new All_Matches_Fragment();
                    return tab1;
                case 1:
                    VenueFragment tab2 = new VenueFragment();
                    return tab2;
                case 2:
                    //Domestic_Match_Fragment tab3 = new Domestic_Match_Fragment();
                   // return tab3;
                case 3:
                    //Women_Match_Fragment tab4 = new Women_Match_Fragment();
                   // return tab4;
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
