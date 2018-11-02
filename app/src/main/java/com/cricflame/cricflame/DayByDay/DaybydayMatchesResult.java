package com.cricflame.cricflame.DayByDay;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.AllMatchResultAdapter;
import com.cricflame.cricflame.Chat.main.service.MainService;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.Fragment.MatchWiseResultFragment;
import com.cricflame.cricflame.Fragment.SeriesWiseResultFragment;
import com.cricflame.cricflame.Fragment.TeamWiseResultFragment;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.News.EndlessRecyclerOnScrollListener;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Results.ResultsActivity;
import com.cricflame.cricflame.Results.fragments.Domestic_Results_Fragment;
import com.cricflame.cricflame.Results.fragments.International_Results_Fragment;
import com.cricflame.cricflame.Results.fragments.T20League_Results_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class DaybydayMatchesResult extends AppCompatActivity {
    private ImageView back;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daybyday__results_);

        init();
    }

    private void init(){

        viewPager = (ViewPager) findViewById(R.id.vp_results);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_results);
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Date Wise"));
        tabLayout.addTab(tabLayout.newTab().setText("Series Wise"));
        tabLayout.addTab(tabLayout.newTab().setText("Team Wise"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ResultsPager adapter = new ResultsPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
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

    private class ResultsPager extends FragmentStatePagerAdapter {
        private final int tabCount;

        public ResultsPager(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount= tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    MatchWiseResultFragment fragment1 = new MatchWiseResultFragment();
                    return  fragment1;

                case 1:
                    SeriesWiseResultFragment fragment2 = new SeriesWiseResultFragment();
                    return  fragment2;

                case 2:
                    TeamWiseResultFragment fragment3 = new TeamWiseResultFragment();
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
