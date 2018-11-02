package com.cricflame.cricflame;

import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cricflame.cricflame.Model.Pager2;


public class BrowseSeries extends AppCompatActivity {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_browse_series);

        if(new CheckProxy().isProxyDisabled()){

        }else {
            Toast.makeText(BrowseSeries.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
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
        tabLayout.addTab(tabLayout.newTab().setText("INTERNATIONAL"));
        tabLayout.addTab(tabLayout.newTab().setText("T20 LEAGUES"));
        tabLayout.addTab(tabLayout.newTab().setText("DOMESTIC"));
        tabLayout.addTab(tabLayout.newTab().setText("WOMEN"));
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



}