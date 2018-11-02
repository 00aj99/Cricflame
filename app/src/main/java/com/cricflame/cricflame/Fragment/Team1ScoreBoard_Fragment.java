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

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.Bowling_adapter;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.ScoreboardData;
import com.cricflame.cricflame.Adapter.Batting_adapter;
import com.cricflame.cricflame.Adapter.Fall_Wicket_adapter;
import com.cricflame.cricflame.R;

import com.cricflame.cricflame.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Deepak Sharma on 10/10/2017.
 */

public class Team1ScoreBoard_Fragment extends Fragment {
    public  ArrayList<ScoreboardData> scoreBatArrayList=new ArrayList<>();
    public  ArrayList<ScoreboardData> scoreBatArrayList1=new ArrayList<>();
    public  ArrayList<ScoreboardData> scoreBowlArrayList=new ArrayList<>();
    public  ArrayList<ScoreboardData> scoreBowlArrayList1=new ArrayList<>();
    public  ArrayList<ScoreboardData> fallWicketArrayList=new ArrayList<>();
    public  ArrayList<ScoreboardData> fallWicketArrayList1=new ArrayList<>();
    ListView batList,bowlList,fall_wicket;
    Batting_adapter batting_adapter;
    Bowling_adapter bowling_adapter;
    Fall_Wicket_adapter fall_wicket_adapter;
    LinearLayout battingLayout,bowling_layout,wicket_layout,extra_layout;
    TextView extra,total,nextBat,title;
    Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_fragment_layout, container, false);
        batList= (ListView) view.findViewById(R.id.listView1);
        bowlList= (ListView) view.findViewById(R.id.listView2);
        extra= (TextView) view.findViewById(R.id.extra);
        total= (TextView) view.findViewById(R.id.total);
        nextBat= (TextView) view.findViewById(R.id.next_bat);
        fall_wicket= (ListView) view.findViewById(R.id.listView3);
        battingLayout= (LinearLayout) view.findViewById(R.id.layout_batting);
        bowling_layout= (LinearLayout) view.findViewById(R.id.layout_bowling);
        extra_layout= (LinearLayout) view.findViewById(R.id.extra_layout);
        wicket_layout= (LinearLayout) view.findViewById(R.id.layout_wicket);
        title= (TextView) view.findViewById(R.id.score_title);
        this.mHandler = new Handler();


        getTeam1ScoreBoard();

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
            getTeam1ScoreBoard();

          //  Team1ScoreBoard_Fragment.this.mHandler.postDelayed(m_Runnable, 5000);
        }

    };


    public void getTeam1ScoreBoard(){
        scoreBatArrayList.clear();
        scoreBowlArrayList.clear();
        fallWicketArrayList.clear();




        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"livematchscore.php?id="+ MainActivity.MatchId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
           //         Log.e("Response",""+response.toString());
                    //JSONObject data=response.getJSONObject("data");

                    JSONObject teams=response.getJSONObject("data");
                    JSONObject scorecard=teams.getJSONObject("scorecard");
                    JSONObject team1score=null;

                    if (teams.getString("match_type").equals("TEST")) {
                        String inning= Scoreboard_Fragment.team_score;
                        if (inning.equals("1")){
                            team1score=scorecard.getJSONObject("team1_f");
                        }
                        else if (inning.equals("2")){
                            team1score=scorecard.getJSONObject("team2_f");
                        }
                    }
                    else{
                        team1score=scorecard.getJSONObject("team1_f");
                    }
                    title.setText(teams.getString("status"));
                        extra.setText(team1score.getString("extras"));
                        total.setText(team1score.getString("total")+"  RR :"+team1score.getString("rr"));
                        nextBat.setText(team1score.getString("next_bat"));
                    JSONArray team1BatArray=team1score.getJSONArray("batting");


                    JSONObject batCheck=team1BatArray.getJSONObject(0);
                    if (batCheck.getString("batsman").equals("")&& batCheck.getString("run").equals("")&& batCheck.getString("ball").equals("")&&batCheck.getString("outdesc").equals("")&&batCheck.getString("strike_rate").equals("")){
                        battingLayout.setVisibility(View.GONE);
                        extra_layout.setVisibility(View.GONE);

                    }
                    else {
                        for (int j = 0; j < team1BatArray.length(); j++) {
                            JSONObject jsonObject1 = team1BatArray.getJSONObject(j);
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

                            scoreBatArrayList.add(scoreData);
                        }
                    }
                    JSONArray team1BowlArray=team1score.getJSONArray("bowling");

                    JSONObject bowlCheck=team1BowlArray.getJSONObject(0);
                    if (bowlCheck.getString("bowler").equals("")&&bowlCheck.getString("over").equals("")&&bowlCheck.getString("run").equals("")&&bowlCheck.getString("wicket").equals("")&&bowlCheck.getString("economy").equals("")){
                        bowling_layout.setVisibility(View.GONE);


                    }
                    else {

                        for (int i = 0; i < team1BowlArray.length(); i++) {
                            JSONObject jsonObject1 = team1BowlArray.getJSONObject(i);
                            ScoreboardData scoreData = new ScoreboardData();
                            //  squadsData.setId(jsonObject1.getString("id"));
                            scoreData.setBowler(jsonObject1.getString("bowler"));
                            scoreData.setOver(jsonObject1.getString("over"));
                            scoreData.setMaiden(jsonObject1.getString("maiden"));
                            scoreData.setBowl_run(jsonObject1.getString("run"));
                            scoreData.setWicket(jsonObject1.getString("wicket"));
                            scoreData.setEconomy(jsonObject1.getString("economy"));
                           // teamTwo = jsonObject1.getString("team");
                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            scoreBowlArrayList.add(scoreData);

                        }
                    }
                    JSONArray team1WicketArray=team1score.getJSONArray("fall_of_wic");
                    JSONObject wicCheck=team1WicketArray.getJSONObject(0);
                    if (wicCheck.getString("name").equals("")&& wicCheck.getString("over").equals("")&& wicCheck.getString("score").equals("")){
                        wicket_layout.setVisibility(View.GONE);

                    }
                    else {

                        for (int i = 0; i < team1WicketArray.length(); i++) {
                            JSONObject jsonObject1 = team1WicketArray.getJSONObject(i);
                            ScoreboardData scoreData = new ScoreboardData();
                            //  squadsData.setId(jsonObject1.getString("id"));
                            scoreData.setName(jsonObject1.getString("name"));
                            scoreData.setOver_out(jsonObject1.getString("over"));
                            scoreData.setScore(jsonObject1.getString("score"));

                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            fallWicketArrayList.add(scoreData);
                        }
                    }

                    batting_adapter=new Batting_adapter(getActivity(),scoreBatArrayList);
                    batList.setAdapter(batting_adapter);
                    batting_adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(batList);
                    bowling_adapter=new Bowling_adapter(getActivity(),scoreBowlArrayList);
                    bowlList.setAdapter(bowling_adapter);
                    bowling_adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(bowlList);
                    fall_wicket_adapter=new Fall_Wicket_adapter(getActivity(),fallWicketArrayList);
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
        })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setShouldCache(false);

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }
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



}

