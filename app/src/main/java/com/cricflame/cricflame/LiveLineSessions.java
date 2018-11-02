package com.cricflame.cricflame;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.LiveLineMatchAdapter;
import com.cricflame.cricflame.Adapter.LiveSession_Adapter;
import com.cricflame.cricflame.Adapter.MatchSessions_Adapter;
import com.cricflame.cricflame.Fragment.ScoreCardFragment;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.CricFlame_Session;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.Model.Session_Model;
import com.cricflame.cricflame.betfair.BetfairMarketPrice;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class LiveLineSessions extends AppCompatActivity {
    private ImageView back;
    RecyclerView Sessions, Sessions_Team2;
    MatchSessions_Adapter matchSessions_adapter;
    List<CricFlame_Session> SessionList;
    List<CricFlame_Session> SessionList_team2;
    String Team1,Team2,Innings,Key;
    RequestQueue requestQueue;
    DatabaseReference mRoofDb;
    TextView Team1Name, Team2Name;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_line_sessions);
        this.requestQueue = Volley.newRequestQueue(LiveLineSessions.this);
        mRoofDb = FirebaseUtils.getThirdDatabase(LiveLineSessions.this).getReference();
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadingDialog = new LoadingDialog(LiveLineSessions.this);

        Sessions = findViewById(R.id.sessions_recycler);
        Sessions_Team2 = findViewById(R.id.sessions_team2_recycler);
        Team1Name = findViewById(R.id.team1);
        Team2Name = findViewById(R.id.team2);
        Team2Name.setVisibility(8);
        Team1Name.setVisibility(8);
        Team1 = getIntent().getExtras().getString("t1");
        Team2 = getIntent().getExtras().getString("t2");
        Innings = getIntent().getExtras().getString("inning");
        Key = getIntent().getExtras().getString("key");
        GetSessiosnTeam1();
    }



    public void GetSessiosnTeam1() {
        loadingDialog.show();
        SessionList = new ArrayList<>();
        final String Ref = Team1.trim().toUpperCase();
        this.requestQueue.add(new JsonObjectRequest(Global.LIVEMATCH_URL+"/liveMatchSession/"+Key+"/"+Ref+".json", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Iterator objects = response.keys();
                while (objects.hasNext()) {
                    String key = objects.next().toString();
                    try{
                        JSONObject result = response.getJSONObject(key);
                        CricFlame_Session list = new CricFlame_Session();
                        if(result.has("o")){
                            list.setOver(result.getString("o"));
                            list.setRunScored(result.getString("runs"));
                            list.setMin(result.getString("smn"));
                            list.setMax(result.getString("smx"));
                            list.setOpen(result.getString("so"));
                            list.setRate_Team(Ref);
                            SessionList.add(list);
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                        if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                    }


                }


               if(SessionList.size()>0){
                    Team1Name.setVisibility(0);
                    Team1Name.setText(Team1);
                    Collections.sort(SessionList);
                   matchSessions_adapter = new MatchSessions_Adapter (LiveLineSessions.this,SessionList);
                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveLineSessions.this,LinearLayoutManager.VERTICAL,false);
                   Sessions.setLayoutManager(linearLayoutManager);
                   Sessions.setNestedScrollingEnabled(false);
                   Sessions.setAdapter(matchSessions_adapter);
                   Sessions.setNestedScrollingEnabled(false);
               }
                GetSessiosnTeam2();

                //if(loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if(loadingDialog.isShowing())
                loadingDialog.dismiss();
            }
        }));
    }

    public void GetSessiosnTeam2() {
        //loadingDialog.show();
        SessionList_team2 = new ArrayList<>();
        final String Ref = Team2.trim().toUpperCase();;
        this.requestQueue.add(new JsonObjectRequest(Global.LIVEMATCH_URL+"/liveMatchSession/"+Key+"/"+Ref+".json", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Iterator objects = response.keys();
                while (objects.hasNext()) {
                    String key = objects.next().toString();
                    try{
                        JSONObject result = response.getJSONObject(key);
                        CricFlame_Session list = new CricFlame_Session();
                        if(result.has("o")){
                            list.setOver(result.getString("o"));
                            list.setRunScored(result.getString("runs"));
                            list.setMin(result.getString("smn"));
                            list.setMax(result.getString("smx"));
                            list.setOpen(result.getString("so"));
                            list.setRate_Team(Ref);
                            SessionList_team2.add(list);
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                        if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                    }


                }


                if(SessionList_team2.size()>0){
                    Team2Name.setVisibility(0);
                    Team2Name.setText(Team2);
                    Collections.sort(SessionList_team2);
                    matchSessions_adapter = new MatchSessions_Adapter (LiveLineSessions.this,SessionList_team2);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveLineSessions.this,LinearLayoutManager.VERTICAL,false);
                    Sessions_Team2.setLayoutManager(linearLayoutManager);
                    Sessions_Team2.setNestedScrollingEnabled(false);
                    Sessions_Team2.setAdapter(matchSessions_adapter);
                    Sessions_Team2.setNestedScrollingEnabled(false);
                }

                if(loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if(loadingDialog.isShowing())
                loadingDialog.dismiss();
            }
        }));
    }



}
