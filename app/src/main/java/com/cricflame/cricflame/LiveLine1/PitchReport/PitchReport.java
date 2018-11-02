package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.MainActivity;
import com.cricflame.cricflame.Model.LiveMatchDetails;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Notification.Model.Config;
import com.cricflame.cricflame.Notification.Utils.NotificationUtils;
import com.cricflame.cricflame.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PitchReport extends AppCompatActivity {
    ImageView back;
    TabLayout tabLayout;
    public static String status="";
    public static String team1_score="";
    public static String team2_score="";
    public static String country1="";
    public static String country2="";
    public static String country1_image="";
    public static String country2_image="";
    private ViewPager viewPager;
    RequestQueue requestQueue;
    String connectionUrl;
    FirebaseDatabase firebaseDatabase=null;
    ImageView flag1,flag2;
    String Key,Match_Status;
    LinearLayout Odds_Card_Lay;
    View White_Seperator;
    TextView Full_NameTeam1,Full_NameTeam2;
    TextView team1Name,team2Name,scoreTeam1,scoreteam2,over1,over2,odds1,odds2,topcomment,venue_match,matchStatus,bottomcomment,Card_rate_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_report);

        Key = getIntent().getExtras().getString("key");

        this.requestQueue = Volley.newRequestQueue(this);

        try{
            connectionUrl = Global.LIVEMATCH_URL;
            firebaseDatabase = FirebaseDatabase.getInstance(connectionUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
       // new AsyncCurrentMatch().execute();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("H2H"));
        tabLayout.addTab(tabLayout.newTab().setText("Pitch Report"));
        tabLayout.addTab(tabLayout.newTab().setText("STATS"));
        tabLayout.addTab(tabLayout.newTab().setText("RECORD"));
        tabLayout.addTab(tabLayout.newTab().setText("DID YOU KNOW"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        PitchReportPager adapter = new PitchReportPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        flag1 =  findViewById(R.id.team1_card_logo_image_view);
        flag2 =  findViewById(R.id.team2_card_logo_image_view);
        team1Name = (TextView) findViewById(R.id.team1_name_card);
        team2Name = (TextView) findViewById(R.id.team2_name_card);
        scoreTeam1 = (TextView) findViewById(R.id.score1_card);
        scoreteam2 = (TextView) findViewById(R.id.score2_card);
        over1 = (TextView) findViewById(R.id.over1_card);
        over2 = (TextView) findViewById(R.id.over2_card);
        odds1 = (TextView) findViewById(R.id.odds1_card);
        odds2 = (TextView) findViewById(R.id.odds2_card);
        topcomment = (TextView) findViewById(R.id.top_match_card_details);
        venue_match = (TextView) findViewById(R.id.venue_view);
        matchStatus = (TextView) findViewById(R.id.match_card_status);
        bottomcomment = (TextView) findViewById(R.id.bottom_match_card_details);
        Odds_Card_Lay =  ((LinearLayout) findViewById(R.id.odds_card_layout));
        White_Seperator = findViewById(R.id.white_separator);
        Card_rate_team = findViewById(R.id.card_rate_team);
        Full_NameTeam1 = findViewById(R.id.full_team1_name_card);
        Full_NameTeam2 = findViewById(R.id.full_team2_name_card);


        getLiveMatches();
    }

    public void getLiveMatches() {
        //requestQueue.add(new JsonObjectRequest(connectionUrl+"/liveMatches.json", null, new Response.Listener<JSONObject>() {
        requestQueue.add(new JsonObjectRequest(connectionUrl+"/liveMatchWithCricExch/"+Key+".json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject match) {

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
                                   /* if(!rate2.equalsIgnoreCase("0")){
                                        if(rate2.contains(".")){
                                            rate2 = String.valueOf(Double.parseDouble(rate2) + Double.parseDouble("1.00"));
                                        }else{
                                            rate2 = String.valueOf(Integer.parseInt(rate2) + Integer.parseInt("1"));
                                        }
                                    }*/

                            String rate_team = match.getString("rate_team");
                            String score = match.getString(FirebaseAnalytics.Param.SCORE);
                            String score2 = match.getString("score2");
                            String series_name = match.getString("series_name");
                            Match_Status = match.getString("status");
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
                            String id  = match.getString("id");
                            String ScoreCardid = match.getString("id1");
                            String IsCricBuzz = match.getString("cb");
                            String EventId = match.getString("eventid");
                            if(IsCricBuzz.equalsIgnoreCase("0")){
                                if (inning.trim().equals("2")) {
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
                            String i1="";
                            String i2="";
                            String i3="";
                            if(type.equalsIgnoreCase("1")){
                                if (match.has("i1")) i1 = match.getString("i1");
                                else i1 = "0";

                                if (match.has("i2"))i2 = match.getString("i2");
                                else i2 = "0";

                                if (match.has("i3")) i3  = match.getString("i3");
                                else i3 = "0";
                            }

                            String IsSuperOver = "0";
                            if(match.has("isSuperOver")){
                                IsSuperOver = match.getString("isSuperOver");
                            }

                            setListener();

                            try {
                                if (!str.trim().equals("")) {
                                    //flag1.setImageURI(Uri.parse(live.get(position).getFlag1()));
                                    if (!PitchReport.this.isDestroyed()) {
                                        Glide.with(getApplicationContext()).load(str).into(flag1);
                                    }

                                }
                                if (!str2.trim().equals("")) {
                                    //flag2.setImageURI(Uri.parse(live.getFlag2()));
                                    if (!PitchReport.this.isDestroyed()) {
                                        Glide.with(getApplicationContext()).load(str2).into(flag2);
                                    }

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(series_name);
                            spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 33);
                            topcomment.setText(spannableStringBuilder);
                            bottomcomment.setText(comment);
                            if (type.trim().equals("1") || rate.trim().equals("") || (Double.parseDouble(rate) == 0.0d && Double.parseDouble(rate2) == 0.0d)) {
                                Odds_Card_Lay.setBackground(PitchReport.this.getResources().getDrawable(R.drawable.transparent));
                                odds1.setText("");
                                odds2.setText("");
                               White_Seperator.setVisibility(8);
                               Card_rate_team.setText(" ");
                            } else {
                                odds1.setText(rate);
                                odds2.setText(rate2);
                                Card_rate_team.setText(rate_team);
                            }
                            venue_match.setText(match_number);
                            if (Match_Status.equals("0")) {
                                matchStatus.setText("UPCOMING");
                                over1.setVisibility(8);
                                over2.setVisibility(8);
                                scoreTeam1.setVisibility(8);
                                scoreteam2.setVisibility(8);
                                Full_NameTeam1.setVisibility(0);
                                Full_NameTeam1.setText(team1);
                                Full_NameTeam2.setVisibility(0);
                                Full_NameTeam2.setText(team2);
                                team1Name.setText(t1);
                                team2Name.setText(t2);
                            } else {
                                if (Match_Status.equals("1")) {
                                    matchStatus.setText("LIVE");
                                } else {
                                    matchStatus.setText("RESULT");
                                }
                                team1Name.setText(t1);
                                team2Name.setText(t2);
                                if (inning.equals("1")) {
                                    if(type.equalsIgnoreCase("1")){
                                        if(i2.equalsIgnoreCase("0") || i2.equalsIgnoreCase("")){
                                            scoreTeam1.setText(score + "/" + wicket + (wicket.trim().equals("10") ? "" : "*"));
                                            scoreteam2.setText("- / -");
                                            if(ballsdone.equalsIgnoreCase("")){
                                                over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                            }else{
                                                over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                            }

                                            over2.setText("Yet to bat");

                                        }else{
                                            if(IsSuperOver.equalsIgnoreCase("1")){
                                                scoreTeam1.setText(score + "/" + wicket + (wicket.trim().equals("10") ? "" : "*"));
                                                scoreteam2.setText(score2 + "/" + wicket2);
                                                if(ballsdone.equalsIgnoreCase("")){
                                                    over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                }else{
                                                    over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                                }

                                                Full_NameTeam1.setText("Inn 1 - "+i1);
                                                Full_NameTeam1.setVisibility(0);
                                                Full_NameTeam2.setText("Inn 2 - "+i2);
                                                Full_NameTeam2.setVisibility(0);
                                                if(ballsdone2.equalsIgnoreCase("")){
                                                    over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                }else{
                                                    over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                                }
                                            } else{
                                                Full_NameTeam1.setText("Inn 1 - "+i1);
                                                Full_NameTeam1.setVisibility(0);
                                                Full_NameTeam2.setText("Inn 2 - "+i2);
                                                Full_NameTeam2.setVisibility(0);
                                                scoreTeam1.setText(score + "/" + wicket + (wicket.trim().equals("10") ? "" : "*"));
                                                scoreteam2.setText("- / -");
                                                if(ballsdone.equalsIgnoreCase("")){
                                                    over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                }else{
                                                    over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                                }


                                                over2.setText("Yet to bat");
                                            }

                                        }
                                    }else{
                                        scoreTeam1.setText(score + "/" + wicket + (wicket.trim().equals("10") ? "" : "*"));
                                        scoreteam2.setText("- / -");
                                        if(ballsdone.equalsIgnoreCase("")){
                                            over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                        }else{
                                            over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                        }

                                        over2.setText("Yet to bat");
                                    }


                                } else {
                                    if(type.equalsIgnoreCase("1")) {
                                        if (i3.equalsIgnoreCase("0") && i2.equalsIgnoreCase("0")) {
                                            scoreTeam1.setText(score + "/" + wicket);
                                            scoreteam2.setText(score2 + "/" + wicket2 + (wicket2.trim().equals("10") ? "" : "*"));
                                            if(ballsdone.equalsIgnoreCase("")){
                                                over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                            }else{
                                                over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                            }

                                            if(ballsdone2.equalsIgnoreCase("")){
                                                over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                            }else{
                                                over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                            }
                                        }else{
                                            if(IsCricBuzz.equalsIgnoreCase("0")){

                                                if(i2.equalsIgnoreCase("0")){
                                                    scoreTeam1.setText(score + "/" + wicket);
                                                    scoreteam2.setText(score2 + "/" + wicket2 + (wicket2.trim().equals("10") ? "" : "*"));

                                                    if(ballsdone.equalsIgnoreCase("")){
                                                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                    }else{
                                                        over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                                    }

                                                    if(ballsdone2.equalsIgnoreCase("")){
                                                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                    }else{
                                                        over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                                    }
                                                }else if(!i3.equalsIgnoreCase("0") && (!i2.equalsIgnoreCase("0"))){
                                                    scoreTeam1.setText(i3);
                                                    Full_NameTeam2.setText("Inn 2 - "+i2);
                                                    Full_NameTeam2.setVisibility(0);
                                                    scoreteam2.setText(score2 + "/" + wicket2 + (wicket2.trim().equals("10") ? "" : "*"));
                                                    if(ballsdone.equalsIgnoreCase("")){
                                                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                    }else{
                                                        over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                                    }

                                                    if(ballsdone2.equalsIgnoreCase("")){
                                                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                    }else{
                                                        over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                                    }


                                                    Full_NameTeam2.setVisibility(0);

                                                } else{
                                                    scoreTeam1.setText(score + "/" + wicket2 + (wicket.trim().equals("10") ? "" : "*"));
                                                    scoreteam2.setText(i2);

                                                    if(ballsdone.equalsIgnoreCase("")){
                                                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                    }else{
                                                        over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                                    }

                                                    if(ballsdone2.equalsIgnoreCase("")){
                                                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                    }else{
                                                        over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                                    }
                                                    over2.setVisibility(View.GONE);
                                                }



                                                Full_NameTeam1.setText("Inn 1 - "+i1);
                                                Full_NameTeam1.setVisibility(0);
                                                Full_NameTeam2.setText("Inn 2 - "+i2);
                                                //((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);

                                            }else{
                                                scoreTeam1.setText(score + "/" + wicket);
                                                scoreteam2.setText(score2 + "/" + wicket2 + (wicket2.trim().equals("10") ? "" : "*"));
                                                if(ballsdone.equalsIgnoreCase("")){
                                                    over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                }else{
                                                    over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                                }

                                                Full_NameTeam1.setText("Inn 1 - "+i1);
                                                Full_NameTeam1.setVisibility(0);
                                                Full_NameTeam2.setText("Inn 1 - "+i2);
                                                Full_NameTeam2.setVisibility(0);
                                                if(ballsdone2.equalsIgnoreCase("")){
                                                    over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                                }else{
                                                    over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                                }
                                            }


                                        }
                                    }else{
                                        scoreTeam1.setText(score + "/" + wicket);
                                        scoreteam2.setText(score2 + "/" + wicket2 + (wicket2.trim().equals("10") ? "" : "*"));
                                        if(ballsdone.equalsIgnoreCase("")){
                                            over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                        }else{
                                            over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                                        }

                                        if(ballsdone2.equalsIgnoreCase("")){
                                            over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                        }else{
                                            over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                                        }

                                    }

                                    if (comment.trim().equals("")) {
                                        try{
                                            bottomcomment.setText((Integer.parseInt(target) - Integer.parseInt(score2)) + " runs needed in " + (Integer.parseInt(totalballs) - Integer.parseInt(ballsdone2)) + " balls");
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }

                            //MainActivity.this.setListener(liveMatchDetails);
                            //Has to Implement Later
                            // MainActivity.this.setListener(liveMatchDetails);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }

                }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void setListener() {
        if (Match_Status.trim().equals("1")) {
            ValueEventListener valueEventListener = new C13818();
            if(firebaseDatabase!=null){
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("liveMatchWithCricExch").child(Key);
                databaseReference.addValueEventListener(valueEventListener);
            }
        }
    }

    class C13818 implements ValueEventListener {
        C13818() {
        }
        public void onDataChange(DataSnapshot dataSnapshot) {
            final String key = dataSnapshot.getKey();
            String ballsdone = dataSnapshot.child("ballsdone").getValue().toString();
            String ballsdone2 = dataSnapshot.child("ballsdone2").getValue().toString();
            String comment = dataSnapshot.child("comment").getValue().toString();
            String date = dataSnapshot.child("date").getValue().toString();
            String str = "";
            String str2 = "";
            String target = "";
            String totalballs = "";
            try {
                str = dataSnapshot.child("flag1").getValue().toString();
                str2 = dataSnapshot.child("flag2").getValue().toString();
                target = dataSnapshot.child("target").getValue().toString();
                totalballs = dataSnapshot.child("total_balls").getValue().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String inning = dataSnapshot.child("inning").getValue().toString();
            String match_number = dataSnapshot.child("match_number").getValue().toString();
            String rate = dataSnapshot.child("rate").getValue().toString();
            String rate2 = dataSnapshot.child("rate2").getValue().toString();
               /* if(!rate2.equalsIgnoreCase("0")){
                    if(rate2.contains(".")){
                        rate2 = String.valueOf(Double.parseDouble(rate2) + Double.parseDouble("1.00"));
                    }else{
                        rate2 = String.valueOf(Integer.parseInt(rate2) + Integer.parseInt("1"));
                    }
                }*/

            String rate_team = dataSnapshot.child("rate_team").getValue().toString();
            String score = dataSnapshot.child(FirebaseAnalytics.Param.SCORE).getValue().toString();
            String score2 = dataSnapshot.child("score2").getValue().toString();
            String series_name = dataSnapshot.child("series_name").getValue().toString();
            String status = dataSnapshot.child("status").getValue().toString();
            String t1 = dataSnapshot.child("t1").getValue().toString();
            String t2 = dataSnapshot.child("t2").getValue().toString();
            String team1 = dataSnapshot.child("team1").getValue().toString();
            String team2 = dataSnapshot.child("team2").getValue().toString();
            String title = dataSnapshot.child("title").getValue().toString();
            String type = dataSnapshot.child("type").getValue().toString();
            String venue = dataSnapshot.child("venue").getValue().toString();
            String wicket = dataSnapshot.child("wicket").getValue().toString();
            String wicket2 = dataSnapshot.child("wicket2").getValue().toString();
            String order = dataSnapshot.child("order").getValue().toString();
            String id = dataSnapshot.child("id").getValue().toString();
            String ScoreCardid = dataSnapshot.child("id1").getValue().toString();
            String IsCricBuzz = dataSnapshot.child("cb").getValue().toString();
            String EventId = dataSnapshot.child("eventid").getValue().toString();
            if(IsCricBuzz.equalsIgnoreCase("0")){
                if (inning.trim().equals("2")) {
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

            try{
                if (!str.trim().equals("")) {
                    //flag1.setImageURI(Uri.parse(live.get(position).getFlag1()));
                    if (!PitchReport.this.isDestroyed()) {
                        Glide.with(getApplicationContext()).load(str).into(flag1);
                    }

                }
                if (!str2.trim().equals("")) {
                    //flag2.setImageURI(Uri.parse(live.getFlag2()));
                    if (!PitchReport.this.isDestroyed()) {
                        Glide.with(getApplicationContext()).load(str2).into(flag2);
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(series_name);
            spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 33);
            topcomment.setText(spannableStringBuilder);
            bottomcomment.setText(comment);
            if (type.trim().equals("1") || rate.trim().equals("") || (Double.parseDouble(rate) == 0.0d && Double.parseDouble(rate2) == 0.0d)) {
                Odds_Card_Lay.setBackground(PitchReport.this.getResources().getDrawable(R.drawable.transparent));
                odds1.setText("");
                odds2.setText("");
                White_Seperator.setVisibility(8);
                Card_rate_team.setText(" ");
            } else {
                odds1.setText(rate);
                odds2.setText(rate2);
                Card_rate_team.setText(rate_team);
            }
            venue_match.setText(match_number);
            if (status.equals("0")) {
                matchStatus.setText("UPCOMING");
                over1.setVisibility(8);
                over2.setVisibility(8);
                scoreTeam1.setVisibility(8);
                scoreteam2.setVisibility(8);
                Full_NameTeam1.setVisibility(0);
                Full_NameTeam1.setText(team1);
                Full_NameTeam2.setVisibility(0);
                Full_NameTeam2.setText(team2);
                team1Name.setText(t1);
                team2Name.setText(t2);
            } else {
                if (status.equals("1")) {
                    matchStatus.setText("LIVE");
                } else {
                    matchStatus.setText("RESULT");
                }
                team1Name.setText(t1);
                team2Name.setText(t2);
                if (inning.equals("1")) {
                    scoreTeam1.setText(score + "/" + wicket + (wicket.trim().equals("10") ? "" : "*"));
                    scoreteam2.setText("- / -");
                    if (ballsdone.equalsIgnoreCase("")) {
                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                    } else {
                        over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                    }

                    over2.setText("Yet to bat");
                } else {
                    scoreTeam1.setText(score + "/" + wicket);
                    scoreteam2.setText(score2 + "/" + wicket2 + (wicket2.trim().equals("10") ? "" : "*"));
                    if (ballsdone.equalsIgnoreCase("")) {
                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                    } else {
                        over1.setText(ballsToOver(Integer.parseInt(ballsdone)) + " OVERS");
                    }

                    if (ballsdone2.equalsIgnoreCase("")) {
                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                    } else {
                        over2.setText(ballsToOver(Integer.parseInt(ballsdone2)) + " OVERS");
                    }

                    if (comment.trim().equals("")) {
                        try{
                            bottomcomment.setText((Integer.parseInt(target) - Integer.parseInt(score2)) + " runs needed in " + (Integer.parseInt(totalballs) - Integer.parseInt(ballsdone2)) + " balls");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

        }


        public void onCancelled(DatabaseError databaseError) {
        }
    }

        public static String ballsToOver(int balls) {
            return (balls / 6) + "." + (balls % 6);
        }


    protected void onResume() {
        super.onResume();

        // this.mHandler.postDelayed(m_Runnable,5000);
        //  this.mHandler.postDelayed(m_Runnable,5000);


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //    MainActivity.this.mHandler.removeCallbacks(m_Runnable);
        //  this.mHandler.removeMessages(0);

    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }


}