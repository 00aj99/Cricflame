package com.cricflame.cricflame.Standing;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Standing.Adapter.StandingAdapter;
import com.cricflame.cricflame.Standing.Model.Standing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StandingActivity extends AppCompatActivity {
    private StandingAdapter standingAdapter;
    private Spinner sp_standing;
    private ArrayList<Standing> standing_list = new ArrayList<Standing>();
    TabLayout tabLayout;
    ViewPager viewPager;

    public static String url,TourName;

    List<String> keyList;
    private ImageView back;
    TextView SeriesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing);
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SeriesName = findViewById(R.id.series_title);

        TourName = getIntent().getExtras().getString("tourname");
        url = getIntent().getExtras().getString("url");
        sp_standing = (Spinner) findViewById(R.id.sp_standing);

        SeriesName.setText(TourName);
        //iCurrentSelection= sp_standing.getSelectedItemPosition();
       /* sp_standing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                url = standing_list.get(i).getSeriesUrl();
                getSandingDetails(url);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getSandingList();*/
        getSandingDetails(url);

    }

    private void getSandingDetails(String url) {
        String URL = url;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject pointsTable = response.getJSONObject("group");
                            keyList = getAllKeys(pointsTable);
                            ViewPagerTask(keyList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StandingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

    }

    private void ViewPagerTask(List<String> list) {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        StandingPager myPagerAdapter1 = new StandingPager(getSupportFragmentManager());
        myPagerAdapter1.addFragment(list);

        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager, false);
        }

        viewPager.setAdapter(myPagerAdapter1);
        myPagerAdapter1.notifyDataSetChanged();
    }


    private List<String> getAllKeys(JSONObject jsonObject) throws JSONException {
        List<String> keys = new ArrayList<String>();
        keys.clear();
        Iterator<?> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            keys.add(key);
        }
        return keys;
    }


    public class StandingPager extends FragmentStatePagerAdapter {
        private List<String> mFragmentTitleList;

        public StandingPager(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            StandingFragment fragment = new StandingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key", mFragmentTitleList.get(position));
            fragment.setArguments(bundle);
            return fragment;

        }


        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        private void addFragment(List<String> title) {
            mFragmentTitleList = title;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }


}
