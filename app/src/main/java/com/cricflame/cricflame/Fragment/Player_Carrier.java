package com.cricflame.cricflame.Fragment;

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
import com.cricflame.cricflame.BrowsePlayer;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.ScoreboardData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Deepak Sharma on 29/10/2017.
 */

public class Player_Carrier  extends Fragment {
    public ArrayList<ScoreboardData> battingList = new ArrayList<ScoreboardData>();
    ListView batting_list;
    Batting_adapter batting_adapter;
    ImageView no_data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carrier_layout, container, false);
        batting_list = (ListView) view.findViewById(R.id.carrier_list);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        if(new CheckProxy().isProxyDisabled()){
            getPlayerBattingData();
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            getActivity().finish();
        }
        return view;
    }

    public void getPlayerBattingData(){
        battingList.clear();
        JsonObjectRequest productRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "player.php?id="+ BrowsePlayer.Player_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Log.e("Response from Server :","  ₹ "+jsonObject.toString());
                    JSONObject result=jsonObject.getJSONObject("data");
                    JSONArray data = result.getJSONArray("career");
                    if (data.length()<=0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    }
                    else {
                        for (int i = 0; i < data.length(); i++) {
                            ScoreboardData score = new ScoreboardData();
                            JSONObject runs = data.getJSONObject(i);
                            score.setBatsman(runs.getString("format"));
                            score.setBat_run(runs.getString("debut"));
                            score.setBat_ball(runs.getString("last_played"));
                            battingList.add(score);

                        }
                        batting_adapter = new Batting_adapter(getActivity(), battingList);
                        batting_list.setAdapter(batting_adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        productRequest.setShouldCache(false);
        productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(productRequest);



    }

    public class Batting_adapter extends BaseAdapter {

        private Activity activity;

        private LayoutInflater inflater;
        private ArrayList<ScoreboardData> Items;


        public Batting_adapter(Activity activity, ArrayList<ScoreboardData> Items) {
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
                convertView = inflater.inflate(R.layout.carrier_layout_view, null);


            TextView batsman = (TextView) convertView.findViewById(R.id.format);
            TextView run = (TextView) convertView.findViewById(R.id.debut);
            TextView bowl = (TextView) convertView.findViewById(R.id.last_played);




            batsman.setText(Items.get(position).getBatsman());
            run.setText(Items.get(position).getBat_run());
            bowl.setText(Items.get(position).getBat_ball());

            notifyDataSetChanged();
            // Glide.with(activity).load(Global.IMG_URL+Items.get(position).getImage()).into(Image);

            return convertView;

        }

    }

}

