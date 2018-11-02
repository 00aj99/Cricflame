package com.cricflame.cricflame.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.Bowling_adapter;
import com.cricflame.cricflame.Adapter.Batting_adapter;
import com.cricflame.cricflame.Adapter.Fall_Wicket_adapter;
import com.cricflame.cricflame.Model.ScoreboardData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static com.cricflame.cricflame.Global.URL;
import static com.cricflame.cricflame.LiveLineScoreboard.team_score_live;

/**
 * Created by Deepak Sharma on 10/10/2017.
 */

public class Team2LiveScore extends Fragment {

    public  ArrayList<ScoreboardData> scoreBatArrayList1=new ArrayList<>();
    public  ArrayList<ScoreboardData> scoreBowlArrayList1=new ArrayList<>();
    public  ArrayList<ScoreboardData> fallWicketArrayList1=new ArrayList<>();
    ListView batList,bowlList,fall_wicket;
    Batting_adapter batting_adapter;
    Bowling_adapter bowling_adapter;
    Fall_Wicket_adapter fall_wicket_adapter;
    LinearLayout battingLayout,bowling_layout,wicket_layout,extra_layout,next_bat_layout;
    TextView extra,total,nextBat,title;
    Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_fragment_layout, container, false);
        batList= (ListView) view.findViewById(R.id.listView1);
        bowlList= (ListView) view.findViewById(R.id.listView2);
        fall_wicket= (ListView) view.findViewById(R.id.listView3);
        battingLayout= (LinearLayout) view.findViewById(R.id.layout_batting);
        bowling_layout= (LinearLayout) view.findViewById(R.id.layout_bowling);
        extra_layout= (LinearLayout) view.findViewById(R.id.extra_layout);
        wicket_layout= (LinearLayout) view.findViewById(R.id.layout_wicket);
        extra= (TextView) view.findViewById(R.id.extra);
        total= (TextView) view.findViewById(R.id.total);
        nextBat= (TextView) view.findViewById(R.id.next_bat);
        title= (TextView) view.findViewById(R.id.score_title);
        next_bat_layout= (LinearLayout) view.findViewById(R.id.next_bat_layout);
        next_bat_layout.setVisibility(View.GONE);

        this.mHandler = new Handler();

        getTeam2ScoreBoard();


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mHandler.removeCallbacks(m_Runnable);
        //  this.mHandler.removeMessages(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        this.mHandler.postDelayed(m_Runnable,5000);

    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            getTeam2ScoreBoard();

            Team2LiveScore.this.mHandler.postDelayed(m_Runnable, 5000);
        }

    };



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public void getTeam2ScoreBoard(){
        scoreBatArrayList1.clear();
        scoreBowlArrayList1.clear();
        fallWicketArrayList1.clear();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL+"liveline_scorecard.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");

                    JSONObject teams=response.getJSONObject("data");
                    JSONObject scorecard=teams.getJSONObject("scorecard");
                    JSONObject team2score=null;
                    if (teams.getString("match_type").equals("TEST")) {
                        String inning=team_score_live;
                        if (inning.equals("1")){
                            team2score=scorecard.getJSONObject("team1_s");
                        }
                        else if (inning.equals("2")){
                            team2score=scorecard.getJSONObject("team2_s");
                        }
                    }
                    else{
                        team2score=scorecard.getJSONObject("team2_f");
                    }


                    title.setText(teams.getString("status"));
                    extra.setText(team2score.getString("extras"));
                    total.setText(team2score.getString("total"));
                  //  nextBat.setText(team2score.getString("next_bat"));

                    JSONArray team2BatArray=team2score.getJSONArray("batting");
                    JSONObject batCheck=team2BatArray.getJSONObject(0);
                    if (batCheck.getString("batsman").equals("")&&batCheck.getString("run").equals("")&&batCheck.getString("ball").equals("")&&batCheck.getString("outdesc").equals("")&&batCheck.getString("strike_rate").equals("")){
                        battingLayout.setVisibility(View.GONE);
                        extra_layout.setVisibility(View.GONE);

                    }
                    else {

                        for (int j = 0; j < team2BatArray.length(); j++) {
                            JSONObject jsonObject1 = team2BatArray.getJSONObject(j);
                            ScoreboardData scoreData = new ScoreboardData();
                            //  squadsData.setId(jsonObject1.getString("id"));
                            scoreData.setBatsman(jsonObject1.getString("batsman"));
                            scoreData.setStatus(jsonObject1.getString("outdesc"));
                            scoreData.setBat_run(jsonObject1.getString("run"));
                            scoreData.setBat_ball(jsonObject1.getString("ball"));
                            scoreData.setFour(jsonObject1.getString("no_of_4"));
                            scoreData.setSix(jsonObject1.getString("no_of_6"));
                            scoreData.setStrike_rate(jsonObject1.getString("strike_rate"));


                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            scoreBatArrayList1.add(scoreData);
                        }
                    }
                    JSONArray team2BowlArray=team2score.getJSONArray("bowling");

                    JSONObject bowlCheck=team2BowlArray.getJSONObject(0);
                    if (bowlCheck.getString("bowler").equals("")&&bowlCheck.getString("over").equals("")&&bowlCheck.getString("run").equals("")&&bowlCheck.getString("wicket").equals("")&&bowlCheck.getString("economy").equals("")){
                        bowling_layout.setVisibility(View.GONE);


                    }
                    else {

                        for (int i = 0; i < team2BowlArray.length(); i++) {
                            JSONObject jsonObject1 = team2BowlArray.getJSONObject(i);
                            ScoreboardData scoreData = new ScoreboardData();
                            //  squadsData.setId(jsonObject1.getString("id"));
                            scoreData.setBowler(jsonObject1.getString("bowler"));
                            scoreData.setOver(jsonObject1.getString("over"));
                            scoreData.setMaiden(jsonObject1.getString("maiden"));
                            scoreData.setBowl_run(jsonObject1.getString("run"));
                            scoreData.setWicket(jsonObject1.getString("wicket"));
                            scoreData.setEconomy(jsonObject1.getString("economy"));

                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            scoreBowlArrayList1.add(scoreData);
                        }
                    }
                    JSONArray team2WicketArray=team2score.getJSONArray("fall_of_wic");
                    JSONObject wicCheck=team2WicketArray.getJSONObject(0);
                    if (wicCheck.getString("name").equals("")&&wicCheck.getString("over").equals("")&&wicCheck.getString("score").equals("")){
                        wicket_layout.setVisibility(View.GONE);
                    }
                    else {
                        for (int i = 0; i < team2WicketArray.length(); i++) {
                            JSONObject jsonObject1 = team2WicketArray.getJSONObject(i);
                            ScoreboardData scoreData = new ScoreboardData();
                            //  squadsData.setId(jsonObject1.getString("id"));
                            scoreData.setName(jsonObject1.getString("name"));
                            scoreData.setOver_out(jsonObject1.getString("over"));
                            scoreData.setScore(jsonObject1.getString("score"));

                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            fallWicketArrayList1.add(scoreData);
                        }
                    }

                    batting_adapter=new Batting_adapter(getActivity(),scoreBatArrayList1);
                    batList.setAdapter(batting_adapter);
                    batting_adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(batList);
                    bowling_adapter=new Bowling_adapter(getActivity(),scoreBowlArrayList1);
                    bowlList.setAdapter(bowling_adapter);
                    bowling_adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(bowlList);
                    fall_wicket_adapter=new Fall_Wicket_adapter(getActivity(),fallWicketArrayList1);
                    fall_wicket.setAdapter(fall_wicket_adapter);
                    fall_wicket_adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(fall_wicket);
                    // months.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }



}

