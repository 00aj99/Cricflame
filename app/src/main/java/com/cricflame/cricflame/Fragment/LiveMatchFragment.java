package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.IslamicCalendar;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.LiveSession_Adapter;
import com.cricflame.cricflame.Adapter.LiveTieSessionAdapter;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.LiveLine1.Commentries.Commentary;
import com.cricflame.cricflame.LiveLine1.PitchReport.PitchReport;
import com.cricflame.cricflame.LiveLineSessions;
import com.cricflame.cricflame.Model.LiveMatchDetails;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.Session_Model;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.NewLiveMatchActivity;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.betfair.BetfairActivity;
import com.cricflame.cricflame.betfair.BetfairMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.droidsonroids.gif.GifImageView;


public class LiveMatchFragment extends Fragment {
    GifImageView Loader;
    static MediaPlayer mPlayer;
    boolean SessionSpeechOn;
    String TAG = "LiveMatchFragment.java";
    String active_player = "";
    String ballsdone = "";
    boolean ballupdateSpeechOn;
    String bowler = "";
    String comment = "";
    String comment2 = "";
    String connectionUrl = "";
    String currentball = "";
    int dbInstance;
    DatabaseReference drP0;
    DatabaseReference drP1;
    DatabaseReference drP2;
    DatabaseReference cricBuzz;
    DatabaseReference sessions;
    Handler h2;
    int f2034i = 0;
    String inning = "1";
    String key;
    String MatchStatus;
    boolean langHindi;
    String lastWicket = "";
    String lastball = "";
    String lastballs = "";
    String matchTitle;
    String needrun = "";
    String new_ss1 = "";
    String new_ss2 = "";
    String old_ss1 = "";
    String old_ss2 = "";
    String player1 = "";
    String player1_balls = "";
    String player1_four;
    String player1_run = "";
    String player1_six;
    String player1_strikeRate = "-";
    String player2 = "";
    String player2_balls = "";
    String player2_four;
    String player2_run = "";
    String player2_six;
    String player2_strikeRate = "-";
    String powerPlay;
    String rate = "";
    String rate2 = "";
    String rateD;
    String rateD2;
    String rateT;
    String rateT2;
    String rate_team = "";
    String rate_team2;
    String remaining_balls = "";
    Runnable runnable2;
    String overs;
    String score = "";
    String session = "";
    String session2 = "";
    String session_max = "";
    String session_min = "";
    String session_open = "";
    String session_over = "";
    String session_team = "";
    String thisball = "";
    TextToSpeech tts;
    int type;
    View f2035v;
    ValueEventListener velP0;
    ValueEventListener velP1;
    ValueEventListener velP2;
    ValueEventListener velCricBuzz;
    ValueEventListener SessionCricBuzz;
    ValueEventListener TargetListner;
    String wicket = "";
    SharedPreferences.Editor e;
    String CommentLive,Score1,Score2,Over1,Over2,Team1,Team2,ISCricBuzz;
    TextView Target_Txt;
    String flag1,flag2;
    Button Sessions,scorecard,pitchReport,CommentaryButton;
    RecyclerView Sessions_Rec,Tie_Session_Rec,Completed_Session_Rec;
    LiveSession_Adapter liveSession_adapter;
    LiveTieSessionAdapter liveTieSessionAdapter;
    List<Session_Model> SessionList;
    Handler mHandler;
    TextView txtLiveLineScore;
    RequestQueue requestQueue;
    RelativeLayout Lotus_Main_Lay,Tie_Match_Main,Complete_Match_Main;
    String EventId;
    DatabaseReference MatchCommentary;
    FirebaseDatabase firebaseDatabase;
    String token,MarketId;
    //int updateFlag = 0;
    Double sum = 0.0;String selectionId = "";Double sumBackLay = 0.0;String team1ID,team2ID,team3ID;String runnersCount = "";
    String i1,i2,i3,oddsType,SessionType;
    FirebaseDatabase ScoreRequestRef;
    String mRequestBody = null;
    Uri telegram,whatsapp,tips,youtube,betfair,apple,facebook,website,exchange,twitter;
    ImageView imvtelegram,imvwhatsapp,imvtips,imvyoutube,imvbetfair,imvapple,imvfacebook,imvplaystore,imvwebsite,imvexchange,imvtwitter;
    DatabaseReference HomeTarget;
    String Target,Status="";
    String ProjecteeScore = "", SuperOver="";
    JsonObjectRequest lotusRequest;


    class C09125 implements TextToSpeech.OnInitListener {
        C09125() {
        }

        public void onInit(int i) {
            if (i == -1) {
                Toast.makeText(LiveMatchFragment.this.getContext(), "Speech Engine could not be initialized.", 0).show();
            } else if (LiveMatchFragment.this.langHindi) {
                int r = LiveMatchFragment.this.tts.setLanguage(new Locale("hi"));
                LiveMatchFragment.this.tts.setSpeechRate(Float.parseFloat("0.85"));
                if (r == -1 || r == -2) {
                    LiveMatchFragment.this.tts.setLanguage(Locale.ENGLISH);
                }
            } else {
                LiveMatchFragment.this.tts.setLanguage(Locale.ENGLISH);
            }
        }
    }

    class C14379 implements Response.Listener<JSONObject> {
        C14379() {
        }

