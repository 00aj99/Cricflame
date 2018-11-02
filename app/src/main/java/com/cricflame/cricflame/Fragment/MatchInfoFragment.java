package com.cricflame.cricflame.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.LiveLine1.PitchReport.PitchTab3;
import com.cricflame.cricflame.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MatchInfoFragment extends Fragment {
    TextView txtMatch,txtSeries,txtStatus,txtStartTime,txtEndTime,txtUmpires,txt3rdUmpire,txtRefree,txtStadium,txtCity,txtCapacity,txtHost,
            txtTeam1,txtTeam2,txtTeam1Squad,txtTeam2Squad,txtToss;
    String flag1;
    String flag2;
    ListView infoList;
    String key;
    ProgressBar progressBar;
    String status;
    JSONObject obj = null;
    String team1;
    String team1_full;
    String id;
    String team2;
    String team2_full;
    int dbInstance;
    String connectionUrl="";
    TextView SereieName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matchinfo_fragment, container, false);
        this.team1 = getArguments().getString("team1");
        this.team2 = getArguments().getString("team2");
        this.id = getArguments().getString("id");
       // Log.e("match_id",id);
        this.team1_full = getArguments().getString("team1_full");
        this.team2_full = getArguments().getString("team2_full");
        this.flag1 = getArguments().getString("flag1");
        this.flag2 = getArguments().getString("flag2");
        this.key = getArguments().getString("key");
        this.status = getArguments().getString("status");


        try{
            obj = new JSONObject();
            try {
                obj.put("matchid", id);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

//        this.infoList = (ListView) view.findViewById(R.id.info_list);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        this.progressBar.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 21) {
            dbInstance = ThreadLocalRandom.current().nextInt(2, 11);
        } else {
            dbInstance = new Random().nextInt(9) + 2;
        }
        //connectionUrl = Global.LIVEMATCH_URL + dbInstance + ".firebaseio.com";
        connectionUrl = Global.LIVEMATCH_URL;
        ImageView Flagteam_1 = ((ImageView) view.findViewById(R.id.team1_flag));
        Glide.with(getActivity()).load(this.flag1).into(Flagteam_1);
        ImageView Flagteam_2 = ((ImageView) view.findViewById(R.id.team2_flag));
        Glide.with(getActivity()).load(this.flag2).into(Flagteam_2);

        SereieName = view.findViewById(R.id.series_name_match_info);
        ((TextView) view.findViewById(R.id.team1_name_short)).setText(team1);
        ((TextView) view.findViewById(R.id.team2_name_short)).setText(team2);
        ((TextView) view.findViewById(R.id.team1_name_full)).setText(team1_full);
        ((TextView) view.findViewById(R.id.team2_name_full)).setText(team2_full);

        FragmentManager manager = getFragmentManager();
        PitchTab3 pitchTab3 = new PitchTab3();
        assert manager != null;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frm_h2h,pitchTab3);
        transaction.commit();

        init(view);
        getInfo();

        return view;
    }


    private void init(View view){
        txt3rdUmpire = (TextView) view.findViewById(R.id.txt_third_umpire);
        txtCapacity = (TextView) view.findViewById(R.id.txt_capacity);
        txtCity = (TextView) view.findViewById(R.id.txt_city);
        txtEndTime = (TextView) view.findViewById(R.id.txt_time);
        txtHost = (TextView) view.findViewById(R.id.txt_host);
        txtMatch = (TextView) view.findViewById(R.id.txt_match);
        txtRefree = (TextView) view.findViewById(R.id.txt_referee);
        txtSeries = (TextView) view.findViewById(R.id.txt_series);
        txtStadium = (TextView) view.findViewById(R.id.txt_stadium);
        txtStartTime = (TextView) view.findViewById(R.id.txt_date);
        txtStatus = (TextView) view.findViewById(R.id.txt_status);
        txtUmpires = (TextView) view.findViewById(R.id.txt_umpire);
        txtTeam1 = (TextView) view.findViewById(R.id.txt_match_team1);
        txtTeam2 = (TextView) view.findViewById(R.id.txt_match_team2);
        txtTeam1Squad = (TextView) view.findViewById(R.id.txt_team1_squad);
        txtTeam2Squad = (TextView) view.findViewById(R.id.txt_team2_squad);
        txtToss = (TextView) view.findViewById(R.id.txt_toss);

    }
    private void getInfo() {
        //Live URL
        //StringRequest request = new StringRequest(Request.Method.POST, (Global.JAVA_URL+"matchInfo"), new Response.Listener<String>() {
        StringRequest request = new StringRequest(Request.Method.POST, (Global.URL+"matchinfo.php"), new Response.Listener<String>() {
        //Local URL
       // StringRequest request = new StringRequest(Request.Method.POST, ("http://192.168.1.106:8083/api/"+"matchInfo"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject j = new JSONObject(response);
                    try {
                        JSONObject obj = j.getJSONObject("matchinfo");
                        if(obj.getString("umpire3").equalsIgnoreCase("null")){
                            txt3rdUmpire.setText("--");
                        }else{
                            txt3rdUmpire.setText(obj.getString("umpire3"));
                        }

                        String[] venue = obj.getString("venue").split(",");
                        txtCity.setText(venue[1]);
                        txtHost.setText(venue[2]);
                        txtStadium.setText(venue[0]);
                        if (obj.has("capacity")) txtCapacity.setText(obj.getString("capacity"));
                        else txtCapacity.setText("--");

                        txtMatch.setText(obj.getString("matchdesc"));
                        if(obj.getString("referee").equalsIgnoreCase("null")){
                            txtRefree.setText("--");
                        }else{
                            txtRefree.setText(obj.getString("referee"));
                        }
                       if(obj.getString("startdt").equalsIgnoreCase("null")){
                           txtStartTime.setText("--");
                       }else{
                           txtStartTime.setText(obj.getString("startdt") +" at "+obj.getString("starttime"));
                       }

                        txtSeries.setText(obj.getString("seriesname"));
                        SereieName.setText(obj.getString("seriesname"));
                        txtStatus.setText(obj.getString("status"));


                        if (!obj.has("umpire2")){
                            if(obj.getString("umpire1").equalsIgnoreCase("null")){
                                txtUmpires.setText("--");
                            }else{
                                txtUmpires.setText(obj.getString("umpire1"));
                            }
                        }


                        else if (!obj.has("umpire1")){
                            if(obj.getString("umpire2").equalsIgnoreCase("null")){
                                txtUmpires.setText("--");
                            }else{
                                txtUmpires.setText(obj.getString("umpire2"));
                            }

                        }
                        else{
                            if(obj.getString("umpire1").equalsIgnoreCase("null") && obj.getString("umpire2").equalsIgnoreCase("null")){
                                txtUmpires.setText("--");
                            }
                            else if(obj.getString("umpire1").equalsIgnoreCase("null")){
                                txtUmpires.setText(obj.getString("umpire2"));
                            }else if(obj.getString("umpire2").equalsIgnoreCase("null")){
                                txtUmpires.setText(obj.getString("umpire1"));
                            }else{
                                txtUmpires.setText(obj.getString("umpire1")+" , "+obj.getString("umpire2"));
                            }

                        }

                        if(obj.getString("tosswinner").equalsIgnoreCase("null") || obj.getString("tosswinner").equalsIgnoreCase("")){
                            txtToss.setText("--");
                        }else{
                            txtToss.setText(obj.getString("tosswinner")+" opt to "+obj.getString("tossdecision"));
                        }



                        JSONObject squad = j.getJSONObject("squad");

                        //JSONObject Team1 = squad.getJSONObject(team1.trim().toUpperCase());
                        //JSONObject Team2 = squad.getJSONObject(team2.trim().toUpperCase());
                        JSONObject Team1 = squad.getJSONObject("0");
                        team1_full = Team1.getString("teamfullname");
                        JSONObject Team2 = squad.getJSONObject("1");
                        team2_full = Team2.getString("teamfullname");

                        if(obj.getString("tosswinner").equalsIgnoreCase("null") || obj.getString("tosswinner").equalsIgnoreCase("")){
                            txtTeam1.setText(team1_full +"\n"+"Playing XV");
                            txtTeam1Squad.setText(Team1.getString("squad1")+" , "+Team1.getString("squad2")+" , "+Team1.getString("squad3")+" , "+Team1.getString("squad4")+" , "+Team1.getString("squad5")+" , "+Team1.getString("squad6")+" , "+Team1.getString("squad7")+" , "+Team1.getString("squad8")+" , "+Team1.getString("squad9")+" , "+Team1.getString("squad10")+" , "+Team1.getString("squad11")+" , "+Team1.getString("squad12")+" , "+Team1.getString("squad13")+" , "+Team1.getString("squad14")+" , "+Team1.getString("squad15"));

                            txtTeam2.setText(team2_full+"\n"+"Playing XV");
                            txtTeam2Squad.setText(Team2.getString("squad1")+" , "+Team2.getString("squad2")+" , "+Team2.getString("squad3")+" , "+Team2.getString("squad4")+" , "+Team2.getString("squad5")+" , "+Team2.getString("squad6")+" , "+Team2.getString("squad7")+" , "+Team2.getString("squad8")+" , "+Team2.getString("squad9")+" , "+Team2.getString("squad10")+" , "+Team2.getString("squad11")+" , "+Team2.getString("squad12")+" , "+Team2.getString("squad13")+" , "+Team2.getString("squad14")+" , "+Team2.getString("squad15"));
                        }else{
                            txtTeam1.setText(team1_full +"\n"+"Playing XI");
                            txtTeam1Squad.setText(Team1.getString("squad1")+" , "+Team1.getString("squad2")+" , "+Team1.getString("squad3")+" , "+Team1.getString("squad4")+" , "+Team1.getString("squad5")+" , "+Team1.getString("squad6")+" , "+Team1.getString("squad7")+" , "+Team1.getString("squad8")+" , "+Team1.getString("squad9")+" , "+Team1.getString("squad10")+" , "+Team1.getString("squad11"));

                            txtTeam2.setText(team2_full+"\n"+"Playing XI");
                            txtTeam2Squad.setText(Team2.getString("squad1")+" , "+Team2.getString("squad2")+" , "+Team2.getString("squad3")+" , "+Team2.getString("squad4")+" , "+Team2.getString("squad5")+" , "+Team2.getString("squad6")+" , "+Team2.getString("squad7")+" , "+Team2.getString("squad8")+" , "+Team2.getString("squad9")+" , "+Team2.getString("squad10")+" , "+Team2.getString("squad11"));
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);


                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();
                //params.put("matchid", Match_Id);
                params.put("matchid", id);
                params.put("type","squad");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(request);
    }
}


