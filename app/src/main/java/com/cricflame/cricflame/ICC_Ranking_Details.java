package com.cricflame.cricflame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cricflame.cricflame.Adapter.RankingPager_New;
import com.cricflame.cricflame.Adapter.RankingPager_womens;
import com.cricflame.cricflame.Ranking.adapter.RankingPager;
import com.google.android.exoplayer2.Player;

import java.text.Normalizer;


public class ICC_Ranking_Details extends AppCompatActivity {
    String Gender,Type,Format,PlayerType;
    ImageView back;
    TextView Header;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iccranking_details);

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        Gender = bundle.getString("gender");
        Type = bundle.getString("type");
        Format = bundle.getString("format");
        PlayerType = bundle.getString("playertype");
        Header = findViewById(R.id.header_detials);

      /*  toolbar =  findViewById(R.id.toolbar_Rankings_details);
        setSupportActionBar(toolbar);*/

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Type.equalsIgnoreCase("teams") && Gender.equalsIgnoreCase("men")){
            Header.setText("Men's Team Rankings");
        }else if(Type.equalsIgnoreCase("teams") && Gender.equalsIgnoreCase("women")){
            Header.setText("Women's Team Rankings");
        }else if(Type.equalsIgnoreCase("Players") && Gender.equalsIgnoreCase("women")){
            Header.setText("Women's Player Rankings");
        }else if(Type.equalsIgnoreCase("Players") && Gender.equalsIgnoreCase("men")){
            Header.setText("Men's Player Rankings");
        }


        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Gender.equalsIgnoreCase("Men")){
            TabLayout tabLayout = (TabLayout) findViewById(com.cricflame.cricflame.R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("TEST"));
            tabLayout.addTab(tabLayout.newTab().setText("ODI"));
            tabLayout.addTab(tabLayout.newTab().setText("T20"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



            final ViewPager viewPager = (ViewPager) findViewById(com.cricflame.cricflame.R.id.pager);
            RankingPager_New adapter = new RankingPager_New(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);

            if(Format.equalsIgnoreCase("test")){
                viewPager.setCurrentItem(0);
            }else if(Format.equalsIgnoreCase("ODI")){
                viewPager.setCurrentItem(1);
            }else if(Format.equalsIgnoreCase("T20")){
                viewPager.setCurrentItem(2);
            }else{
                viewPager.setCurrentItem(0);
            }
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


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        }else{
            TabLayout tabLayout = (TabLayout) findViewById(com.cricflame.cricflame.R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("ODI"));
            tabLayout.addTab(tabLayout.newTab().setText("T20"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


            final ViewPager viewPager = (ViewPager) findViewById(com.cricflame.cricflame.R.id.pager);
            RankingPager_womens adapter = new RankingPager_womens(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(1);
            if(Format.equalsIgnoreCase("ODI")){
                viewPager.setCurrentItem(0);
            }else if(Format.equalsIgnoreCase("T20")){
                viewPager.setCurrentItem(1);
            }else{
                viewPager.setCurrentItem(0);
            }
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
}
