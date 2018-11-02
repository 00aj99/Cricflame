package com.cricflame.cricflame;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.LiveLineMatchAdapter;
import com.cricflame.cricflame.Model.LiveMatchDetails;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LiveLineActivity2 extends AppCompatActivity {

    private RecyclerView rcvLiveMatchList;
    private LiveLineMatchAdapter liveLineMatchAdapter;
    private ArrayList<LiveMatchDetails> liveMatchList;
    private RequestQueue requestQueue;
    private ArrayList<Pair<DatabaseReference, ValueEventListener>> pairsOfDatabaseandListener;
    private ImageView back;
    int dbInstance;
    LoadingDialog loadingDialog;
    String connectionUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_line2);
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        loadingDialog = new LoadingDialog(LiveLineActivity2.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        connectionUrl = Global.LIVEMATCH_URL;
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLiveMatches();
    }

    private void init(){
        this.requestQueue = Volley.newRequestQueue(this);
        this.pairsOfDatabaseandListener = new ArrayList<>();
        liveMatchList= new ArrayList<>();

        rcvLiveMatchList = findViewById(R.id.rcv_live_match_list);

    }

    public void getLiveMatches() {
        loadingDialog.show();
        this.requestQueue.add(new JsonObjectRequest(connectionUrl+"/liveMatchWithCricExch.json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                int i;
                /*if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    liveMatchviewPager.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.GONE);
                }*/
                Iterator objects = response.keys();
                LiveLineActivity2.this.liveMatchList.clear();
                for (i = 0; i < LiveLineActivity2.this.pairsOfDatabaseandListener.size(); i++) {
                    ((DatabaseReference) ((Pair) LiveLineActivity2.this.pairsOfDatabaseandListener.get(i)).first).removeEventListener((ValueEventListener) ((Pair) LiveLineActivity2.this.pairsOfDatabaseandListener.get(i)).second);
                }
                LiveLineActivity2.this.pairsOfDatabaseandListener.clear();
                while (objects.hasNext()) {
                    String key = objects.next().toString();
                    try {
                        JSONObject match = response.getJSONObject(key);
                        try {
                            String ballsdone = match.getString("ballsdone");
                            String ballsdone2 = match.getString("ballsdone2");
                            String comment = match.getString("comment");
                            //String date = match.getString(IndexEntry.COLUMN_NAME_DATE);
                            String date = match.getString("date");
                            String str = "";
                            String str2 = "";
                            String totalballs = "";
                            String target = "";
                            try {
                                str = match.getString("flag1");
                                str2 = match.getString("flag2");
                                target = match.getString("target");
                                totalballs = match.getString("total_balls");
                            } catch (JSONException e) {
                            }
                            String inning = match.getString("inning");
                            String match_number = match.getString("match_number");
                            String rate = match.getString("rate");
                            String rate2 = match.getString("rate2");
                            String rate_team = match.getString("rate_team");
                            String score = match.getString(FirebaseAnalytics.Param.SCORE);
                            String score2 = match.getString("score2");
                            String series_name = match.getString("series_name");
                            String status = match.getString("status");
                            String t1 = match.getString("t1");
                            String t2 = match.getString("t2");
                            String team1 = match.getString("team1");
                            String team2 = match.getString("team2");
                            String title = match.getString("title");
                            String type = match.getString("type");
                            String venue = match.getString("venue");
                            String wicket = match.getString("wicket");
                            String wicket2 = match.getString("wicket2");
                            String order = match.getString("order");
                            String id = match.getString("id");
                            String ScoreCardId = match.getString("id1");
                            String IsCricBuzz = match.getString("cb");
                            String EventId = match.getString("eventid");
                            String MarketId = match.getString("market_Id");

                            String i1="0";
                            String i2="0";
                            String i3="0";
                            if(type.equalsIgnoreCase("1")){
                                if (match.has("i1")) i1 = match.getString("i1");
                                else i1 = "";

                                if (match.has("i2"))i2 = match.getString("i2");
                                else i2 = "";

                                if (match.has("i3")) i3  = match.getString("i3");
                                else i3 = "";
                            }
                            if(IsCricBuzz.equalsIgnoreCase("0")){
                                if (inning.trim().equals("2")) {
                                    if(type.equalsIgnoreCase("0") || (i2.equalsIgnoreCase("0") || !i3.equalsIgnoreCase("0"))){
                                        String ballswap = ballsdone;
                                        ballsdone = ballsdone2;
                                        ballsdone2 = ballswap;
                                        String runswap = score;
                                        score = score2;
                                        score2 = runswap;
                                        String wicketswap = wicket;
                                        wicket = wicket2;
                                        wicket2 = wicketswap;
                                    }

                                }
                            }

                            String oddsType = match.getString("oddstype");
                            String SessionType = match.getString("sessiontype");
                            String IsSuperOver = "0";
                            if(match.has("isSuperOver")){
                                IsSuperOver = match.getString("isSuperOver");
                            }

                            LiveMatchDetails liveMatchDetails = new LiveMatchDetails(key, ballsdone, ballsdone2, comment, date, str, str2, inning, match_number, rate, rate2, rate_team, score, score2, series_name, status, t1, t2, team1, team2, title, type, venue, wicket, wicket2, target, totalballs, order,id, ScoreCardId,IsCricBuzz,EventId,MarketId,i1,i2,i3,oddsType,SessionType,IsSuperOver);
                            LiveLineActivity2.this.liveMatchList.add(liveMatchDetails);
                            //Has to Implement Later
                            //HomeActivity.this.setListener(liveMatchDetails);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    } catch (JSONException e22) {
                        e22.printStackTrace();
                    }
                }

                //For Removing Dublicate Values
                for (int in = 0; in <= liveMatchList.size() - 1; in++) {
                    for (int j = liveMatchList.size() - 1; j > in; j--) {
                        if (liveMatchList.get(j).getT1().trim().equalsIgnoreCase(liveMatchList.get(in).getT1().trim())
                                && liveMatchList.get(j).getT2().trim().equalsIgnoreCase(liveMatchList.get(in).getT2().trim())
                                && liveMatchList.get(j).getStatus().equalsIgnoreCase(liveMatchList.get(in).getStatus())) {
                            if(liveMatchList.get(j).getIsCricBuzz().equalsIgnoreCase("1")){
                                liveMatchList.remove(j);

                            }else{
                                liveMatchList.remove(in);
                                if(in == 0){
                                    j = liveMatchList.size();
                                }
                            }

                        }
                    }
                }
                //For Removing Dublicate Values


                Collections.sort(LiveLineActivity2.this.liveMatchList);
                Log.d("lcount", "listner live " + LiveLineActivity2.this.liveMatchList.size());
                liveLineMatchAdapter = new LiveLineMatchAdapter(LiveLineActivity2.this,liveMatchList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveLineActivity2.this,LinearLayoutManager.VERTICAL,false);
                rcvLiveMatchList.setLayoutManager(linearLayoutManager);
                rcvLiveMatchList.setAdapter(liveLineMatchAdapter);
                liveLineMatchAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
        try{
            if(loadingDialog.isShowing())
                loadingDialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
