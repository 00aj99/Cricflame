package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Global;

import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PitchReportActivity extends AppCompatActivity {

    private RecyclerView rv_pitch_report;
    private ImageView country_logo1, country_logo2;
    private TextView country, country2;
    public ArrayList<TourData> stats_list = new ArrayList<>();
    private PitchReportAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_report_layout);
        init();

        SnapHelper mSnapHelper1 = new PagerSnapHelper();
        mSnapHelper1.attachToRecyclerView(rv_pitch_report);
        getAllStats();
        ItemClickSupport.addTo(rv_pitch_report)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        mAdapter = (PitchReportAdapter) recyclerView.getAdapter();
                        TourData product = (TourData) mAdapter.getItem(position);
                      //  VIDEO_URL = product.getStrat_date();
                        //VideoViewActivity.VIDEO_HEADING = product.getHeading();
                       // startActivity(new Intent(PitchReportActivity.this, VideoViewActivity.class));

                    }
                });

    }


    public void getAllStats() {
        stats_list.clear();

        JsonArrayRequest productRequest = new JsonArrayRequest(Request.Method.GET, Global.URL + "get_stats.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject stats = jsonArray.getJSONObject(i);
                        TourData videoData = new TourData();
                        videoData.setId(stats.getString("stats_id"));
                        videoData.setHeading(stats.getString("heading"));
                        videoData.setImage(stats.getString("image_name"));
                        videoData.setStrat_date(stats.getString("date"));
                        videoData.setDescription(stats.getString("news"));
                        stats_list.add(videoData);

                    }

                    //  mHAdapter = new VideoListAdapter(MainActivity.this, videoList);
                    mAdapter = new PitchReportAdapter(PitchReportActivity.this, stats_list);
                    rv_pitch_report.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    //viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, allMatches);
                    rv_pitch_report.setLayoutManager(new LinearLayoutManager(PitchReportActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    rv_pitch_report.setHasFixedSize(false);
                    rv_pitch_report.setItemAnimator(new DefaultItemAnimator());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        productRequest.setShouldCache(false);productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(PitchReportActivity.this).add(productRequest);

    }

    public void init() {
        rv_pitch_report = (RecyclerView) findViewById(R.id.rv_pitch_report);
        country_logo1 = (ImageView) findViewById(R.id.country_logo1);
        country_logo2 = (ImageView) findViewById(R.id.country_logo2);

        country = (TextView) findViewById(R.id.country);
        country2 = (TextView) findViewById(R.id.country2);


    }

    class PitchReportAdapter extends RecyclerView.Adapter<PitchReportAdapter.MyViewHolder> {
        Context context;
        ArrayList<TourData> stats_list = new ArrayList<TourData>();


        public PitchReportAdapter(Context context, ArrayList<TourData> leavelist) {
            this.context = context;
            this.stats_list = leavelist;
        }

        @Override
        public PitchReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_home_layout_view, parent, false);
            PitchReportAdapter.MyViewHolder myholder = new PitchReportAdapter.MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final PitchReportAdapter.MyViewHolder holder, int position) {

            holder.heading.setText(stats_list.get(position).getHeading());
            holder.date.setText(stats_list.get(position).getStrat_date());
            holder.description.setText(stats_list.get(position).getDescription());

            Glide.with(context).load(Global.PHOTO_URL + stats_list.get(position).getImage()).into(holder.image_view);


        }

        @Override
        public int getItemCount() {
            return stats_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView heading, date, description;
            ImageView image_view;


            public MyViewHolder(View view) {
                super(view);

                heading = (TextView) view.findViewById(com.cricflame.cricflame.R.id.title);
                date = (TextView) view.findViewById(com.cricflame.cricflame.R.id.duration);
                description = (TextView) view.findViewById(com.cricflame.cricflame.R.id.description);
                image_view = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.news_image);

            }
        }


        public Object getItem(int location) {
            return stats_list.get(location);
        }


    }


}


