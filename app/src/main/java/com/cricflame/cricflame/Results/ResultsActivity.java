package com.cricflame.cricflame.Results;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Results.fragments.Domestic_Results_Fragment;
import com.cricflame.cricflame.Results.fragments.International_Results_Fragment;
import com.cricflame.cricflame.Results.fragments.T20League_Results_Fragment;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        TabLayout tabLayout = (TabLayout) findViewById(com.cricflame.cricflame.R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("International"));
        tabLayout.addTab(tabLayout.newTab().setText("Domestic"));
        tabLayout.addTab(tabLayout.newTab().setText("T20 Leagues"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(com.cricflame.cricflame.R.id.pager);
        ResultsPager adapter = new ResultsPager(getSupportFragmentManager(), tabLayout.getTabCount());
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

    public class ResultsPager extends FragmentStatePagerAdapter {
        private final int tabCount;

        public ResultsPager(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount= tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    International_Results_Fragment fragment1 = new International_Results_Fragment();
                    return  fragment1;

                case 1:
                    Domestic_Results_Fragment fragment2 = new Domestic_Results_Fragment();
                    return  fragment2;

                case 2:
                    T20League_Results_Fragment fragment3 = new T20League_Results_Fragment();
                    return  fragment3;


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

}
