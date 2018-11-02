package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalysisActivity extends AppCompatActivity {
    public ArrayList<TourData> analysis_list = new ArrayList<>();
    private AnalysisAdapter mAdapter;
    private RecyclerView rv_analysis;
    private ImageView back;
    String MatchId;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        loadingDialog = new LoadingDialog(AnalysisActivity.this);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MatchId = getIntent().getExtras().getString("id");
        init();
        getAllAnalysis();
        ItemClickSupport.addTo(rv_analysis)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        mAdapter = (AnalysisAdapter) recyclerView.getAdapter();
                        TourData product = (TourData) mAdapter.getItem(position);
                        Intent intent = new Intent(AnalysisActivity.this, DetailAnlysis.class);
                        intent.putExtra("image",product.getImage());
                        intent.putExtra("heading",product.getHeading());
                        intent.putExtra("description",product.getDescription());
                        intent.putExtra("date",product.getStrat_date());
                        startActivity(intent);

                    }
                });
    }

    public void init() {
        rv_analysis = (RecyclerView) findViewById(R.id.rv_analysis);

        /*match_info = (TextView) view.findViewById(R.id.match_info);
        country_logo1=(ImageView)view.findViewById(R.id.country_logo1);
        country_logo2 = (ImageView) view.findViewById(R.id.country_logo2);
        upcoming = (TextView) view.findViewById(R.id.upcomning);
        country1 = (TextView) view.findViewById(R.id.country1);
        country2 = (TextView) view.findViewById(R.id.country2);
        team1_run = (TextView) view.findViewById(R.id.team1_run);
        team2_run = (TextView) view.findViewById(R.id.team2_run);*/
    }

    public void getAllAnalysis() {
        analysis_list.clear();
        loadingDialog.show();

        StringRequest productRequest = new StringRequest(Request.Method.POST, Global.URL + "get_analysis.php?matchid=" + MatchId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject stats = jsonArray.getJSONObject(i);
                        TourData statsData = new TourData();

                        statsData.setHeading(stats.getString("heading"));
                        statsData.setImage(stats.getString("image_name"));
                        statsData.setStrat_date(stats.getString("date"));
                        statsData.setDescription(stats.getString("news"));
                        analysis_list.add(statsData);

                    }
                    mAdapter = new AnalysisAdapter(AnalysisActivity.this, analysis_list);
                    rv_analysis.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    rv_analysis.setLayoutManager(new LinearLayoutManager(AnalysisActivity.this, LinearLayoutManager.VERTICAL, false));
                    rv_analysis.setHasFixedSize(false);
                    rv_analysis.setItemAnimator(new DefaultItemAnimator());
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }
        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("matchid", MatchId);
//                return params;
//            }
        };
        productRequest.setShouldCache(false);productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(AnalysisActivity.this).add(productRequest);

    }

    class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.MyViewHolder> {
        Context context;
        ArrayList<TourData> stats_list = new ArrayList<TourData>();


        public AnalysisAdapter(Context context, ArrayList<TourData> leavelist) {
            this.context = context;
            this.stats_list = leavelist;
        }

        @Override
        public AnalysisAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_home_layout_view, parent, false);
            AnalysisAdapter.MyViewHolder myholder = new AnalysisAdapter.MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final AnalysisAdapter.MyViewHolder holder, int position) {

            holder.heading.setText(stats_list.get(position).getHeading());
            holder.date.setText(stats_list.get(position).getStrat_date());
            holder.description.setText(Html.fromHtml(stats_list.get(position).getDescription()));
            holder.image_view.setVisibility(View.GONE);
            Glide.with(context).load(Global.ANALYSIS_IMAGE_URL + stats_list.get(position).getImage()).into(holder.image_view);

            //Glide.with(getActivity()).load("http://cricflame.com/admin/data/liveline/team1.png").into(holder.country_logo1);
            //  Glide.with(getActivity()).load("http://cricflame.com/admin/data/liveline/team2.png").into(holder.country_logo2);



        }

        @Override
        public int getItemCount() {
            return stats_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView heading, date, description;
            ImageView image_view;//,country_logo1,country_logo2


            public MyViewHolder(View view) {
                super(view);

                heading = (TextView) view.findViewById(com.cricflame.cricflame.R.id.title);
                date = (TextView) view.findViewById(com.cricflame.cricflame.R.id.duration);
                description = (TextView) view.findViewById(com.cricflame.cricflame.R.id.description);
                image_view = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.news_image);
                // country_logo1=(ImageView)view.findViewById(R.id.country_logo1);
                // country_logo2 = (ImageView) view.findViewById(R.id.country_logo2);

            }
        }


        public Object getItem(int location) {
            return stats_list.get(location);
        }


    }
}
