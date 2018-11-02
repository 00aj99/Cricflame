package com.cricflame.cricflame.DayByDay;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.AllMatchData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class Daybyday_Match_Fragment extends Fragment {

    LoadingDialog loadingDialog;

    public Daybyday_Match_Fragment() {
        // Required empty public constructor
    }


    public ArrayList<AllMatchData> tourData=new ArrayList<AllMatchData>();
    ListView tour_list;
    AllMatch_Adapter tour_adapter;
    ImageView no_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_matches_layout,container,false);
        tour_list= (ListView) view.findViewById(R.id.all_match_list);
        loadingDialog = new LoadingDialog(getActivity());
        getAllInternationalTours();



        return view;
    }

    public void getAllInternationalTours(){
        tourData.clear();
        loadingDialog.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"schedule_match_sort.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data=response.getJSONObject("data");
                    JSONArray months = data.getJSONArray("match");
                    if (months.length() <= 0) {
                        Toast.makeText(getActivity(), "No Record Found!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for (int i = 0; i < months.length(); i++) {
                            JSONObject jsonObject1 = months.getJSONObject(i);
                            AllMatchData matchData = new AllMatchData();
                            matchData.setId(jsonObject1.getString("id"));
                            matchData.setMatch_date(jsonObject1.getString("match_name"));
                            matchData.setMatch_name(jsonObject1.getString("start_date")+" "+jsonObject1.getString("start_time"));
                            matchData.setTeam1_name(jsonObject1.getString("team1"));
                            matchData.setTeam1_flag(jsonObject1.getString("team1_img"));
                            matchData.setTeam2_flag(jsonObject1.getString("team2_img"));
                            matchData.setTeam2_name(jsonObject1.getString("team2"));

                            tourData.add(matchData);


                        }

                        tour_adapter = new AllMatch_Adapter(getActivity(), tourData);

                        tour_list.setAdapter(tour_adapter);

                        if(loadingDialog.isShowing()){
                            loadingDialog.dismiss();
                        }
                    }  // months.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setShouldCache(false);jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

    public class AllMatch_Adapter extends BaseAdapter {

        private Activity activity;

        private LayoutInflater inflater;
        private ArrayList<AllMatchData> Items;



        public AllMatch_Adapter(Activity activity, ArrayList<AllMatchData> Items) {
            this.activity = activity;
            this.Items = Items;
        }



        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public Object getItem(int location) {
            return Items.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.all_matches_view,null);

            TextView Id = (TextView) convertView.findViewById(R.id.id);
            TextView match_date = (TextView) convertView.findViewById(R.id.match_date);
            TextView match_detail = (TextView) convertView.findViewById(R.id.match_detail);
            TextView team1_name = (TextView) convertView.findViewById(R.id.team1_name);
            TextView team2_name = (TextView) convertView.findViewById(R.id.team2_name);
            ImageView team1_flag = (ImageView) convertView.findViewById(R.id.team1_flag);
            ImageView team2_flag = (ImageView) convertView.findViewById(R.id.team2_flag);

            Id.setText(Items.get(position).getId());
            match_date.setText(Items.get(position).getMatch_date());
            match_detail.setText(Items.get(position).getMatch_name());
            team1_name.setText(Items.get(position).getTeam1_name());
            team2_name.setText(Items.get(position).getTeam2_name());

            notifyDataSetChanged();
            Glide.with(activity).load(Global.PHOTO_URL+Items.get(position).getTeam1_flag()).into(team1_flag);
            Glide.with(activity).load(Global.PHOTO_URL+Items.get(position).getTeam2_flag()).into(team2_flag);

            return convertView;

        }


    }

}
