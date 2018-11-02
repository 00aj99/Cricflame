package com.cricflame.cricflame.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Ranking.Model.Summary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Global.Tour_Id;
import static com.cricflame.cricflame.Global.Tour_name;
import static com.cricflame.cricflame.Global.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryMatchFragment extends Fragment {


    public ArrayList<Summary> most_run_summary = new ArrayList<>();
    public ArrayList<Summary> most_wicket_summary = new ArrayList<>();
    private ImageView no_data;

    private RecyclerView rv_most_runs;
    private RecyclerView rv_most_wickets;
    private TextView btnTest, btnT20, btnOdi;
    private SummaryMatchAdapter adapter;
    int flag = 4;
    private TextView series_title;
    private SummaryMatchAdapter1 adapter1;

    public SummaryMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary_match, container, false);
        init(view);
        getSummaryMatch();
        series_title.setText(Tour_name);

        return view;
    }

    public void init(View view) {
        rv_most_wickets = (RecyclerView) view.findViewById(R.id.summary_match_most_wickets);
        rv_most_runs = (RecyclerView) view.findViewById(R.id.summary_match_most_runs);
        no_data = (ImageView) view.findViewById(R.id.no_data);
        series_title = (TextView) view.findViewById(R.id.series_title);


        btnTest = (TextView) view.findViewById(R.id.btnTest);
        btnT20 = (TextView) view.findViewById(R.id.btnT20);
        btnOdi = (TextView) view.findViewById(R.id.btnOdi);


        rv_most_wickets.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_most_wickets.setHasFixedSize(false);
        rv_most_wickets.setItemAnimator(new DefaultItemAnimator());

        rv_most_runs.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_most_runs.setHasFixedSize(false);
        rv_most_runs.setItemAnimator(new DefaultItemAnimator());

        btnTest.setTextColor(getResources().getColor(R.color.green));
        btnTest.setBackgroundColor(Color.WHITE);
        btnT20.setTextColor(Color.WHITE);
        btnT20.setBackgroundColor(getResources().getColor(R.color.green));
        btnOdi.setTextColor(Color.WHITE);
        btnOdi.setBackgroundColor(getResources().getColor(R.color.green));


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest.setTextColor(getResources().getColor(R.color.green));
                btnTest.setBackgroundColor(Color.WHITE);
                btnT20.setTextColor(Color.WHITE);
                btnT20.setBackgroundColor(getResources().getColor(R.color.green));
                btnOdi.setTextColor(Color.WHITE);
                btnOdi.setBackgroundColor(getResources().getColor(R.color.green));
                flag = 4;
                getSummaryMatch();


            }
        });

        btnOdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOdi.setTextColor(getResources().getColor(R.color.green));
                btnOdi.setBackgroundColor(Color.WHITE);
                btnT20.setTextColor(Color.WHITE);
                btnT20.setBackgroundColor(getResources().getColor(R.color.green));
                btnTest.setTextColor(Color.WHITE);
                btnTest.setBackgroundColor(getResources().getColor(R.color.green));
                flag=1;
                getSummaryMatch();
            }
        });

        btnT20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnT20.setTextColor(getResources().getColor(R.color.green));
                btnT20.setBackgroundColor(Color.WHITE);
                btnOdi.setTextColor(Color.WHITE);
                btnOdi.setBackgroundColor(getResources().getColor(R.color.green));
                btnTest.setTextColor(Color.WHITE);
                btnTest.setBackgroundColor(getResources().getColor(R.color.green));
                flag= 2;
                getSummaryMatch();
            }
        });
    }


    public void getSummaryMatch() {

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL + "livematchsummary.php?id=" + Tour_Id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

//                    Log.e( "mostruns: ",mostruns+"" );
//                    Log.e( "mostwickets: ",mostwickets+"" );

                        JSONObject data = response.getJSONObject("data");
                        try{
                            JSONObject mostruns = data.getJSONObject("mostruns");
                            getMostRunsSummary(mostruns);
                            JSONObject mostwickets = data.getJSONObject("mostwickets");
                            getMostWicketsSummary(mostwickets);
                        }
                        catch (NullPointerException e){
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{
                        Toast.makeText(getActivity(), "Some thing went wrong!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Some thing went wrong!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            jsonObjectRequest.setShouldCache(false);
            Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void getMostRunsSummary(JSONObject mostwickets) {

        try {
            JSONArray jsonArray;
            if(flag == 1){
                most_run_summary.clear();
                jsonArray = mostwickets.getJSONArray("odi");

            } else if(flag == 2){
                most_run_summary.clear();
                jsonArray = mostwickets.getJSONArray("t20");

            } else{
                most_run_summary.clear();
                jsonArray = mostwickets.getJSONArray("test");
           }

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject1 = null;
                jsonObject1 = jsonArray.getJSONObject(j);
                Summary matchData = new Summary();
                matchData.setPlayer(jsonObject1.getString("player_name"));
                matchData.setImage(jsonObject1.getString("img"));
                matchData.setRuns(jsonObject1.getString("runs"));
                matchData.setSr(jsonObject1.getString("sr"));

                most_run_summary.add(matchData);
            }

            adapter = new SummaryMatchAdapter(getActivity(), most_run_summary);
            rv_most_runs.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getMostWicketsSummary(JSONObject mostruns) {


        try {
            JSONArray jsonArray;
            if(flag == 1){
                most_wicket_summary.clear();
                jsonArray = mostruns.getJSONArray("odi");

            } else if(flag == 2){
                most_wicket_summary.clear();
                jsonArray = mostruns.getJSONArray("t20");

            } else{
                most_wicket_summary.clear();
                jsonArray = mostruns.getJSONArray("test");

            }

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject1 = null;
                jsonObject1 = jsonArray.getJSONObject(j);
                Summary matchData = new Summary();
                matchData.setPlayer(jsonObject1.getString("player_name"));
                matchData.setImage(jsonObject1.getString("img"));
                matchData.setRuns(jsonObject1.getString("wickets"));
                matchData.setSr(jsonObject1.getString("economy"));

                most_wicket_summary.add(matchData);
            }

            adapter1 = new SummaryMatchAdapter1(getActivity(), most_wicket_summary);
            rv_most_wickets.setAdapter(adapter1);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public class SummaryMatchAdapter extends RecyclerView.Adapter<SummaryMatchAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<Summary> list;
        public SummaryMatchAdapter(Context context,ArrayList<Summary> list) {
            this.mContext = context;
            this.list = list;
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_team_single_layout, parent, false);
            MyViewHolder myholder = new MyViewHolder(view);
            return myholder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.sr.setText(list.get(position).getSr());
            holder.player.setText(list.get(position).getPlayer());
            holder.runs.setText(list.get(position).getRuns());

            Glide.with(mContext).load(Global.PLAYER_URL+list.get(position).Image).into(holder.image);
            holder.sn.setText(position+1+"");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView runs,sn,sr,player;
            public ImageView image;
            public MyViewHolder(View itemView) {
                super(itemView);

                sn = (TextView)itemView.findViewById(R.id.rank);
                runs = (TextView)itemView.findViewById(R.id.rating);
                sr = (TextView)itemView.findViewById(R.id.point);
                player = (TextView)itemView.findViewById(R.id.country);
                image = (ImageView)itemView.findViewById(R.id.image);

            }
        }
    }
    public class SummaryMatchAdapter1 extends RecyclerView.Adapter<SummaryMatchAdapter1.MyViewHolder> {

        private Context mContext;
        private ArrayList<Summary> list;
        public SummaryMatchAdapter1(Context context,ArrayList<Summary> list) {
            this.mContext = context;
            this.list = list;
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_team_single_layout, parent, false);
            MyViewHolder myholder = new MyViewHolder(view);
            return myholder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.sr.setText(list.get(position).getSr());
            holder.player.setText(list.get(position).getPlayer());
            holder.runs.setText(list.get(position).getRuns());

            Glide.with(mContext).load(Global.PLAYER_URL+list.get(position).Image).into(holder.image);
            holder.sn.setText(position+1+"");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView runs,sn,sr,player;
            public ImageView image;
            public MyViewHolder(View itemView) {
                super(itemView);

                sn = (TextView)itemView.findViewById(R.id.rank);
                runs = (TextView)itemView.findViewById(R.id.rating);
                sr = (TextView)itemView.findViewById(R.id.point);
                player = (TextView)itemView.findViewById(R.id.country);
                image = (ImageView)itemView.findViewById(R.id.image);

            }
        }
    }
}
