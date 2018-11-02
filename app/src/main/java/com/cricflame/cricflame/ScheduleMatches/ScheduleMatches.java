package com.cricflame.cricflame.ScheduleMatches;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Model.PagerSchedule;

public class ScheduleMatches extends AppCompatActivity {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_schedule_matches);
        if(new CheckProxy().isProxyDisabled()){

        }else {
            Toast.makeText(ScheduleMatches.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(com.cricflame.cricflame.R.id.tab_layout);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("INTERNATIONAL"));
        tabLayout.addTab(tabLayout.newTab().setText("ODI"));
        tabLayout.addTab(tabLayout.newTab().setText("T20"));
        tabLayout.addTab(tabLayout.newTab().setText("LEAGUE"));
        tabLayout.addTab(tabLayout.newTab().setText("WOMEN"));
        tabLayout.addTab(tabLayout.newTab().setText("TEST"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(com.cricflame.cricflame.R.id.pager);
        PagerSchedule adapter = new PagerSchedule(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
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



    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(com.cricflame.cricflame.R.layout.progressdialog);
        // dialog.setMessage(Message);
        return dialog;
    }

}