        public void onResponse(JSONObject dataSnapshot) {
            try {
                if(ISCricBuzz.equalsIgnoreCase("1")){
                    LiveMatchFragment.this.rate = dataSnapshot.getString("r");
                    LiveMatchFragment.this.rate2 = dataSnapshot.getString("r2");

                    if (LiveMatchFragment.this.type == 1) {
                        LiveMatchFragment.this.rateT = dataSnapshot.getString("rT");
                        LiveMatchFragment.this.rateT2 = dataSnapshot.getString("rT2");
                   /* if(!LiveMatchFragment.this.rateT2.equalsIgnoreCase("0")){
                        if(LiveMatchFragment.this.rateT2.contains(".")){
                            LiveMatchFragment.this.rateT2 = String.valueOf(Double.parseDouble(rateT2) + Double.parseDouble("1.00"));
                        }else{
                            LiveMatchFragment.this.rateT2 = String.valueOf(Integer.parseInt(rateT2) + Integer.parseInt("1"));
                        }
                    }*/

                        LiveMatchFragment.this.rateD = dataSnapshot.getString("rD");
                        LiveMatchFragment.this.rateD2 = dataSnapshot.getString("rD2");
                    /*if(!LiveMatchFragment.this.rateD2.equalsIgnoreCase("0") || !LiveMatchFragment.this.rateD2.equalsIgnoreCase("")){
                        if(LiveMatchFragment.this.rateD2.contains(".")){
                            LiveMatchFragment.this.rateD2 = String.valueOf(Double.parseDouble(rateD2) + Double.parseDouble("1.00"));
                        }else{
                            LiveMatchFragment.this.rateD2 = String.valueOf(Integer.parseInt(rateD2) + Integer.parseInt("1"));
                        }
                    }
*/
                    }
                }

               /* if(!LiveMatchFragment.this.rate2.equalsIgnoreCase("0")){
                    if(LiveMatchFragment.this.rate2.contains(".")){
                        LiveMatchFragment.this.rate2 = String.valueOf(Double.parseDouble(rate2) + Double.parseDouble("1.00"));
                    }else{
                        LiveMatchFragment.this.rate2 = String.valueOf(Integer.parseInt(rate2) + Integer.parseInt("1"));
                    }
                }*/


                if(ISCricBuzz.equalsIgnoreCase("1") || ((ISCricBuzz.equalsIgnoreCase("0") && SessionType.equalsIgnoreCase("cricexch")))) {
                    LiveMatchFragment.this.session = dataSnapshot.getString("sn");
                    LiveMatchFragment.this.session2 = dataSnapshot.getString("sn2");
                    LiveMatchFragment.this.session_min = dataSnapshot.getString("s_mi");
              /*  if(!LiveMatchFragment.this.session_min.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_min =  LiveMatchFragment.this.session_min +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_min)+1);
                }*/
                    LiveMatchFragment.this.session_max = dataSnapshot.getString("s_mx");
                /*if(!LiveMatchFragment.this.session_max.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_max =  LiveMatchFragment.this.session_max +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_max)+1);
                }*/
                    LiveMatchFragment.this.session_open = dataSnapshot.getString("s_o");
               /* if(!LiveMatchFragment.this.session_open.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_open =  LiveMatchFragment.this.session_open +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_open)+1);
                }*/

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                LiveMatchFragment.this.loadP0();
                Log.e(LiveMatchFragment.this.TAG, "Loading p0");
            } catch (Exception e2) {
                Log.e(LiveMatchFragment.this.TAG, "Error p0");
            }
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
            this.flag1 = getArguments().getString("flag1");
            this.flag2 = getArguments().getString("flag2");
            this.type = getArguments().getInt("type");
            this.key = getArguments().getString("key");
            this.MatchStatus = getArguments().getString("matchStatus");
            this.CommentLive = getArguments().getString("comment");
            this.Score1 = getArguments().getString("score1");
            this.Score2 = getArguments().getString("score2");
            this.Over1 = getArguments().getString("over1");
            this.Over2 = getArguments().getString("over2");
            this.Team1 = getArguments().getString("team1");
            this.Team2 = getArguments().getString("team2");
            this.ISCricBuzz = getArguments().getString("ISCricBuzz");
            this.EventId = getArguments().getString("EventID");
            this.MarketId = getArguments().getString("MarketId");
            this.i1 = getArguments().getString("i1");
            this.i2 = getArguments().getString("i2");
            this.i3 = getArguments().getString("i3");
            this.oddsType = getArguments().getString("oddsType");
            this.SessionType = getArguments().getString("sessionType");
            this.f2035v = inflater.inflate(R.layout.livematch_fragment, container, false);
            if (this.type == 1) {
                this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(0);
                this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(0);
            }


            if(ISCricBuzz.equalsIgnoreCase("0")){
                if (Build.VERSION.SDK_INT >= 21) {
                    dbInstance = ThreadLocalRandom.current().nextInt(2, 11);
                } else {
                    dbInstance = new Random().nextInt(9) + 2;
                }
                connectionUrl = Global.CRICK_EXCHANGE_MATCH + dbInstance + ".firebaseio.com";
            }else{
                connectionUrl = Global.LIVEMATCH_URL;
            }

            requestQueue = Volley.newRequestQueue(getActivity());
            Loader = this.f2035v.findViewById(R.id.loader);
            Lotus_Main_Lay = this.f2035v.findViewById(R.id.lay_main_lotus);
            Tie_Match_Main = this.f2035v.findViewById(R.id.lay_main_lotus_tie);
            Complete_Match_Main = this.f2035v.findViewById(R.id.lay_main_lotus_complete);
            Target_Txt = this.f2035v.findViewById(R.id.target_txt);
            ImageView team1_Flag = this.f2035v.findViewById(R.id.team1_flag);
            ImageView team2_Flag = this.f2035v.findViewById(R.id.team2_flag);
            Sessions = this.f2035v.findViewById(R.id.get_sessions);

            Sessions_Rec = this.f2035v.findViewById(R.id.rate_recycler);
            Tie_Session_Rec = this.f2035v.findViewById(R.id.rate__tie_recycler);
            Completed_Session_Rec = this.f2035v.findViewById(R.id.rate__complete_recycler);
            scorecard = this.f2035v.findViewById(R.id.btn_live_scorecard);
            txtLiveLineScore = this.f2035v.findViewById(R.id.live_line_score);
            CommentaryButton = this.f2035v.findViewById(R.id.btn_live_commentary);
            pitchReport = this.f2035v.findViewById(R.id.btn_live_pitch_report);
            this.mHandler = new Handler();

            imvapple = this.f2035v.findViewById(R.id.imv_apple);
            imvbetfair = this.f2035v.findViewById(R.id.imv_betfair);
            imvplaystore = this.f2035v.findViewById(R.id.imv_play_store);
            imvexchange = this.f2035v.findViewById(R.id.imv_exchange);
            imvtelegram = this.f2035v.findViewById(R.id.imv_telegram);
            imvwebsite = this.f2035v.findViewById(R.id.imv_website);
            imvwhatsapp = this.f2035v.findViewById(R.id.imv_whatsapp);
            imvyoutube = this.f2035v.findViewById(R.id.imv_youtube);
            imvfacebook = this.f2035v.findViewById(R.id.imv_facebook);
            imvtips = this.f2035v.findViewById(R.id.imv_tips);
            imvtwitter = this.f2035v.findViewById(R.id.imv_twitter);

            getlinks();
            shareLinks();


            Sessions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), LiveLineSessions.class);
                    i.putExtra("t1",Team1);
                    i.putExtra("t2",Team2);
                    i.putExtra("key",key);
                    i.putExtra("inning",inning);
                    startActivity(i);
                }
            });

            scorecard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((NewLiveMatchActivity)getActivity()).pager.setCurrentItem(2);
                }
            });


            pitchReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), PitchReport.class);
                    i.putExtra("key",key);
                    startActivity(i);
                }
            });


            CommentaryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), Commentary.class);
                    i.putExtra("inning",inning);
                    i.putExtra("key",key);
                    i.putExtra("i1",i1);
                    i.putExtra("i2",i2);
                    i.putExtra("i3",i3);
                    i.putExtra("type",type);
                    startActivity(i);
                }
            });


            try{
                Glide.with(getActivity()).load(flag1).into(team1_Flag);
                Glide.with(getActivity()).load(flag2).into(team2_Flag);
            }catch (Exception e){
                e.printStackTrace();
            }

            firebaseDatabase = FirebaseDatabase.getInstance(this.connectionUrl);
            titleStringRequest();
            scoreRequest();
            if(ISCricBuzz.equalsIgnoreCase("0")){
                if(oddsType.equalsIgnoreCase("betfair")){

                }
                    //getToken();
            }

            // GetRates();
            this.drP0 = firebaseDatabase.getReference().child("sV1").child(this.key).child("p0");
            this.drP1 = firebaseDatabase.getReference().child("sV1").child(this.key).child("p1");
            this.drP2 = firebaseDatabase.getReference().child("sV1").child(this.key).child("p2");
            this.HomeTarget = FirebaseDatabase.getInstance(Global.LIVEMATCH_URL).getReference().child("liveMatchWithCricExch").child(this.key);

            ScoreRequestRef = FirebaseDatabase.getInstance(Global.LIVEMATCH_URL);
            this.cricBuzz = ScoreRequestRef.getReference().child("cricBuzz").child(this.key);
            if(ISCricBuzz.equalsIgnoreCase("0")){
                if(SessionType.equalsIgnoreCase("own")|| SessionType.equalsIgnoreCase("lotus")){
                    this.sessions = ScoreRequestRef.getReference().child("cricBuzz").child("session").child(this.key);
                }
            }
            //this.p0rate = firebaseDatabase.getReference().child("sV1").child(this.key).child("p0").child("r");
            Log.e(this.TAG, "onCreateView: " + this.key);

        }catch (Exception e){
            e.printStackTrace();
        }
        return this.f2035v;
    }

    private void initSpeech() {
        try{
            if (this.tts == null) {
                this.tts = new TextToSpeech(getActivity(), new C09125());
            }
            final SharedPreferences sharedPreferences = getContext().getSharedPreferences("speech", 0);
            this.ballupdateSpeechOn = sharedPreferences.getBoolean("ballUpdateSpeechOn", true);
            this.SessionSpeechOn = sharedPreferences.getBoolean("sessionSpeechOn", true);
            this.langHindi = sharedPreferences.getBoolean("lang", true);
            if (this.ballupdateSpeechOn) {
                ((Switch) this.f2035v.findViewById(R.id.ballUpdateSpeechSwitch)).setChecked(true);
            }
            if (this.SessionSpeechOn) {
                ((Switch) this.f2035v.findViewById(R.id.sessionUpdateSpeechSwitch)).setChecked(true);
            }
            ToggleButton langToggle = (ToggleButton) this.f2035v.findViewById(R.id.langToggle);
            if (this.langHindi) {
                langToggle.setChecked(true);
            } else {
                langToggle.setChecked(false);
            }
            langToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                class C09131 implements TextToSpeech.OnInitListener {
                    C09131() {
                    }

                    public void onInit(int i) {
                        if (i == -1) {
                            return;
                        }
                        if (LiveMatchFragment.this.langHindi) {
                            int r = LiveMatchFragment.this.tts.setLanguage(new Locale("hi"));
                            LiveMatchFragment.this.tts.setSpeechRate(Float.parseFloat("0.85"));
                            if (r == -1 || r == -2) {
                                LiveMatchFragment.this.tts.setLanguage(Locale.ENGLISH);
                                return;
                            }
                            return;
                        }
                        LiveMatchFragment.this.tts.setLanguage(Locale.ENGLISH);
                    }
                }

                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    e = sharedPreferences.edit();
                    e.putBoolean("lang", b);
                    LiveMatchFragment.this.langHindi = b;
                    e.apply();
                    LiveMatchFragment.this.tts = new TextToSpeech(LiveMatchFragment.this.getActivity(), new C09131());
                }
            });
            ((Switch) this.f2035v.findViewById(R.id.ballUpdateSpeechSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        e = sharedPreferences.edit();
                        e.putBoolean("ballUpdateSpeechOn", true);
                        LiveMatchFragment.this.ballupdateSpeechOn = true;
                        e.apply();
                        return;
                    }
                    e = sharedPreferences.edit();
                    e.putBoolean("ballUpdateSpeechOn", false);
                    LiveMatchFragment.this.ballupdateSpeechOn = false;
                    e.apply();
                }
            });
            ((Switch) this.f2035v.findViewById(R.id.sessionUpdateSpeechSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        e = sharedPreferences.edit();
                        e.putBoolean("sessionSpeechOn", true);
                        LiveMatchFragment.this.SessionSpeechOn = true;
                        e.apply();
                        return;
                    }
                    e = sharedPreferences.edit();
                    e.putBoolean("sessionSpeechOn", false);
                    LiveMatchFragment.this.SessionSpeechOn = false;
                    e.apply();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onResume() {
        try{
            initSpeech();
            initVEL();
            this.drP0.addValueEventListener(this.velP0);
            this.drP1.addValueEventListener(this.velP1);
            this.drP2.addValueEventListener(this.velP2);
            this.cricBuzz.addValueEventListener(this.velCricBuzz);
            if(ISCricBuzz.equalsIgnoreCase("0") && ((SessionType.equalsIgnoreCase("own") || SessionType.equalsIgnoreCase("lotus")))){
                this.sessions.addValueEventListener(this.SessionCricBuzz);
            }
            this.HomeTarget.addValueEventListener(this.TargetListner);
            getActivity().getWindow().addFlags(128);
            this.h2 = new Handler();
            this.mHandler.postDelayed(m_Runnable,1000);
        }catch(Exception e){
            e.printStackTrace();
        }

        super.onResume();

    }

    public void onPause() {
        try{
            this.drP0.removeEventListener(this.velP0);
            this.drP1.removeEventListener(this.velP1);
            this.drP2.removeEventListener(this.velP2);
            this.cricBuzz.removeEventListener(this.velCricBuzz);
            if(ISCricBuzz.equalsIgnoreCase("0") && ((SessionType.equalsIgnoreCase("own") || SessionType.equalsIgnoreCase("lotus")))){
                this.sessions.removeEventListener(this.SessionCricBuzz);
            }
            this.HomeTarget.removeEventListener(this.TargetListner);
            this.tts.stop();
            this.tts.shutdown();
            this.tts = null;
            this.mHandler.removeCallbacks(m_Runnable);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mHandler.removeCallbacks(m_Runnable);
        //  this.mHandler.removeMessages(0);
    }



    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            if(EventId.equalsIgnoreCase("" ) || EventId.equalsIgnoreCase("0") || EventId.equalsIgnoreCase("none")){

            }else{
                GetData();
                GetTiedMatchRates();
                GetCompleteMatchRates();
            }
            if(ISCricBuzz.equalsIgnoreCase("0")){

                if (MarketId.equalsIgnoreCase("0") || MarketId.equalsIgnoreCase("") || MarketId.equalsIgnoreCase("none")) {
                }else{
                    if(oddsType.equalsIgnoreCase("betfair")){
                       // GetRates();
                       // getTeam();
                    }else if(oddsType.equalsIgnoreCase("lotus")){
                       // GetLotusRates();
                    }else{

                    }

                }

            }

            LiveMatchFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }

    };

    public void scoreRequest() {
        JsonObjectRequest p0Request = new JsonObjectRequest(this.connectionUrl + "/sV1/" + this.key + "/p0.json", null, new C14379(), new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(LiveMatchFragment.this.TAG, "onErrorResponse: Error p0");
            }
        });
        JsonObjectRequest p1Request = new JsonObjectRequest(this.connectionUrl + "/sV1/" + this.key + "/p1.json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject dataSnapshot) {
                try {
                    //LiveMatchFragment.this.active_player = dataSnapshot.getString("ap");
                    LiveMatchFragment.this.ballsdone = dataSnapshot.getString("bd");
                    LiveMatchFragment.this.currentball = dataSnapshot.getString("cb");
                    LiveMatchFragment.this.lastballs = dataSnapshot.getString("lb");
                    LiveMatchFragment.this.needrun = dataSnapshot.getString("nr");
                    if(dataSnapshot.has("ps")){
                        LiveMatchFragment.this.ProjecteeScore = dataSnapshot.getString("ps");
                    }else{
                        LiveMatchFragment.this.ProjecteeScore = "";
                    }
                  /*  LiveMatchFragment.this.player1_balls = dataSnapshot.getString("p1b");
                    LiveMatchFragment.this.player1_run = dataSnapshot.getString("p1r");
                    LiveMatchFragment.this.player2_balls = dataSnapshot.getString("p2b");
                    LiveMatchFragment.this.player2_run = dataSnapshot.getString("p2r");
                    if(dataSnapshot.has("p14")){
                        LiveMatchFragment.this.player1_four = dataSnapshot.getString("p14");
                    }else{
                        LiveMatchFragment.this.player1_four ="-";
                    }
                    if(dataSnapshot.has("p24")){
                        LiveMatchFragment.this.player2_four = dataSnapshot.getString("p24");
                    }else{
                        LiveMatchFragment.this.player2_four = "-";
                    }

                    if(dataSnapshot.has("p16")){
                        LiveMatchFragment.this.player1_six = dataSnapshot.getString("p16");
                    }else{
                        LiveMatchFragment.this.player1_six = "-";
                    }

                    if(dataSnapshot.has("p26")){
                        LiveMatchFragment.this.player2_six = dataSnapshot.getString("p26");
                    }else{
                        LiveMatchFragment.this.player2_six ="-";
                    }
*/


                    LiveMatchFragment.this.remaining_balls = dataSnapshot.getString("rb");
                    LiveMatchFragment.this.score = dataSnapshot.getString("s");
                   // LiveMatchFragment.this.session_team = dataSnapshot.getString("st");
                    LiveMatchFragment.this.wicket = dataSnapshot.getString("w");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    LiveMatchFragment.this.loadP1();
                    Log.e(LiveMatchFragment.this.TAG, "Loading p1");
                } catch (Exception e2) {
                    Log.e(LiveMatchFragment.this.TAG, "Error p1", e2);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(LiveMatchFragment.this.TAG, "Error response p1");
            }
        });
        JsonObjectRequest p2Request = new JsonObjectRequest(this.connectionUrl + "/sV1/" + this.key + "/p2.json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject dataSnapshot) {
                try {
                   // LiveMatchFragment.this.comment = dataSnapshot.getString("c");

                  //  LiveMatchFragment.this.comment2 = dataSnapshot.getString("c2");
                    LiveMatchFragment.this.inning = dataSnapshot.getString("i");
                     LiveMatchFragment.this.session_team = dataSnapshot.getString("st");
                    //LiveMatchFragment.this.lastWicket = dataSnapshot.getString("lw");
                    // LiveMatchFragment.this.player1 = dataSnapshot.getString("p1");
                    //LiveMatchFragment.this.player2 = dataSnapshot.getString("p2");
                    // LiveMatchFragment.this.bowler = dataSnapshot.getString("b");
                    LiveMatchFragment.this.powerPlay = dataSnapshot.getString("pp");
                    if(ISCricBuzz.equalsIgnoreCase("1")){
                        LiveMatchFragment.this.rate_team = dataSnapshot.getString("rt");
                        if (LiveMatchFragment.this.type == 1) {
                            LiveMatchFragment.this.rate_team2 = dataSnapshot.getString("rt2");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    LiveMatchFragment.this.loadP2();
                    Log.e(LiveMatchFragment.this.TAG, "Loading p2");
                } catch (Exception e2) {
                    Log.e(LiveMatchFragment.this.TAG, "Error p2");
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(LiveMatchFragment.this.TAG, "Volley Error p2");
            }
        });


        JsonObjectRequest cricBuzzRequest = new JsonObjectRequest(Global.LIVEMATCH_URL + "/cricBuzz/" + this.key + ".json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject dataSnapshot) {
                try{
                    //LiveMatchFragment.this.active_player = dataSnapshot.child("ap").getValue().toString();
                    LiveMatchFragment.this.lastWicket = dataSnapshot.getString("lw");
                    LiveMatchFragment.this.bowler = dataSnapshot.getString("b");
                    LiveMatchFragment.this.player1 = dataSnapshot.getString("p1");
                    LiveMatchFragment.this.player2 = dataSnapshot.getString("p2");
                    LiveMatchFragment.this.player1_balls = dataSnapshot.getString("p1b");
                    LiveMatchFragment.this.player1_run = dataSnapshot.getString("p1r");
                    LiveMatchFragment.this.player2_balls = dataSnapshot.getString("p2b");
                    LiveMatchFragment.this.player2_run = dataSnapshot.getString("p2r");
                    if(dataSnapshot.has("p14")){
                        LiveMatchFragment.this.player1_four = dataSnapshot.getString("p14");
                    }else{
                        LiveMatchFragment.this.player1_four ="-";
                    }
                    if(dataSnapshot.has("p24")){
                        LiveMatchFragment.this.player2_four = dataSnapshot.getString("p24");
                    }else{
                        LiveMatchFragment.this.player2_four ="-";
                    }
                    if(dataSnapshot.has("p16")){
                        LiveMatchFragment.this.player1_six = dataSnapshot.getString("p16");
                    }else{
                        LiveMatchFragment.this.player1_six ="-";
                    }

                    if(dataSnapshot.has("p26")){
                        LiveMatchFragment.this.player2_six = dataSnapshot.getString("p26");
                    }else{
                        LiveMatchFragment.this.player2_six ="-";
                    }

                    if(dataSnapshot.has("c")){
                        LiveMatchFragment.this.comment = dataSnapshot.getString("c");
                    }else{
                        LiveMatchFragment.this.comment = "0";
                    }

                    if(dataSnapshot.has("c2")){
                        LiveMatchFragment.this.comment2 = dataSnapshot.getString("c2");
                    }else{
                        LiveMatchFragment.this.comment2 = "0";
                    }

                    if( (ISCricBuzz.equalsIgnoreCase("0")) && ((oddsType.equalsIgnoreCase("lotus")) || oddsType.equalsIgnoreCase("betfair")) ){
                        if(dataSnapshot.has("r")){
                            LiveMatchFragment.this.rate = dataSnapshot.getString("r");
                        }else{
                            LiveMatchFragment.this.rate = "0";
                        }
                        if(dataSnapshot.has("r2")){
                            LiveMatchFragment.this.rate2 = dataSnapshot.getString("r2");
                        }else{
                            LiveMatchFragment.this.rate2 = "0";
                        }

                        if(dataSnapshot.has("rt")){
                            LiveMatchFragment.this.rate_team = dataSnapshot.getString("rt");
                        }else{
                            LiveMatchFragment.this.rate_team = "0";
                        }

                        if(type == 1){
                            if(dataSnapshot.has("rt2")){
                                LiveMatchFragment.this.rate_team2 = dataSnapshot.getString("rt2");
                            }else{
                                LiveMatchFragment.this.rate_team2 = "0";
                            }

                            if(dataSnapshot.has("rT")){
                                LiveMatchFragment.this.rateT = dataSnapshot.getString("rT");
                            }else{
                                LiveMatchFragment.this.rateT = "0";
                            }

                            if(dataSnapshot.has("rT2")){
                                LiveMatchFragment.this.rateT2 = dataSnapshot.getString("rT2");
                            }else{
                                LiveMatchFragment.this.rateT2 = "0";
                            }

                            if(dataSnapshot.has("rD")){
                                LiveMatchFragment.this.rateD = dataSnapshot.getString("rD");
                            }else{
                                LiveMatchFragment.this.rateD = "0";
                            }


                            if(dataSnapshot.has("rD2")){
                                LiveMatchFragment.this.rateD2 = dataSnapshot.getString("rD2");
                            }else{
                                LiveMatchFragment.this.rateD2 = "0";
                            }
                        }
                    }



                    try {
                        LiveMatchFragment.this.loadCricBuzzData();
                    } catch (Exception e) {
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(LiveMatchFragment.this.TAG, "Volley Error cricBuzz");
            }
        });


        JsonObjectRequest TargetRequest = new JsonObjectRequest(Global.LIVEMATCH_URL + "/liveMatchWithCricExch/" + this.key + ".json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject dataSnapshot) {
                try {
                    //LiveMatchFragment.this.active_player = dataSnapshot.getString("ap");
                    LiveMatchFragment.this.inning = dataSnapshot.getString("inning");
                    LiveMatchFragment.this.Target = dataSnapshot.getString("target");
                    LiveMatchFragment.this.Status = dataSnapshot.getString("status");
                    if(dataSnapshot.has("isSuperOver")){
                        LiveMatchFragment.this.SuperOver = dataSnapshot.getString("isSuperOver");
                    }else{
                        LiveMatchFragment.this.SuperOver = "0";
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    LiveMatchFragment.this.loadTarget();
                    Log.e(LiveMatchFragment.this.TAG, "Loading Target");
                } catch (Exception e2) {
                    Log.e(LiveMatchFragment.this.TAG, "Error Target", e2);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(LiveMatchFragment.this.TAG, "Error response Target");
            }
        });


       requestQueue.add(TargetRequest);
       requestQueue.add(p0Request);
       requestQueue.add(p1Request);
       requestQueue.add(p2Request);
       requestQueue.add(cricBuzzRequest);
    }

    public void titleStringRequest() {
        String Livematches = "";
        if(ISCricBuzz.equalsIgnoreCase("0")){
            Livematches ="/liveMatches/";
        }else{
            Livematches ="/liveMatchWithCricExch/";
        }
        Volley.newRequestQueue(getActivity()).add(new StringRequest(this.connectionUrl + Livematches + this.key + "/title.json", new Response.Listener<String>() {
            public void onResponse(String response) {
                LiveMatchFragment.this.matchTitle = response.replaceAll("\"", "");
                ((TextView) LiveMatchFragment.this.f2035v.findViewById(R.id.match_title)).setText(LiveMatchFragment.this.matchTitle.toUpperCase());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if (LiveMatchFragment.this.getActivity() != null) {
                    Toast.makeText(LiveMatchFragment.this.getActivity(), "Internet connection error!!", 0).show();
                }
            }
        }));
    }

    public void initVEL() {
        try{
            this.velP0 = new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        if(ISCricBuzz.equalsIgnoreCase("1")){
                            LiveMatchFragment.this.rate = dataSnapshot.child("r").getValue().toString();
                            LiveMatchFragment.this.rate2 = dataSnapshot.child("r2").getValue().toString();
                        }else if(oddsType.equalsIgnoreCase("cricexch")){
                            LiveMatchFragment.this.rate = dataSnapshot.child("r").getValue().toString();
                            LiveMatchFragment.this.rate2 = dataSnapshot.child("r2").getValue().toString();
                        }

                /*if(!LiveMatchFragment.this.rate2.equalsIgnoreCase("0")){
                    if(LiveMatchFragment.this.rate2.contains(".")){
                        LiveMatchFragment.this.rate2 = String.valueOf(Double.parseDouble(rate2) + Double.parseDouble("1.00"));
                    }else{
                        LiveMatchFragment.this.rate2 = String.valueOf(Integer.parseInt(rate2) + Integer.parseInt("1"));
                    }
                }*/


                        if(ISCricBuzz.equalsIgnoreCase("1") || (ISCricBuzz.equalsIgnoreCase("0") && SessionType.equalsIgnoreCase("cricexch"))){
                            LiveMatchFragment.this.session = dataSnapshot.child("sn").getValue().toString();
                            LiveMatchFragment.this.session2 = dataSnapshot.child("sn2").getValue().toString();
                            LiveMatchFragment.this.session_min = dataSnapshot.child("s_mi").getValue().toString();
                /*if(!LiveMatchFragment.this.session_min.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_min =  LiveMatchFragment.this.session_min +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_min)+1);
                }*/
                            LiveMatchFragment.this.session_max = dataSnapshot.child("s_mx").getValue().toString();
               /* if(!LiveMatchFragment.this.session_max.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_max =  LiveMatchFragment.this.session_max +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_max)+1);
                }*/
                            LiveMatchFragment.this.session_open = dataSnapshot.child("s_o").getValue().toString();
                        }


               /* if(!LiveMatchFragment.this.session_open.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_open =  LiveMatchFragment.this.session_open +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_open)+1);
                }*/
                        if (LiveMatchFragment.this.type == 1) {
                            if(ISCricBuzz.equalsIgnoreCase("1")){
                                LiveMatchFragment.this.rateT = dataSnapshot.child("rT").getValue().toString();
                                LiveMatchFragment.this.rateT2 = dataSnapshot.child("rT2").getValue().toString();
                   /* if(!LiveMatchFragment.this.rateT2.equalsIgnoreCase("0")){
                        if(LiveMatchFragment.this.rateT2.contains(".")){
                            LiveMatchFragment.this.rateT2 = String.valueOf(Double.parseDouble(rateT2) + Double.parseDouble("1.00"));
                        }else{
                            LiveMatchFragment.this.rateT2 = String.valueOf(Integer.parseInt(rateT2) + Integer.parseInt("1"));
                        }
                    }*/

                                LiveMatchFragment.this.rateD = dataSnapshot.child("rD").getValue().toString();
                                LiveMatchFragment.this.rateD2 = dataSnapshot.child("rD2").getValue().toString();
                            }else if(oddsType.equalsIgnoreCase("cricexch")){
                                LiveMatchFragment.this.rateT = dataSnapshot.child("rT").getValue().toString();
                                LiveMatchFragment.this.rateT2 = dataSnapshot.child("rT2").getValue().toString();
                   /* if(!LiveMatchFragment.this.rateT2.equalsIgnoreCase("0")){
                        if(LiveMatchFragment.this.rateT2.contains(".")){
                            LiveMatchFragment.this.rateT2 = String.valueOf(Double.parseDouble(rateT2) + Double.parseDouble("1.00"));
                        }else{
                            LiveMatchFragment.this.rateT2 = String.valueOf(Integer.parseInt(rateT2) + Integer.parseInt("1"));
                        }
                    }*/

                                LiveMatchFragment.this.rateD = dataSnapshot.child("rD").getValue().toString();
                                LiveMatchFragment.this.rateD2 = dataSnapshot.child("rD2").getValue().toString();
                            }

                   /* if(!LiveMatchFragment.this.rateD2.equalsIgnoreCase("0") || !LiveMatchFragment.this.rateD2.equalsIgnoreCase("")){
                        if(LiveMatchFragment.this.rateD2.contains(".")){
                            LiveMatchFragment.this.rateD2 = String.valueOf(Double.parseDouble(rateD2) + Double.parseDouble("1.00"));
                        }else{
                            LiveMatchFragment.this.rateD2 = String.valueOf(Integer.parseInt(rateD2) + Integer.parseInt("1"));
                        }
                    }*/

                        }
                        try {
                            LiveMatchFragment.this.loadP0();
                        } catch (Exception e) {
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            this.velP1 = new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        // LiveMatchFragment.this.active_player = dataSnapshot.child("ap").getValue().toString();
                        LiveMatchFragment.this.ballsdone = dataSnapshot.child("bd").getValue().toString();
                        LiveMatchFragment.this.currentball = dataSnapshot.child("cb").getValue().toString();
                        LiveMatchFragment.this.lastballs = dataSnapshot.child("lb").getValue().toString();
                        LiveMatchFragment.this.needrun = dataSnapshot.child("nr").getValue().toString();
                        if(dataSnapshot.hasChild("ps")){
                            LiveMatchFragment.this.ProjecteeScore = dataSnapshot.child("ps").getValue().toString();
                        }else{
                            LiveMatchFragment.this.ProjecteeScore = "";
                        }
                  /*  LiveMatchFragment.this.player1_balls = dataSnapshot.child("p1b").getValue().toString();
                    LiveMatchFragment.this.player1_run = dataSnapshot.child("p1r").getValue().toString();
                    LiveMatchFragment.this.player2_balls = dataSnapshot.child("p2b").getValue().toString();
                    LiveMatchFragment.this.player2_run = dataSnapshot.child("p2r").getValue().toString();
                    if(dataSnapshot.hasChild("p14")){
                        LiveMatchFragment.this.player1_four = dataSnapshot.child("p14").getValue().toString();
                    }else{
                        LiveMatchFragment.this.player1_four ="-";
                    }
                    if(dataSnapshot.hasChild("p24")){
                        LiveMatchFragment.this.player2_four = dataSnapshot.child("p24").getValue().toString();
                    }else{
                        LiveMatchFragment.this.player2_four ="-";
                    }
                    if(dataSnapshot.hasChild("p16")){
                        LiveMatchFragment.this.player1_six = dataSnapshot.child("p16").getValue().toString();
                    }else{
                        LiveMatchFragment.this.player1_six ="-";
                    }

                    if(dataSnapshot.hasChild("p26")){
                        LiveMatchFragment.this.player2_six = dataSnapshot.child("p26").getValue().toString();
                    }else{
                        LiveMatchFragment.this.player2_six ="-";
                    }*/


                        LiveMatchFragment.this.remaining_balls = dataSnapshot.child("rb").getValue().toString();
                        LiveMatchFragment.this.score = dataSnapshot.child("s").getValue().toString();
                        LiveMatchFragment.this.wicket = dataSnapshot.child("w").getValue().toString();
                        try {
                            LiveMatchFragment.this.loadP1();
                        } catch (Exception e) {
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            this.velP2 = new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        // LiveMatchFragment.this.comment = dataSnapshot.child("c").getValue().toString();
                        if(ISCricBuzz.equalsIgnoreCase("1")){
                            LiveMatchFragment.this.rate_team = dataSnapshot.child("rt").getValue().toString();
                            if (LiveMatchFragment.this.type == 1) {
                                LiveMatchFragment.this.rate_team2 = dataSnapshot.child("rt2").getValue().toString();
                            }
                        }else if(oddsType.equalsIgnoreCase("cricexch")){
                            LiveMatchFragment.this.rate_team = dataSnapshot.child("rt").getValue().toString();
                            if (LiveMatchFragment.this.type == 1) {
                                LiveMatchFragment.this.rate_team2 = dataSnapshot.child("rt2").getValue().toString();
                            }
                        }

                        LiveMatchFragment.this.session_team = dataSnapshot.child("st").getValue().toString();
                        if(ISCricBuzz.equalsIgnoreCase("1") || (ISCricBuzz.equalsIgnoreCase("0") && SessionType.equalsIgnoreCase("cricexch"))){
                            LiveMatchFragment.this.session_over = dataSnapshot.child("so").getValue().toString();
                        }

                        //LiveMatchFragment.this.comment2 = dataSnapshot.child("c2").getValue().toString();
                        LiveMatchFragment.this.inning = dataSnapshot.child("i").getValue().toString();
                        // LiveMatchFragment.this.lastWicket = dataSnapshot.child("lw").getValue().toString();
                        LiveMatchFragment.this.player1 = dataSnapshot.child("p1").getValue().toString();
                        LiveMatchFragment.this.player2 = dataSnapshot.child("p2").getValue().toString();
                        // LiveMatchFragment.this.bowler = dataSnapshot.child("b").getValue().toString();
                        if(dataSnapshot.hasChild("pp")){
                            LiveMatchFragment.this.powerPlay = dataSnapshot.child("pp").getValue().toString();
                        }else{
                            LiveMatchFragment.this.powerPlay ="";
                        }




                        try {
                            LiveMatchFragment.this.loadP2();
                        } catch (Exception e) {
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }



                public void onCancelled(DatabaseError databaseError) {
                }
            };
            this.velCricBuzz = new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        //LiveMatchFragment.this.active_player = dataSnapshot.child("ap").getValue().toString();
                        LiveMatchFragment.this.lastWicket = dataSnapshot.child("lw").getValue().toString();
                        LiveMatchFragment.this.bowler = dataSnapshot.child("b").getValue().toString();
                        LiveMatchFragment.this.player1 = dataSnapshot.child("p1").getValue().toString();
                        LiveMatchFragment.this.player2 = dataSnapshot.child("p2").getValue().toString();
                        LiveMatchFragment.this.player1_balls = dataSnapshot.child("p1b").getValue().toString();
                        LiveMatchFragment.this.player1_run = dataSnapshot.child("p1r").getValue().toString();
                        LiveMatchFragment.this.player2_balls = dataSnapshot.child("p2b").getValue().toString();
                        LiveMatchFragment.this.player2_run = dataSnapshot.child("p2r").getValue().toString();
                        if(dataSnapshot.hasChild("p14")){
                            LiveMatchFragment.this.player1_four = dataSnapshot.child("p14").getValue().toString();
                        }else{
                            LiveMatchFragment.this.player1_four ="-";
                        }
                        if(dataSnapshot.hasChild("p24")){
                            LiveMatchFragment.this.player2_four = dataSnapshot.child("p24").getValue().toString();
                        }else{
                            LiveMatchFragment.this.player2_four ="-";
                        }
                        if(dataSnapshot.hasChild("p16")){
                            LiveMatchFragment.this.player1_six = dataSnapshot.child("p16").getValue().toString();
                        }else{
                            LiveMatchFragment.this.player1_six ="-";
                        }

                        if(dataSnapshot.hasChild("p26")){
                            LiveMatchFragment.this.player2_six = dataSnapshot.child("p26").getValue().toString();
                        }else{
                            LiveMatchFragment.this.player2_six ="-";
                        }

                        if(dataSnapshot.hasChild("c")){
                            LiveMatchFragment.this.comment = dataSnapshot.child("c").getValue().toString();
                        }else{
                            LiveMatchFragment.this.comment = "0";
                        }

                        if(dataSnapshot.hasChild("c2")){
                            LiveMatchFragment.this.comment2 = dataSnapshot.child("c2").getValue().toString();
                        }else{
                            LiveMatchFragment.this.comment2 = "0";
                        }

                        if( (ISCricBuzz.equalsIgnoreCase("0")) && (oddsType.equalsIgnoreCase("lotus")) || oddsType.equalsIgnoreCase("betfair") ){
                            if(dataSnapshot.hasChild("r")){
                                LiveMatchFragment.this.rate = dataSnapshot.child("r").getValue().toString();
                            }else{
                                LiveMatchFragment.this.rate = "0";
                            }
                            if(dataSnapshot.hasChild("r2")){
                                LiveMatchFragment.this.rate2 = dataSnapshot.child("r2").getValue().toString();
                            }else{
                                LiveMatchFragment.this.rate2 = "0";
                            }

                            if(dataSnapshot.hasChild("rt")){
                                LiveMatchFragment.this.rate_team = dataSnapshot.child("rt").getValue().toString();
                            }else{
                                LiveMatchFragment.this.rate_team = "0";
                            }

                            if(type == 1){
                                if(dataSnapshot.hasChild("rt2")){
                                    LiveMatchFragment.this.rate_team2 = dataSnapshot.child("rt2").getValue().toString();
                                }else{
                                    LiveMatchFragment.this.rate_team2 = "0";
                                }

                                if(dataSnapshot.hasChild("rT")){
                                    LiveMatchFragment.this.rateT = dataSnapshot.child("rT").getValue().toString();
                                }else{
                                    LiveMatchFragment.this.rateT = "0";
                                }

                                if(dataSnapshot.hasChild("rT2")){
                                    LiveMatchFragment.this.rateT2 = dataSnapshot.child("rT2").getValue().toString();
                                }else{
                                    LiveMatchFragment.this.rateT2 = "0";
                                }

                                if(dataSnapshot.hasChild("rD")){
                                    LiveMatchFragment.this.rateD = dataSnapshot.child("rD").getValue().toString();
                                }else{
                                    LiveMatchFragment.this.rateD = "0";
                                }


                                if(dataSnapshot.hasChild("rD2")){
                                    LiveMatchFragment.this.rateD2 = dataSnapshot.child("rD2").getValue().toString();
                                }else{
                                    LiveMatchFragment.this.rateD2 = "0";
                                }
                            }
                        }




                        try {
                            LiveMatchFragment.this.loadCricBuzzData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }



                public void onCancelled(DatabaseError databaseError) {
                }
            };
            if(ISCricBuzz.equalsIgnoreCase("0") && ((SessionType.equalsIgnoreCase("own") || SessionType.equalsIgnoreCase("lotus")))){
                this.SessionCricBuzz = new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            LiveMatchFragment.this.session = dataSnapshot.child("sn").getValue().toString();
                            LiveMatchFragment.this.session2 = dataSnapshot.child("sn2").getValue().toString();
                            LiveMatchFragment.this.session_min = dataSnapshot.child("s_mi").getValue().toString();
                /*if(!LiveMatchFragment.this.session_min.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_min =  LiveMatchFragment.this.session_min +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_min)+1);
                }*/
                            LiveMatchFragment.this.session_max = dataSnapshot.child("s_mx").getValue().toString();
               /* if(!LiveMatchFragment.this.session_max.equalsIgnoreCase("0")){
                    LiveMatchFragment.this.session_max =  LiveMatchFragment.this.session_max +"-"+String.valueOf(Integer.parseInt(LiveMatchFragment.this.session_max)+1);
                }*/
                            LiveMatchFragment.this.session_open = dataSnapshot.child("s_o").getValue().toString();
                            LiveMatchFragment.this.session_over = dataSnapshot.child("so").getValue().toString();
                            try {
                                LiveMatchFragment.this.loadSessions();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
            }
            this.TargetListner = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        LiveMatchFragment.this.inning = dataSnapshot.child("inning").getValue().toString();
                        LiveMatchFragment.this.Target = dataSnapshot.child("target").getValue().toString();
                        LiveMatchFragment.this.Status = dataSnapshot.child("status").getValue().toString();
                        if(dataSnapshot.hasChild("isSuperOver")){
                            LiveMatchFragment.this.SuperOver = dataSnapshot.child("isSuperOver").getValue().toString();
                        }else{
                            LiveMatchFragment.this.SuperOver = "0";
                        }


                        try {
                            LiveMatchFragment.this.loadTarget();
                        } catch (Exception e) {
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }catch (Exception  e){
            e.printStackTrace();
        }


    }

    public void loadP0() {
        try{

            this.old_ss1 = this.new_ss1;
            this.old_ss2 = this.new_ss2;
            if(ISCricBuzz.equalsIgnoreCase("1")){
                if(this.rate.equalsIgnoreCase("0") && this.rate2.equalsIgnoreCase("0")){
                    this.f2035v.findViewById(R.id.bhavLayout).setVisibility(8);
                }else{
                    this.f2035v.findViewById(R.id.bhavLayout).setVisibility(0);
                }
                ((TextView) this.f2035v.findViewById(R.id.bhavLeft)).setText(this.rate);
                ((TextView) this.f2035v.findViewById(R.id.bhavRight)).setText(this.rate2);
                if (this.type == 1) {
                    if(this.rateT.equalsIgnoreCase("0") && this.rateT2.equalsIgnoreCase("0")){
                        this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(0);
                    }

                    if(this.rateD.equalsIgnoreCase("0") && this.rateD2.equalsIgnoreCase("0")){
                        this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(0);
                    }


                    ((TextView) this.f2035v.findViewById(R.id.bhavTeam2Left)).setText(this.rateT);
                    ((TextView) this.f2035v.findViewById(R.id.bhavTeam2Right)).setText(this.rateT2);
                    ((TextView) this.f2035v.findViewById(R.id.bhavDrawLeft)).setText(this.rateD);
                    ((TextView) this.f2035v.findViewById(R.id.bhavDrawRight)).setText(this.rateD2);
                }
            }else if(oddsType.equalsIgnoreCase("cricexch")){
                if(this.rate.equalsIgnoreCase("0") && this.rate2.equalsIgnoreCase("0")){
                    this.f2035v.findViewById(R.id.bhavLayout).setVisibility(8);
                }else{
                    this.f2035v.findViewById(R.id.bhavLayout).setVisibility(0);
                }
                ((TextView) this.f2035v.findViewById(R.id.bhavLeft)).setText(this.rate);
                ((TextView) this.f2035v.findViewById(R.id.bhavRight)).setText(this.rate2);
                if (this.type == 1) {
                    if(this.rateT.equalsIgnoreCase("0") && this.rateT2.equalsIgnoreCase("0")){
                        this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(0);
                    }

                    if(this.rateD.equalsIgnoreCase("0") && this.rateD2.equalsIgnoreCase("0")){
                        this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(0);
                    }


                    ((TextView) this.f2035v.findViewById(R.id.bhavTeam2Left)).setText(this.rateT);
                    ((TextView) this.f2035v.findViewById(R.id.bhavTeam2Right)).setText(this.rateT2);
                    ((TextView) this.f2035v.findViewById(R.id.bhavDrawLeft)).setText(this.rateD);
                    ((TextView) this.f2035v.findViewById(R.id.bhavDrawRight)).setText(this.rateD2);
                }
            }

            if(ISCricBuzz.equalsIgnoreCase("1") || ((ISCricBuzz.equalsIgnoreCase("0") && SessionType.equalsIgnoreCase("cricexch")))){
                ((TextView) this.f2035v.findViewById(R.id.sessionLeft)).setText(this.session);
                ((TextView) this.f2035v.findViewById(R.id.overRight)).setText(this.session2);
                ((TextView) this.f2035v.findViewById(R.id.sessionExtraMin)).setText("Min : " + this.session_min);
                ((TextView) this.f2035v.findViewById(R.id.sessionExtraMax)).setText("Max : " + this.session_max);
                ((TextView) this.f2035v.findViewById(R.id.sessionExtraOpen)).setText("Open : " + this.session_open);
                ((TextView) this.f2035v.findViewById(R.id.overRight)).setText(this.session2);
                int sessionPassRun = Integer.parseInt(this.session2) - Integer.parseInt(this.score);
                String sessionOvers = "";
                int i = 0;
                if(this.session_over.startsWith("Match")){
                    this.session_over = this.session_over ;
                }else if((this.session_over.contains("1 st"))){
                    this.session_over = this.session_over.replace("1 st", "");
                }else if((this.session_over.contains("1st"))){
                    this.session_over = this.session_over.replace("1st", "");
                }else if((this.session_over.contains("2 nd"))){
                    this.session_over =this.session_over.replace("2 nd", "");
                }else if((this.session_over.contains("2nd"))){
                    this.session_over = this.session_over.replace("2nd", "");
                }else{
                    this.session_over = this.session_over ;
                }
                int length = this.session_over.length();
                if(length>15){
                    length = 15;
                }
                while (i < length) {
                    if (this.session_over.charAt(i) > '/' && this.session_over.charAt(i) < ':') {
                        sessionOvers = sessionOvers + this.session_over.charAt(i);
                    }
                    i++;
                }
                if (sessionOvers.trim() != "") {
                    ((TextView) this.f2035v.findViewById(R.id.sessionPassLeft)).setText("" + ((Integer.parseInt(sessionOvers) * 6) - Integer.parseInt(this.ballsdone)));
                    ((TextView) this.f2035v.findViewById(R.id.sessionPassRight)).setText("" + sessionPassRun);
                }
                if (this.session2.equals("0")) {
                    ((TextView) this.f2035v.findViewById(R.id.sessionLeft)).setText("-");
                    ((TextView) this.f2035v.findViewById(R.id.overRight)).setText("-");
                    ((TextView) this.f2035v.findViewById(R.id.sessionPassLeft)).setText("-");
                    ((TextView) this.f2035v.findViewById(R.id.sessionPassRight)).setText("-");
                }
                this.new_ss1 = this.session;
                this.new_ss2 = this.session2;
                if (!this.new_ss1.equals(this.old_ss1) && !this.new_ss1.equals("0") && this.SessionSpeechOn) {
                    if (("" + this.new_ss1).equals("" + this.new_ss2)) {
                        this.tts.speak(this.new_ss1 + " का नब्बे eleven", 1, null);
                    } else {
                        this.tts.speak(this.new_ss1, 1, null);
                        this.tts.playSilence(300, 1, null);
                        this.tts.speak(this.new_ss2, 1, null);
                    }
                    Log.d(this.TAG, "onResponse: Session Speech ON");
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }


    }

    public void loadP1() {
        try{

            if(LiveMatchFragment.this.ProjecteeScore.equalsIgnoreCase("")){
                ((RelativeLayout) this.f2035v.findViewById(R.id.projectedscoreLayout)).setVisibility(8);
            }else{
                ((RelativeLayout) this.f2035v.findViewById(R.id.projectedscoreLayout)).setVisibility(0);
                ((TextView) this.f2035v.findViewById(R.id.ProjectedScore)).setText("Projected Score :- "+this.ProjecteeScore+" Runs");
            }

            this.lastball = this.thisball;

            ((TextView) this.f2035v.findViewById(R.id.thisover)).setText(this.lastballs);
            try {
                int totalballs = Integer.parseInt(this.ballsdone);
                overs = (totalballs / 6) + "." + (totalballs % 6);
                ((TextView) this.f2035v.findViewById(R.id.crr)).setText("" + Double.valueOf(((double) Math.round(Double.valueOf(Double.parseDouble(this.score) / (((double) totalballs) / 6.0d)).doubleValue() * 100.0d)) / 100.0d));
                ((TextView) this.f2035v.findViewById(R.id.overs)).setText(overs + " OVERS");
            } catch (NumberFormatException e) {
                Log.e(this.TAG, "loadP1: ", e);
            }
            if (this.currentball.trim().length() == 1) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setTextSize(40.0f);
            } else {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setTextSize(18.0f);
            }
            if(this.currentball.equalsIgnoreCase("WIDE")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Wide Ball");
            }else if(this.currentball.equalsIgnoreCase("BALL")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Ball Start");
            }else if(this.currentball.equalsIgnoreCase("4")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("4 Runs");
            }else if(this.currentball.equalsIgnoreCase("6")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("6 Runs");
            }else if(this.currentball.equalsIgnoreCase("Over")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Over End");
            }else if(this.currentball.equalsIgnoreCase("Wicket")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Wkt Gya");
            }else if(this.currentball.equalsIgnoreCase("Nb+1")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("No ball + 1 Run");
            }else if(this.currentball.equalsIgnoreCase("Free Hit") || this.currentball.equalsIgnoreCase("FreeHit")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Free Hit on this Ball");
            }else if(this.currentball.equalsIgnoreCase("Innings Break") || this.currentball.equalsIgnoreCase("Inning Break") ||
                    this.currentball.equalsIgnoreCase("Innings Breaks")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Inning Completed");
            }else if(this.currentball.equalsIgnoreCase("Spinner aaya")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Spin Bowling");
            }else if(this.currentball.equalsIgnoreCase("Faster aaya")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Fast Bowling");
            }else if(this.currentball.equalsIgnoreCase("Play stopped due to rain")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Match Stopped due to rain");
            }else if(this.currentball.equalsIgnoreCase("Rain Stops play")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Match Stopped due to rain");
            }else if(this.currentball.equalsIgnoreCase("Ruka")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Bowler Ruka");
            }else if(this.currentball.equalsIgnoreCase("Wait for Odds")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Odds not stable");
            }else if(this.currentball.startsWith("Click") || this.currentball.startsWith("Download") || this.currentball.startsWith("Cricket") || this.currentball.startsWith("cricket")
                    || this.currentball.startsWith("click") || this.currentball.startsWith("download")) {
                this.currentball = "Cricflame faster than your thoughts";
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.trim().equals("1")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("1 Run");
            } else if (this.currentball.trim().equals("2")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("2 Runs");
            } else if (this.currentball.trim().equalsIgnoreCase("3")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("3 Runs");
            } else if (this.currentball.equalsIgnoreCase("0")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("0 Run");
            }else if (this.currentball.equalsIgnoreCase("Match Stopped")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("No Ball")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("Not-Outtt") || this.currentball.equalsIgnoreCase("Not Out")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("Time Out")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("BYE")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("Leg Bye")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("Run Out")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if (this.currentball.equalsIgnoreCase("Third Umpire")) {
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.equalsIgnoreCase("Wide plus single")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Wide + 1 Run");
            } else if(this.currentball.equalsIgnoreCase("Wide plus double")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Wide + 2 Runs");
            }
            else if(this.currentball.equalsIgnoreCase("Wide plus triple")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Wide + 3 Runs");
            }else if(this.currentball.equalsIgnoreCase("Wide plus four")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText("Wide + 4 Runs");
            }else if(this.currentball.equalsIgnoreCase("Nb+4")){
                this.currentball = "No Ball plus 4 Runs";
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.equalsIgnoreCase("Nb+1")){
                this.currentball = "No Ball plus 1 Run";
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.equalsIgnoreCase("Nb+2")){
                this.currentball = "No Ball plus 2 Runs";
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.equalsIgnoreCase("Nb+3")){
                this.currentball = "No Ball plus 3 Runs";
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.equalsIgnoreCase("Nb+6")){
                this.currentball = "No Ball plus 6 Runs";
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.trim().equalsIgnoreCase("Day 1: Stumps")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.trim().equalsIgnoreCase("Day 2: Stumps")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.trim().equalsIgnoreCase("Day 3: Stumps")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }else if(this.currentball.trim().equalsIgnoreCase("Day 4: Stumps")){
                ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
            }
            else{
                if(this.currentball.contains("Abandoned") || this.currentball.contains("abandoned") ||
                        this.currentball.contains("without") || this.currentball.contains("Without") ) {
                    ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
                }else if(this.currentball.contains("Rescheduled") || this.currentball.contains("rescheduled") ||
                        this.currentball.contains("Won") || this.currentball.contains("won") ||
                        this.currentball.contains("Win") || this.currentball.contains("win")
                        || this.currentball.contains("Tie") || this.currentball.contains("tie")
                        || this.currentball.contains("Tied") || this.currentball.contains("tied")
                        || this.currentball.contains("Delayed") || this.currentball.contains("delayed")
                        || this.currentball.contains("Rain") || this.currentball.contains("rain")
                        || this.currentball.contains("Rains") || this.currentball.contains("rains") ||
                        this.currentball.contains("Stop") || this.currentball.contains("stop") ||
                        this.currentball.contains("Stopped") || this.currentball.contains("stopped") ||
                        this.currentball.contains("Ball Hawa Me") ||
                        this.currentball.contains("Delay") || this.currentball.contains("delay") ||
                        this.currentball.contains("Reduced") || this.currentball.contains("Reduce")){
                    ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
                } else{
                    if(ISCricBuzz.equalsIgnoreCase("0")){
                        this.currentball = "Cricflame faster than your thoughts";
                        ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
                    }else{
                        ((TextView) this.f2035v.findViewById(R.id.ball)).setText(this.currentball);
                    }

                }

            }
            if (SuperOver.equalsIgnoreCase("1")){
            if (this.inning.equals("2")) {
                ((TextView) this.f2035v.findViewById(R.id.need)).setText("-");
                this.f2035v.findViewById(R.id.runNeedLayout).setVisibility(8);
            } else {
                if(LiveMatchFragment.this.Status.equalsIgnoreCase("2")){
                    this.f2035v.findViewById(R.id.runNeedLayout).setVisibility(8);
                }else{
                    this.f2035v.findViewById(R.id.runNeedLayout).setVisibility(0);
                    ((TextView) this.f2035v.findViewById(R.id.need)).setText(this.session_team+" Need " + this.needrun + " runs on " + this.remaining_balls + " balls.");
                    if (this.remaining_balls.equals("0")) {
                        ((TextView) this.f2035v.findViewById(R.id.rr)).setText("");
                    } else {
                        ((TextView) this.f2035v.findViewById(R.id.rr)).setText("" + Double.valueOf(((double) Math.round(Double.valueOf(Double.parseDouble(this.needrun) / (Double.parseDouble(this.remaining_balls) / 6.0d)).doubleValue() * 100.0d)) / 100.0d));
                    }
                }

            }
        } else{
                if (this.inning.equals("1")) {
                    ((TextView) this.f2035v.findViewById(R.id.need)).setText("-");
                    this.f2035v.findViewById(R.id.runNeedLayout).setVisibility(8);
                    //Target_Txt.setVisibility(8);

                } else {
                    if(LiveMatchFragment.this.Status.equalsIgnoreCase("2")){
                        this.f2035v.findViewById(R.id.runNeedLayout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.runNeedLayout).setVisibility(0);

                        if(type == 0){
                            ((TextView) this.f2035v.findViewById(R.id.need)).setText(this.session_team+" Need " + this.needrun + " runs on " + this.remaining_balls + " balls.");
                            if (this.remaining_balls.equals("0")) {
                                ((TextView) this.f2035v.findViewById(R.id.rr)).setText("");
                            } else {
                                ((TextView) this.f2035v.findViewById(R.id.rr)).setText("" + Double.valueOf(((double) Math.round(Double.valueOf(Double.parseDouble(this.needrun) / (Double.parseDouble(this.remaining_balls) / 6.0d)).doubleValue() * 100.0d)) / 100.0d));
                            }
                        }
                    }



                }
            }



            ((TextView) this.f2035v.findViewById(R.id.score)).setText(this.score + "/" + this.wicket);


            this.thisball = this.currentball.trim().toUpperCase();
            if (this.thisball.equals("OVER") && !this.lastball.equals("OVER")) {
                if (this.ballupdateSpeechOn) {
                    if (this.langHindi) {
                        this.tts.speak(this.score + " रन पर " + this.wicket + " विकेट ", 1, null);
                    } else {
                        this.tts.speak(this.score + " runs for " + this.wicket + " wickets", 1, null);
                    }
                }

            }
            if (this.langHindi) {
                if (!this.lastball.equals(this.thisball) && this.ballupdateSpeechOn) {
                    Log.d(this.TAG, "onResponse: Ball Update Speech ON");
                    if (this.currentball.toUpperCase().equals("WIDE")) {
                        this.tts.speak("वाइड आया वाइड", 1, null);
                    }else if (this.currentball.toUpperCase().equals("BALL")) {
                        this.tts.speak("बॉल चालु बॉल", 1, null);
                        //PlaySound(this.currentball);
                    } else if (this.currentball.toUpperCase().trim().equals("WICKET")) {
                        this.tts.speak("विकेट गया विकेट", 1, null);
                    } else if (this.currentball.toUpperCase().equals("NO BALL")) {
                        this.tts.speak("नो बॉल है नो बॉल", 1, null);
                    } else if (this.currentball.trim().equals("4")) {
                        this.tts.speak("चोका आया चोका", 1, null);
                        PlayGif(this.currentball);
                    } else if (this.currentball.trim().equals("1")) {
                        this.tts.speak("सिंगल आया सिंगल", 1, null);
                    } else if (this.currentball.trim().equals("2")) {
                        this.tts.speak("डबल आया डबल", 1, null);
                    } else if (this.currentball.trim().equals("3")) {
                        this.tts.speak("3 रन आया 3 रन", 1, null);
                    } else if (this.currentball.trim().equals("6")) {
                        this.tts.speak("छक्का आया छक्का", 1, null);
                        PlayGif(this.currentball);
                    } else if (this.currentball.toUpperCase().equals("RUKA")) {
                        this.tts.speak("बॉलर रुका है बॉलर रुका है", 1, null);
                    } else if (this.currentball.toUpperCase().equals("FASTER AAYA")) {
                        this.tts.speak("फास्टर आया फास्टर", 1, null);
                    } else if (this.currentball.toUpperCase().equals("SPINNER AAYA")) {
                        this.tts.speak("स्पिनर आया स्पिनर", 1, null);
                    } else if (this.currentball.toUpperCase().equals("0")) {
                        this.tts.speak("खाली खाली खाली", 1, null);
                        //PlaySound(this.currentball);
                    } else if (this.currentball.equals("Player Injured")) {
                        this.tts.speak("प्लेयर injured हुआ है प्लेयर injured हुआ है", 1, null);
                    } else if (this.currentball.equals("Players Entering")) {
                        this.tts.speak("प्लेयर्स एंटर हो रहे है प्लेयर्स एंटर हो रहे है", 1, null);
                    } else if (this.currentball.equals("Drizzle")) {
                        this.tts.speak("बूंदा बांदी हो रही है हल्की हल्की बारिश हो रही है", 1, null);
                    } else {
                        this.tts.speak(this.currentball, 1, null);
                    }
                }
            } else if (!this.lastball.equals(this.thisball) && this.ballupdateSpeechOn) {
                if (this.currentball.toUpperCase().equals("WIDE")) {
                    this.tts.speak("Wide Wide Wide", 1, null);
                } else if (this.currentball.toUpperCase().equals("BALL")) {
                    this.tts.speak("Ball Start Ball", 1, null);
                } else if (this.currentball.toUpperCase().trim().equals("WICKET")) {
                    this.tts.speak("Wicket Wicket Wicket", 1, null);
                } else if (this.currentball.toUpperCase().equals("NO BALL")) {
                    this.tts.speak("No Ball No Ball", 1, null);
                } else if (this.currentball.trim().equals("4")) {
                    this.tts.speak("Four Four Four", 1, null);
                    PlayGif(this.currentball);
                } else if (this.currentball.trim().equals("1")) {
                    this.tts.speak("Single Single Single", 1, null);
                } else if (this.currentball.trim().equals("2")) {
                    this.tts.speak("Double Double Double", 1, null);
                } else if (this.currentball.trim().equals("3")) {
                    this.tts.speak("Triple Triple Triple", 1, null);
                } else if (this.currentball.trim().equals("6")) {
                    this.tts.speak("Six Six Six", 1, null);
                    PlayGif(this.currentball);
                } else if (this.currentball.toUpperCase().equals("RUKA")) {
                    this.tts.speak("Bowler Stopped", 1, null);
                } else if (this.currentball.toUpperCase().equals("FASTER AAYA")) {
                    this.tts.speak("Fast Bowler", 1, null);
                } else if (this.currentball.toUpperCase().equals("SPINNER AAYA")) {
                    this.tts.speak("Spinner", 1, null);
                } else if (this.currentball.toUpperCase().equals("0")) {
                    this.tts.speak("Dot Ball Dot Ball", 1, null);
                } else {
                    this.tts.speak(this.currentball, 1, null);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void loadP2() {
        try{


       /*     TextView CommentTop = ((TextView) this.f2035v.findViewById(R.id.comment));
            TextView CommentBottom = this.f2035v.findViewById(R.id.comment2);
            if(ISCricBuzz.equalsIgnoreCase("0")){
                CommentTop.setVisibility(8);
                CommentBottom.setVisibility(8);
            }else{
               *//* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    CommentTop.setText(Html.fromHtml(this.comment , Html.FROM_HTML_MODE_LEGACY));
                } else {
                    CommentTop.setText(Html.fromHtml(this.comment));
                }*//*
                CommentTop.setVisibility(0);
                CommentBottom.setVisibility(0);
                if(this.comment.equalsIgnoreCase("0")){
                    CommentTop.setVisibility(8);

                }else{
                    CommentTop.setText(this.comment);
                }
                if(this.comment2.equalsIgnoreCase("0")){
                    CommentBottom.setVisibility(8);
                }else{
                    //CommentTop.setText(this.comment);
                    CommentBottom.setText(this.comment2);
                }

            }*/
            //CommentTop.setVisibility(8);



      /*  CommentBottom.setText(this.comment2);
        CommentBottom.setMaxLines(12);*/

      /*  if(ISCricBuzz.equalsIgnoreCase("0")){
            Target_Txt.setVisibility(8);
        }else{
            if(this.inning.equalsIgnoreCase("2")){
                Target_Txt.setVisibility(0);
                //String[] separated = this.Score1.split("/");
              *//*  separated[0];
                separated[1];*//*
                Target_Txt.setText("Target -:"+String.valueOf(Integer.parseInt(this.score)+1));
            }

        }
*/
            if (session_team.equalsIgnoreCase("")){
                txtLiveLineScore.setText(this.score + "/" + this.wicket + "(" +overs+ ")");
            }else txtLiveLineScore.setText(this.session_team +"-"+this.score + "/" + this.wicket + "(" +overs+ ")");
        /*if(this.Over1.equalsIgnoreCase("hide")){
            CommentTop.setText(this.CommentLive );
        }else{
            if (this.MatchStatus.equalsIgnoreCase("Live")) {
                CommentTop.setText(this.CommentLive);
            }else{
                CommentTop.setText(this.CommentLive +"\n\n"+Team1+"-"+Score1+"("+Over1+")"+"              "+Team2+"-"+Score2+"("+Over2+")");
            }

        }*/

      /*  if (this.MatchStatus.equalsIgnoreCase("Result")) {
         CommentTop.setMaxLines(5);
        }else{
            CommentTop.setMaxLines(9);
        }*/

            if(ISCricBuzz.equalsIgnoreCase("1")){
                ((TextView) this.f2035v.findViewById(R.id.rateTeam)).setText(this.rate_team);
            }else if(oddsType.equalsIgnoreCase("cricexch")){
                ((TextView) this.f2035v.findViewById(R.id.rateTeam)).setText(this.rate_team);
            }

            ((TextView) this.f2035v.findViewById(R.id.scorelabel)).setText(this.session_team);
            if(ISCricBuzz.equalsIgnoreCase("1") || ((ISCricBuzz.equalsIgnoreCase("0") && SessionType.equalsIgnoreCase("cricexch")))) {
                ((TextView) this.f2035v.findViewById(R.id.session_overs)).setText(this.session_over);
                //((TextView) this.f2035v.findViewById(R.id.bowler)).setText(this.bowler);
                //((TextView) this.f2035v.findViewById(R.id.lastWicket)).setText(this.lastWicket);
                if (this.session_over.equals("0")) {
                    this.f2035v.findViewById(R.id.overLayout).setVisibility(8);
                } else {
                    this.f2035v.findViewById(R.id.overLayout).setVisibility(0);
                }
            }

            if (this.powerPlay.equals("0")) {
                ((TextView) this.f2035v.findViewById(R.id.powerPlay)).setVisibility(8);
            } else if (this.powerPlay.equals("1")) {
                ((TextView) this.f2035v.findViewById(R.id.powerPlay)).setVisibility(0);
            }
            if (this.type == 1) {
                if(ISCricBuzz.equalsIgnoreCase("1")){
                    ((TextView) this.f2035v.findViewById(R.id.rateTeam2)).setText(this.rate_team2);
                }else if(oddsType.equalsIgnoreCase("cricexch")){
                    ((TextView) this.f2035v.findViewById(R.id.rateTeam2)).setText(this.rate_team2);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void loadTarget(){
        try{
            if(this.type==0){
                if (this.inning.equals("1")) {
                    Target_Txt.setVisibility(8);
                } else{
                    Target_Txt.setVisibility(0);
                    Target_Txt.setText("Target :- "+this.Target +" Runs");
                }
            }else{
                Target_Txt.setVisibility(8);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public void loadCricBuzzData(){
        try{
            LiveMatchFragment.this.active_player = "p1";
            ((TextView) this.f2035v.findViewById(R.id.lastWicket)).setText(this.lastWicket);
            ((TextView) this.f2035v.findViewById(R.id.bowler)).setText(this.bowler);
            if (this.active_player.equals("p1")) {
                //((TextView) this.f2035v.findViewById(R.id.player1)).setText(this.player1 + " *");
                ((TextView) this.f2035v.findViewById(R.id.player1)).setText(this.player1);
                ((TextView) this.f2035v.findViewById(R.id.player2)).setText(this.player2);
                ((TextView) this.f2035v.findViewById(R.id.player1_run)).setText(this.player1_run);
                ((TextView) this.f2035v.findViewById(R.id.player2_run)).setText(this.player2_run);
                ((TextView) this.f2035v.findViewById(R.id.player1_balls)).setText(this.player1_balls);
                ((TextView) this.f2035v.findViewById(R.id.player2_balls)).setText(this.player2_balls);
                ((TextView) this.f2035v.findViewById(R.id.player1_four)).setText(this.player1_four);
                ((TextView) this.f2035v.findViewById(R.id.player2_four)).setText(this.player2_four);
                ((TextView) this.f2035v.findViewById(R.id.player1_six)).setText(this.player1_six);
                ((TextView) this.f2035v.findViewById(R.id.player2_six)).setText(this.player2_six);
                if (this.player1_balls.equals("0") || this.player1_balls.equalsIgnoreCase("")) {
                    this.player1_strikeRate = "-";
                } else {
                    this.player1_strikeRate = String.format("%.2f", new Object[]{Double.valueOf((Double.parseDouble(this.player1_run) / ((double) Integer.parseInt(this.player1_balls))) * 100.0d)});
                }
                if (this.player2_balls.equals("0") || this.player2_balls.equalsIgnoreCase("")) {
                    this.player2_strikeRate = "-";
                } else {
                    this.player2_strikeRate = String.format("%.2f", new Object[]{Double.valueOf((Double.parseDouble(this.player2_run) / ((double) Integer.parseInt(this.player2_balls))) * 100.0d)});
                }
                ((TextView) this.f2035v.findViewById(R.id.player1_sr)).setText(this.player1_strikeRate);
                ((TextView) this.f2035v.findViewById(R.id.player2_sr)).setText(this.player2_strikeRate);
            } else {
                ((TextView) this.f2035v.findViewById(R.id.player2)).setText(this.player1);
                ((TextView) this.f2035v.findViewById(R.id.player1)).setText(this.player2 + " *");
                ((TextView) this.f2035v.findViewById(R.id.player1_run)).setText(this.player2_run);
                ((TextView) this.f2035v.findViewById(R.id.player2_run)).setText(this.player1_run);
                ((TextView) this.f2035v.findViewById(R.id.player1_balls)).setText(this.player2_balls);
                ((TextView) this.f2035v.findViewById(R.id.player2_balls)).setText(this.player1_balls);
                ((TextView) this.f2035v.findViewById(R.id.player1_four)).setText(this.player2_four);
                ((TextView) this.f2035v.findViewById(R.id.player2_four)).setText(this.player1_four);
                ((TextView) this.f2035v.findViewById(R.id.player1_six)).setText(this.player2_six);
                ((TextView) this.f2035v.findViewById(R.id.player2_six)).setText(this.player1_six);
                if (this.player1_balls.equals("0") || this.player1_balls.equalsIgnoreCase("")) {
                    this.player1_strikeRate = "-";
                } else {
                    this.player1_strikeRate = String.format("%.2f", new Object[]{Double.valueOf((Double.parseDouble(this.player1_run) / ((double) Integer.parseInt(this.player1_balls))) * 100.0d)});
                }
                if (this.player2_balls.equals("0") || this.player2_balls.equalsIgnoreCase("")) {
                    this.player2_strikeRate = "-";
                } else {
                    this.player2_strikeRate = String.format("%.2f", new Object[]{Double.valueOf((Double.parseDouble(this.player2_run) / ((double) Integer.parseInt(this.player2_balls))) * 100.0d)});
                }
                ((TextView) this.f2035v.findViewById(R.id.player1_sr)).setText(this.player2_strikeRate);
                ((TextView) this.f2035v.findViewById(R.id.player2_sr)).setText(this.player1_strikeRate);
            }

            TextView CommentTop = ((TextView) this.f2035v.findViewById(R.id.comment));
            TextView CommentBottom = this.f2035v.findViewById(R.id.comment2);

               /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    CommentTop.setText(Html.fromHtml(this.comment , Html.FROM_HTML_MODE_LEGACY));
                } else {
                    CommentTop.setText(Html.fromHtml(this.comment));
                }*/

            if(this.comment.equalsIgnoreCase("0") || this.comment.equalsIgnoreCase("")){
                CommentTop.setVisibility(8);

            }else{
                CommentTop.setVisibility(0);
                CommentTop.setText(this.comment);


            }
            if(this.comment2.equalsIgnoreCase("0") || this.comment2.equalsIgnoreCase("")){
                CommentBottom.setVisibility(8);
            }else{
                CommentBottom.setVisibility(0);
                CommentBottom.setText(this.comment2);

            }
            if( ((ISCricBuzz.equalsIgnoreCase("0") && (oddsType.equalsIgnoreCase("lotus") || oddsType.equalsIgnoreCase("betfair"))) )){
                if(this.rate.equalsIgnoreCase("0") && this.rate2.equalsIgnoreCase("0")){
                    this.f2035v.findViewById(R.id.bhavLayout).setVisibility(8);
                }else{
                    this.f2035v.findViewById(R.id.bhavLayout).setVisibility(0);
                }
                ((TextView) this.f2035v.findViewById(R.id.rateTeam)).setText(this.rate_team);
                ((TextView) this.f2035v.findViewById(R.id.bhavLeft)).setText(this.rate);
                ((TextView) this.f2035v.findViewById(R.id.bhavRight)).setText(this.rate2);

                if (this.type == 1) {
                    ((TextView) this.f2035v.findViewById(R.id.rateTeam2)).setText(this.rate_team2);
                    if(this.rateT.equalsIgnoreCase("0") && this.rateT2.equalsIgnoreCase("0")){
                        this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.bhavTeam2Layout).setVisibility(0);
                    }

                    if(this.rateD.equalsIgnoreCase("0") && this.rateD2.equalsIgnoreCase("0")){
                        this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(8);
                    }else{
                        this.f2035v.findViewById(R.id.bhavDrawLayout).setVisibility(0);
                    }


                    ((TextView) this.f2035v.findViewById(R.id.bhavTeam2Left)).setText(this.rateT);
                    ((TextView) this.f2035v.findViewById(R.id.bhavTeam2Right)).setText(this.rateT2);
                    ((TextView) this.f2035v.findViewById(R.id.bhavDrawLeft)).setText(this.rateD);
                    ((TextView) this.f2035v.findViewById(R.id.bhavDrawRight)).setText(this.rateD2);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void loadSessions(){
        try{

            this.old_ss1 = this.new_ss1;
            this.old_ss2 = this.new_ss2;

            ((TextView) this.f2035v.findViewById(R.id.sessionLeft)).setText(this.session);
            ((TextView) this.f2035v.findViewById(R.id.overRight)).setText(this.session2);
            ((TextView) this.f2035v.findViewById(R.id.session_overs)).setText(this.session_over);
            ((TextView) this.f2035v.findViewById(R.id.sessionExtraMin)).setText("Min : " + this.session_min);
            ((TextView) this.f2035v.findViewById(R.id.sessionExtraMax)).setText("Max : " + this.session_max);
            ((TextView) this.f2035v.findViewById(R.id.sessionExtraOpen)).setText("Open : " + this.session_open);
            if (session_over.equals("0")) {
                this.f2035v.findViewById(R.id.overLayout).setVisibility(8);
            } else {
                this.f2035v.findViewById(R.id.overLayout).setVisibility(0);
            }
            int sessionPassRun = Integer.parseInt(this.session2) - Integer.parseInt(this.score);
            String sessionOvers = "";
            int i = 0;
            if(this.session_over.startsWith("Match")){
                this.session_over = this.session_over ;
            } else if((this.session_over.contains("1 st"))){
                this.session_over = this.session_over.replace("1 st", "");
            }else if((this.session_over.contains("1st"))){
                this.session_over = this.session_over.replace("1st", "");
            }else if((this.session_over.contains("2 nd"))){
                this.session_over =this.session_over.replace("2 nd", "");
            }else if((this.session_over.contains("2nd"))){
                this.session_over = this.session_over.replace("2nd", "");
            }else if((this.session_over.contains("2nd"))){
                this.session_over = this.session_over.replace("2nd", "");
            } else{
                this.session_over = this.session_over ;
            }
            int length = this.session_over.length();
            if(length>15){
                length = 15;
            }
            while (i < length) {
                if (this.session_over.charAt(i) > '/' && this.session_over.charAt(i) < ':') {
                    sessionOvers = sessionOvers + this.session_over.charAt(i);
                }
                i++;
            }


            if (sessionOvers.trim() != "") {
                ((TextView) this.f2035v.findViewById(R.id.sessionPassLeft)).setText("" + ((Integer.parseInt(sessionOvers) * 6) - Integer.parseInt(this.ballsdone)));
                ((TextView) this.f2035v.findViewById(R.id.sessionPassRight)).setText("" + sessionPassRun);
            }
            if (session2.equals("0")) {
                ((TextView) this.f2035v.findViewById(R.id.sessionLeft)).setText("-");
                ((TextView) this.f2035v.findViewById(R.id.overRight)).setText("-");
                ((TextView) this.f2035v.findViewById(R.id.sessionPassLeft)).setText("-");
                ((TextView) this.f2035v.findViewById(R.id.sessionPassRight)).setText("-");
            }
            this.new_ss1 = this.session;
            this.new_ss2 = this.session2;
            if (!this.new_ss1.equals(this.old_ss1) && !this.new_ss1.equals("0") && this.SessionSpeechOn) {
                if (("" + this.new_ss1).equals("" + this.new_ss2)) {
                    this.tts.speak(this.new_ss1 + " का नब्बे eleven", 1, null);
                } else {
                    this.tts.speak(this.new_ss1, 1, null);
                    this.tts.playSilence(300, 1, null);
                    this.tts.speak(this.new_ss2, 1, null);
                }
                Log.d(this.TAG, "onResponse: Session Speech ON");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void GetData(){
        JsonObjectRequest lotusRequest = new JsonObjectRequest(Request.Method.GET,Global.LOTUS_URL+EventId, null, new C14572(), new C14583());
        requestQueue.add(lotusRequest);
    }

    class C14572 implements Response.Listener<JSONObject> {
        C14572() {
        }

        public void onResponse(JSONObject response) {
            try {
                SessionList = new ArrayList<>();
                JSONArray results = response.getJSONArray("result");


                JSONObject events = results.getJSONObject(0).getJSONObject("event");
                //MatchTitle.setText(events.getString("name"));
                for (int i = 0; i < results.length(); i++) {
                    if (results.getJSONObject(i).getString("btype").equalsIgnoreCase("LINE")) {
                        JSONArray runners = results.getJSONObject(i).getJSONArray("runners");
                        for (int j = 0; j < runners.length(); j++) {
                            Session_Model list = new Session_Model();
                            list.setStatus(results.getJSONObject(i).getString("statusLabel"));
                            list.setRateTeam(runners.getJSONObject(j).getString("name"));
                            JSONArray back = runners.getJSONObject(j).getJSONArray("back");
                            JSONArray lay = runners.getJSONObject(j).getJSONArray("lay");
                            for (int k = 0; k < back.length(); k++) {
                                if (results.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")) {
                                    list.setLineRight(back.getJSONObject(k).getString("line"));
                                    list.setPriceRight(back.getJSONObject(k).getString("price"));
                                }

                            }
                            for (int l = 0; l < lay.length(); l++) {
                                if (results.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")) {
                                    list.setLineLeft(lay.getJSONObject(l).getString("line"));
                                    list.setPriceLeft(lay.getJSONObject(l).getString("price"));
                                    SessionList.add(list);
                                }

                            }


                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (SessionList.size() == 0) {
                    Lotus_Main_Lay.setVisibility(8);
                } else {
                    Lotus_Main_Lay.setVisibility(0);

                    liveSession_adapter = new LiveSession_Adapter(getActivity(), SessionList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    Sessions_Rec.setLayoutManager(linearLayoutManager);
                    Sessions_Rec.setAdapter(liveSession_adapter);
                    Sessions_Rec.setNestedScrollingEnabled(false);
                    //liveSession_adapter.notifyDataSetChanged();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class C14583 implements Response.ErrorListener {
        C14583() {
        }

        public void onErrorResponse(VolleyError error) {
            try {
                Toast.makeText(getActivity(), "Internet Error.", 0);
            } catch (Exception e) {
            }
        }
    }


    private void getlinks(){

        try{
            JsonArrayRequest linksRequest = new JsonArrayRequest(Request.Method.POST,Global.Comman_Url+"intrestingLinks", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        if (response.getJSONObject(0).has("telegram_link")){
                            telegram = Uri.parse(response.getJSONObject(0).getString("telegram_link"));
                        }
                        if (response.getJSONObject(0).has("whatsapp_link")){
                            whatsapp = Uri.parse(response.getJSONObject(0).getString("whatsapp_link"));
                        }
                        if (response.getJSONObject(0).has("tips_link")){
                            tips = Uri.parse(response.getJSONObject(0).getString("tips_link"));
                        }
                        if (response.getJSONObject(0).has("youtube_link")){
                            youtube = Uri.parse(response.getJSONObject(0).getString("youtube_link"));
                        }
                        if (response.getJSONObject(0).has("betfair_link")){
                            betfair = Uri.parse(response.getJSONObject(0).getString("betfair_link"));
                        }
                        if (response.getJSONObject(0).has("apple_link")){
                            apple = Uri.parse(response.getJSONObject(0).getString("apple_link"));
                        }
                        if (response.getJSONObject(0).has("facebook_link")){
                            facebook = Uri.parse(response.getJSONObject(0).getString("facebook_link"));
                        }
                        if (response.getJSONObject(0).has("website_link")){
                            website = Uri.parse(response.getJSONObject(0).getString("website_link"));
                        }
                        if (response.getJSONObject(0).has("exchange_link")){
                            exchange = Uri.parse(response.getJSONObject(0).getString("exchange_link"));
                        }
                        if (response.getJSONObject(0).has("twitter_link")){
                            twitter = Uri.parse(response.getJSONObject(0).getString("twitter_link"));
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }},new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            linksRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(linksRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void shareLinks(){

        imvapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(apple != null) {
                    try {
                        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, apple);
                        startActivity(whatsappIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }

                }else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });

        imvbetfair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent betfairIntent = new Intent(getActivity(), BetfairMainActivity.class);
                startActivity(betfairIntent);
            }
        });

        imvplaystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        imvexchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                exchange = Uri.parse("https://www.youtube.com/watch?v=-GPxPvysVyI");

                if (exchange != null){
                    try {
                        Intent exchangeIntent = new Intent(Intent.ACTION_VIEW, exchange);
                        startActivity(exchangeIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });
        imvtelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                telegram = Uri.parse("https://t.me/joinchat/AAAAAEQHPWBBwOZDF7UaoA");

                if (telegram != null){
                    try {
                        Intent telegramIntent = new Intent(Intent.ACTION_VIEW, telegram);
                        startActivity(telegramIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();

            }
        });
        imvwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (website != null){
                    try {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, website);
                        startActivity(webIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });
        imvwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                whatsapp = Uri.parse("https://wa.me/919015716259");

                if (whatsapp != null){
                    try {
                        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, whatsapp);
                        startActivity(whatsappIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });
        imvyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                youtube = Uri.parse("https://www.youtube.com/watch?v=-GPxPvysVyI");

                if (youtube != null){
                    try {
                        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtube);
                        startActivity(youtubeIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();

            }
        });
        imvfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                facebook = Uri.parse("https://en-gb.facebook.com/Skool24x7/");

                if (facebook != null){
                    try {
                        Intent facebookIntent = new Intent(Intent.ACTION_VIEW, facebook);
                        startActivity(facebookIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });
        imvtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tips = Uri.parse("https://www.youtube.com/watch?v=-GPxPvysVyI");

                if (tips != null){
                    try {
                        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, tips);
                        startActivity(whatsappIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });

        imvtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                twitter = Uri.parse("https://twitter.com/AndroidDev");

                if (twitter != null){
                    try {
                        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, twitter);
                        startActivity(whatsappIntent);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getActivity(), "No link is available", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void GetTiedMatchRates(){
        try{
            JsonObjectRequest lotusRequest = new JsonObjectRequest(Request.Method.GET,Global.LOTUS_URL+EventId, null, new Success_Tie(), new Fail_Tie());
            requestQueue.add(lotusRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class Success_Tie implements Response.Listener<JSONObject> {
        Success_Tie() {
        }

        public void onResponse(JSONObject response) {
            try {
                SessionList = new ArrayList<>();
                JSONArray results = response.getJSONArray("result");


                JSONObject events = results.getJSONObject(0).getJSONObject("event");
                //MatchTitle.setText(events.getString("name"));
                for (int i = 0; i < results.length(); i++) {
                    if (results.getJSONObject(i).getString("mtype").equalsIgnoreCase("TIED_MATCH")) {
                        JSONArray runners = results.getJSONObject(i).getJSONArray("runners");
                        for (int j = 0; j < runners.length(); j++) {
                            Session_Model list = new Session_Model();
                            list.setStatus(results.getJSONObject(i).getString("statusLabel"));
                            list.setRateTeam(runners.getJSONObject(j).getString("name"));
                            JSONArray back = runners.getJSONObject(j).getJSONArray("back");
                            JSONArray lay = runners.getJSONObject(j).getJSONArray("lay");
                            for (int k = 0; k < 1; k++) {
                                if (results.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")) {
                                    if(lay.length()>0){
                                        list.setLineRight(lay.getJSONObject(k).getString("price"));
                                        list.setPriceRight(lay.getJSONObject(k).getString("size"));
                                    }else{
                                        list.setLineRight("0");
                                        list.setPriceRight("0");
                                    }

                                }

                            }
                            for (int l = 0; l < 1; l++) {
                                if (results.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")) {
                                    if(back.length()>0){
                                        list.setLineLeft(back.getJSONObject(l).getString("price"));
                                        list.setPriceLeft(back.getJSONObject(l).getString("size"));
                                    }else{
                                        list.setLineLeft("0");
                                        list.setPriceLeft("0");
                                    }

                                    SessionList.add(list);
                                }

                            }


                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                if (SessionList.size() == 0) {
                    Tie_Match_Main.setVisibility(8);
                } else {
                    Tie_Match_Main.setVisibility(0);

                    liveTieSessionAdapter = new LiveTieSessionAdapter(getActivity(), SessionList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    Tie_Session_Rec.setLayoutManager(linearLayoutManager);
                    Tie_Session_Rec.setAdapter(liveTieSessionAdapter);
                    Tie_Session_Rec.setNestedScrollingEnabled(false);
                    //liveSession_adapter.notifyDataSetChanged();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }


    class Fail_Tie implements Response.ErrorListener {
        Fail_Tie() {
        }

        public void onErrorResponse(VolleyError error) {
            try {
                Toast.makeText(getActivity(), "Internet Error.", 0);
            } catch (Exception e) {
            }
        }
    }


    public void GetCompleteMatchRates(){
        try{
            JsonObjectRequest lotusRequest = new JsonObjectRequest(Request.Method.GET,Global.LOTUS_URL+EventId, null, new Success_Complete(), new Fail_Complete());
            requestQueue.add(lotusRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class Success_Complete implements Response.Listener<JSONObject> {
        Success_Complete() {
        }

        public void onResponse(JSONObject response) {
            try {
                SessionList = new ArrayList<>();
                JSONArray results = response.getJSONArray("result");


                JSONObject events = results.getJSONObject(0).getJSONObject("event");
                //MatchTitle.setText(events.getString("name"));
                for (int i = 0; i < results.length(); i++) {
                    if (results.getJSONObject(i).getString("mtype").equalsIgnoreCase("COMPLETED_MATCH")) {
                        JSONArray runners = results.getJSONObject(i).getJSONArray("runners");
                        for (int j = 0; j < runners.length(); j++) {
                            Session_Model list = new Session_Model();
                            list.setStatus(results.getJSONObject(i).getString("statusLabel"));
                            list.setRateTeam(runners.getJSONObject(j).getString("name"));
                            JSONArray back = runners.getJSONObject(j).getJSONArray("back");
                            JSONArray lay = runners.getJSONObject(j).getJSONArray("lay");
                            for (int k = 0; k < 1; k++) {
                                if (results.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")) {
                                    if(lay.length()>0){
                                        list.setLineRight(lay.getJSONObject(k).getString("price"));
                                        list.setPriceRight(lay.getJSONObject(k).getString("size"));
                                    }else{
                                        list.setLineRight("0");
                                        list.setPriceRight("0");
                                    }

                                }

                            }
                            for (int l = 0; l < 1; l++) {
                                if (results.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")) {
                                    if(back.length()>0){
                                        list.setLineLeft(back.getJSONObject(l).getString("price"));
                                        list.setPriceLeft(back.getJSONObject(l).getString("size"));
                                    }else{
                                        list.setLineLeft("0");
                                        list.setPriceLeft("0");
                                    }

                                    SessionList.add(list);
                                }

                            }


                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                if (SessionList.size() == 0) {
                    Complete_Match_Main.setVisibility(8);
                } else {
                    Complete_Match_Main.setVisibility(0);

                    liveTieSessionAdapter = new LiveTieSessionAdapter(getActivity(), SessionList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    Completed_Session_Rec.setLayoutManager(linearLayoutManager);
                    Completed_Session_Rec.setAdapter(liveTieSessionAdapter);
                    Completed_Session_Rec.setNestedScrollingEnabled(false);
                    //liveSession_adapter.notifyDataSetChanged();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }


    class Fail_Complete implements Response.ErrorListener {
        Fail_Complete() {
        }

        public void onErrorResponse(VolleyError error) {
            try {
                Toast.makeText(getActivity(), "Internet Error.", 0);
            } catch (Exception e) {
            }
        }
    }


    public void PlayGif(String Char){
        try{
            if(Char.equalsIgnoreCase("6")){
                Loader.setBackgroundResource(R.drawable.gif_six);
            }else{
                Loader.setBackgroundResource(R.drawable.gif);
            }
            Loader.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Loader.setVisibility(View.GONE);
                }
            }, 3000);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
