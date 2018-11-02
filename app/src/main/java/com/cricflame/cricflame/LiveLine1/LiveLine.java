package com.cricflame.cricflame.LiveLine1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.LiveLine1.Commentries.Commentary;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.LiveLineScoreboard;
import com.cricflame.cricflame.LiveLine1.Sessions.SessionsActivity;
import com.cricflame.cricflame.LiveLine1.PitchReport.PitchReport;
import com.cricflame.cricflame.MainActivity;
import com.cricflame.cricflame.R;

import com.cricflame.cricflame.SplashActivity;
import com.cricflame.cricflame.betfair.BetfairMainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class LiveLine extends AppCompatActivity {


    DatabaseReference target;//=m1.child("Target");
    DatabaseReference firstbatcountry;
    DatabaseReference firstbatover;
    DatabaseReference firstbatscore;
    DatabaseReference firstbatwicket;

    DatabaseReference rootBatsman;//=m1.child("Batsman1");
    DatabaseReference rootBatsman2;//=m1.child("Batsman2");
    DatabaseReference Batsman1striker;//=rootBatsman.child("Striker");
    DatabaseReference Batsman2striker;//=rootBatsman2.child("Striker");
    DatabaseReference m1;
    DatabaseReference m1_name;//=m1.child("Country");
    DatabaseReference m1_score;//=m1.child("Score");
    DatabaseReference m1_over;//=m1.child("Over");
    DatabaseReference m1_wiket;//=m1.child("Wicket");
    DatabaseReference m1_runrate;//=m1.child("RunRate");
    DatabaseReference m1_ballremaining;//=m1.child("BallRemaining");
    DatabaseReference m1_favourite;//=childref3.child("Favorite");
    DatabaseReference m1_rate1;//=childref3.child("Rate1");
    DatabaseReference m1_rate2;//=childref3.child("Rate2");
    DatabaseReference m1_ssnover;//=childref3.child("SsnOver");
    DatabaseReference m1_ssn1;//=childref3.child("Ssn1");
    DatabaseReference m1_ssn2;//=childref3.child("Ssn2");
    DatabaseReference m1_rbox;//=childref3.child("RBox");
    DatabaseReference m1_bbox;//=childref3.child("Bbox");
    DatabaseReference bt;//=m1.child("Batsman1");
    DatabaseReference bt_name;//=bt.child("Name");
    DatabaseReference bt_runs;//=bt.child("Runs");
    DatabaseReference bt_balls;//=bt.child("Ball");
    DatabaseReference bt_sr;//=bt.child("BallPlayed");
    DatabaseReference bt2;//=m1.child("Batsman2");
    DatabaseReference bt2_name;//=bt2.child("Name");
    DatabaseReference bt2_runs;//=bt2.child("Runs");
    DatabaseReference bt2_balls;//=bt2.child("Ball");
    DatabaseReference bt2_sr;//=bt2.child("BallPlayed");
    DatabaseReference ball;//=m1.child("Last6Balls");
    DatabaseReference ball1;//=ball.child("1");
    DatabaseReference ball2;//=ball.child("2");
    DatabaseReference ball3;//=ball.child("3");
    DatabaseReference ball4;//=ball.child("4");
    DatabaseReference ball5;//=ball.child("5");
    DatabaseReference ball6;//=ball.child("6");
    DatabaseReference bowler;//=m1.child("Bowler");

    DatabaseReference b1_4;//=bt.child("4s");
    DatabaseReference b1_6;//=bt.child("6s");

    DatabaseReference b2_4;//=bt2.child("4s");
    DatabaseReference b2_6;//=bt2.child("6s");

    DatabaseReference rrrvalue;


    //String[] listitems={"a","b","c","d","e","f","g","h"};

    //Text Views
    TextView tv1, pageTitle;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv7;
    TextView tv8;
    TextView tv9;
    TextView tv10;
    TextView tv11;
    TextView tv12;
    TextView tv13;
    TextView tv14;
    TextView tv15;
    TextView tv16;
    TextView tv17;
    TextView tv18;
    TextView tv19;
    TextView tv20;
    TextView tv21;
    TextView tv22;
    TextView tv23;
    TextView tv24;
    TextView tv25, BallsRTV, country2tv, socresecond, secondover, secondinnwicket, RRR, RRRvalue;

    TextView Ball1, Ball2, Ball3, Ball4, Ball5, Ball6, B1Strikerast, B2Strikerast, BatsmanFirst, BatsmanSecond, currentBowler;
    TextView B1R, B1Ball, B1SR, B2SR, B2R, B2Ball, B14, B16, B24, B26, scorecard, pitchReport,dummy, dummy9, custommessage, custommessage1, custommessage2, custommessage3, custommessage4;
    ImageView back, sound;
    ;
    String value1;
    String s;
    boolean ismute = false, loaded = false;

    //ListView lv;
    int streamID = 0;
    private int inttest = 0;
    private TextView bl_over,bl_maiden,bl_run,bl_wkt,bl_econo;
    private DatabaseReference blw_over,blw_run,blw_maiden,blw_eco,blw_wicket;


    Integer fapp = 0;
    private MediaPlayer mPlayer2;
    private SoundPool soundPool;

    private int chalue, khali, single, double_run, teen, chauka, six, noball, wide, freehit, wicket;
    private AudioManager audioManager;
    private float volume;
    private TextView marqueText;
    private FirebaseApp secondApp;
    private DatabaseReference childref;
    private DatabaseReference childref3;
    private DatabaseReference matchname;
    private DatabaseReference whichinning;
    private FirebaseDatabase secondaryDatabase;
    public static DatabaseReference mRootref;
    private DatabaseReference customMessage1;
    private DatabaseReference customMessage;
    private DatabaseReference customMessage2;
    private DatabaseReference customMessage3;
    private DatabaseReference customMessage4;
    private DatabaseReference team1name;
    private DatabaseReference team2name;

    private TextView dummy11;

    private TextView country_1;
    private TextView country_2;
    private ImageView img_country_1;
    private ImageView img_country_2;
    private TextView tv_tips;
    private DatabaseReference tips;
    private RelativeLayout rl_country2, rl_rrr, rl_target;
    private TextView bet_lay_team1,bet_lay_team2,bet_back_team1,bet_back_team2;
    private ImageView bet_icon;
    private DatabaseReference df_t1_lay,df_t1_back,df_t2_lay,df_t2_back;
    private TextView bet_team1,bet_team2;
    private ImageView cricflametv_link;
    private String lang;

    TextToSpeech tts,tts1,tts2;
    private ImageView tips_telegram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_live_line_layout);

        SharedPreferences sharedPreferences = getSharedPreferences("ABC",MODE_PRIVATE);
        lang = sharedPreferences.getString("lang",null);



        fapp += 1;
        loaded = true;
        //Long tsLong = System.currentTimeMillis() / 1000;
        //String ts = tsLong.toString();

        //DB REFERENCE//
        //secondApp = FirebaseApp.initializeApp(LiveLine.this, options, ts);
        //secondaryDatabase = FirebaseDatabase.getInstance(secondApp);
       // mRootref = secondaryDatabase.getReference();
        mRootref = FirebaseUtils.getSecondaryDatabase(LiveLine.this).getReference();



        df_t1_back = mRootref.child("Betfair").child("1").child("Back");
        df_t1_lay = mRootref.child("Betfair").child("1").child("Lay");
        df_t2_back = mRootref.child("Betfair").child("2").child("Back");
        df_t2_lay = mRootref.child("Betfair").child("2").child("Lay");

        //DatabaseReference mRootref= FirebaseDatabase.getInstance().getReference();
        childref = mRootref.child("lineupdate");
        childref3 = mRootref.child("Match");
        matchname = childref3.child("Name");
        team1name = childref3.child("TeamAName");
        team2name = childref3.child("TeamBName");
        whichinning = childref3.child("CurrentInning");
        tips = childref3.child("Tips");
        ;//=childref3.child("FirstInning");

        customMessage = childref3.child("CustomMessage");
        customMessage1 = childref3.child("CustomMessage1");
        customMessage2 = childref3.child("CustomMessage2");
        customMessage3 = childref3.child("CustomMessage3");
        customMessage4 = childref3.child("CustomMessage4");


        init();
        marqueText.setSelected(true);
        MediaPlayerLoading();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        scorecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiveLine.this, LiveLineScoreboard.class);
                intent.putExtra("filename","liveline_scorecard.php");
                startActivity(intent);
            }
        });

        dummy9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LiveLine.this, Commentary.class));
            }
        });
        dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LiveLine.this, OfflineCommentaryActivity.class));
                startActivity(new Intent(LiveLine.this, SessionsActivity.class));
            }
        });
        tips_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mRootref.child("Tip").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      if(dataSnapshot.exists()){
                          String url = dataSnapshot.getValue(String.class);
                          Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                          startActivity(i);
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });
            }
        });


        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ismute == true) {
                    sound.setImageDrawable(ContextCompat.getDrawable(LiveLine.this, com.cricflame.cricflame.R.drawable.ic_volume));
                    ismute = false;
                } else {
                    sound.setImageDrawable(ContextCompat.getDrawable(LiveLine.this, com.cricflame.cricflame.R.drawable.ic_vol_mute));
                    ismute = true;
                }


            }
        });

        //Glide.with(getApplicationContext()).invalidate();

        Glide.with(getApplicationContext()).load("http://cricflame.com/admin/data/liveline/team1.png").into(img_country_1);
        Glide.with(getApplicationContext()).load("http://cricflame.com/admin/data/liveline/team2.png").into(img_country_2);


        bet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LiveLine.this, BetfairMainActivity.class));
            }
        });

        cricflametv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(LiveLine.this, LiveVideoActivity.class));
            }
        });


        chooseinning();
    }

    private void MediaPlayerLoading() {
        // Set the hardware buttons to control the music
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            }
        });


        // Load the sound
      /*  chalue = soundPool.load(this, com.cricflame.cricflame.R.raw.chalu, 1);
        khali = soundPool.load(this, com.cricflame.cricflame.R.raw.khali, 1);
        single = soundPool.load(this, com.cricflame.cricflame.R.raw.single, 1);
        double_run = soundPool.load(this, com.cricflame.cricflame.R.raw.double_run, 1);
        chauka = soundPool.load(this, com.cricflame.cricflame.R.raw.chauka, 1);
        teen = soundPool.load(this, com.cricflame.cricflame.R.raw.teen, 1);
        six = soundPool.load(this, com.cricflame.cricflame.R.raw.six, 1);
        wide = soundPool.load(this, com.cricflame.cricflame.R.raw.wide, 1);
        wicket = soundPool.load(this, com.cricflame.cricflame.R.raw.wicket, 1);
        noball = soundPool.load(this, com.cricflame.cricflame.R.raw.noball, 1);
        freehit = soundPool.load(this, com.cricflame.cricflame.R.raw.freehit, 1);*/

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
        // Is the sound loaded already?
    }

    private void setTextviewforsecondinning() {
        rl_country2.setVisibility(View.VISIBLE);
        rl_rrr.setVisibility(View.VISIBLE);
        rl_target.setVisibility(View.VISIBLE);
        tv7.setText("0");
    }

    private void prepareinning1() {

        m1 = childref3.child("FirstInning");

        //target=m1.child("Target");

        rootBatsman = m1.child("Batsman1");
        rootBatsman2 = m1.child("Batsman2");
        Batsman1striker = rootBatsman.child("Striker");
        Batsman2striker = rootBatsman2.child("Striker");

        m1_name = m1.child("Country");
        m1_score = m1.child("Score");
        m1_over = m1.child("Over");
        m1_wiket = m1.child("Wicket");
        m1_runrate = m1.child("RunRate");
        m1_ballremaining = m1.child("BallRemaining");
        m1_favourite = childref3.child("Favorite");
        m1_rate1 = childref3.child("Rate1");
        m1_rate2 = childref3.child("Rate2");
        m1_ssnover = childref3.child("SsnOver");
        m1_ssn1 = childref3.child("Ssn1");
        m1_ssn2 = childref3.child("Ssn2");
        m1_rbox = childref3.child("RBox");
        m1_bbox = childref3.child("Bbox");
        bt = m1.child("Batsman1");
        bt_name = bt.child("Name");
        bt_runs = bt.child("Runs");
        bt_balls = bt.child("Ball");
        bt_sr = bt.child("BallPlayed");
        bt2 = m1.child("Batsman2");
        bt2_name = bt2.child("Name");
        bt2_runs = bt2.child("Runs");
        bt2_balls = bt2.child("Ball");
        bt2_sr = bt2.child("BallPlayed");
        ball = m1.child("Last6Balls");
        ball1 = ball.child("1");
        ball2 = ball.child("2");
        ball3 = ball.child("3");
        ball4 = ball.child("4");
        ball5 = ball.child("5");
        ball6 = ball.child("6");
        bowler = m1.child("Bowler");

        b2_4 = bt2.child("4s");
        b2_6 = bt2.child("6s");

        b1_4 = bt.child("4s");
        b1_6 = bt.child("6s");

        blw_over = m1.child("Bowlerstats").child("over");
        blw_run = m1.child("Bowlerstats").child("runs");
        blw_maiden = m1.child("Bowlerstats").child("maiden");
        blw_eco = m1.child("Bowlerstats").child("economy");
        blw_wicket = m1.child("Bowlerstats").child("wicket");



        doAllThings();

    }

    private void prepareinning2() {
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        firstbatcountry = childref3.child("FirstInning").child("Country");
        firstbatover = childref3.child("FirstInning").child("Over");
        firstbatscore = childref3.child("FirstInning").child("Score");
        firstbatwicket = childref3.child("FirstInning").child("Wicket");
        m1 = childref3.child("SecondInning");
        rrrvalue = m1.child("RRR");

        target = m1.child("Target");

        rootBatsman = m1.child("Batsman1");
        rootBatsman2 = m1.child("Batsman2");
        Batsman1striker = rootBatsman.child("Striker");
        Batsman2striker = rootBatsman2.child("Striker");

        m1_name = m1.child("Country");
        m1_score = m1.child("Score");
        m1_over = m1.child("Over");
        m1_wiket = m1.child("Wicket");
        m1_runrate = m1.child("RunRate");
        m1_ballremaining = m1.child("BallRemaining");
        m1_favourite = childref3.child("Favorite");
        m1_rate1 = childref3.child("Rate1");
        m1_rate2 = childref3.child("Rate2");
        m1_ssnover = childref3.child("SsnOver");
        m1_ssn1 = childref3.child("Ssn1");
        m1_ssn2 = childref3.child("Ssn2");
        m1_rbox = childref3.child("RBox");
        m1_bbox = childref3.child("Bbox");
        bt = m1.child("Batsman1");
        bt_name = bt.child("Name");
        bt_runs = bt.child("Runs");
        bt_balls = bt.child("Ball");
        bt_sr = bt.child("BallPlayed");
        bt2 = m1.child("Batsman2");
        bt2_name = bt2.child("Name");
        bt2_runs = bt2.child("Runs");
        bt2_balls = bt2.child("Ball");
        bt2_sr = bt2.child("BallPlayed");
        ball = m1.child("Last6Balls");
        ball1 = ball.child("1");
        ball2 = ball.child("2");
        ball3 = ball.child("3");
        ball4 = ball.child("4");
        ball5 = ball.child("5");
        ball6 = ball.child("6");
        bowler = m1.child("Bowler");

        b2_4 = bt2.child("4s");
        b2_6 = bt2.child("6s");

        b1_4 = bt.child("4s");
        b1_6 = bt.child("6s");

        target.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = dataSnapshot.getValue(Integer.class);
                tv7.setText(i.toString());
                //tv7.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firstbatcountry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                country2tv.setText(s.toString());
                country2tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firstbatover.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float s = dataSnapshot.getValue(Float.class);
                secondover.setText("(" + s.toString() + ")");
                secondover.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firstbatscore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer s = dataSnapshot.getValue(Integer.class);
                socresecond.setText(s.toString());
                socresecond.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firstbatwicket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer s = dataSnapshot.getValue(Integer.class);
                secondinnwicket.setText("/" + s.toString());
                secondinnwicket.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rrrvalue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float f = dataSnapshot.getValue(Float.class);
                String s = String.format("%.2f", f);
                RRRvalue.setText(s);
                RRRvalue.setVisibility(View.VISIBLE);
                RRR.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        blw_over = m1.child("Bowlerstats").child("over");
        blw_run = m1.child("Bowlerstats").child("runs");
        blw_maiden = m1.child("Bowlerstats").child("maiden");
        blw_eco = m1.child("Bowlerstats").child("economy");
        blw_wicket = m1.child("Bowlerstats").child("wicket");

        doAllThings();
    }

    private void chooseinning() {
        whichinning.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = dataSnapshot.getValue(Integer.class);
                if (i == 1) {
                    //m1=childref3.child("FirstInning");
                    prepareinning1();
                    //doAllThings();
                    //Toast.makeText(Main2Activity.this, "Choose Activity For 1st Inning", Toast.LENGTH_SHORT).show();
                } else {
                    //m1=childref3.child("SecondInning");
                    setTextviewforsecondinning();
                    prepareinning2();
                    //Toast.makeText(Main2Activity.this, "Choose Activity For 2nd Inning", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Main2Activity.this, "Choose Inning Ran", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //m1 =childref3.child("FirstInning");
    }

    private void init() {
        bet_team1 = (TextView) findViewById(R.id.bet_t1);
        bet_team2 = (TextView) findViewById(R.id.bet_t2);

        bet_lay_team1 = (TextView) findViewById(R.id.bet_t1_l);
        bet_lay_team2 = (TextView) findViewById(R.id.bet_t2_l);
        bet_back_team1 = (TextView) findViewById(R.id.bet_t1_b);
        bet_back_team2 = (TextView) findViewById(R.id.bet_t2_b);
        bet_icon = (ImageView) findViewById(R.id.bet_icon);
        tips_telegram = (ImageView) findViewById(R.id.image_logo2);
        cricflametv_link = (ImageView) findViewById(R.id.cricflame_tv);




        rl_country2 = (RelativeLayout) findViewById(R.id.rl_country2);
        rl_rrr = (RelativeLayout) findViewById(R.id.rl_rrr);
        rl_target = (RelativeLayout) findViewById(R.id.rl_target);

        BallsRTV = (TextView) findViewById(com.cricflame.cricflame.R.id.BallsRTV);

        dummy = (TextView) findViewById(com.cricflame.cricflame.R.id.dummy);
        dummy9 = (TextView) findViewById(com.cricflame.cricflame.R.id.dummy2);
        marqueText = (TextView) findViewById(com.cricflame.cricflame.R.id.dummy9);
        custommessage = (TextView) findViewById(com.cricflame.cricflame.R.id.customData);
        custommessage1 = (TextView) findViewById(com.cricflame.cricflame.R.id.customData1);
        custommessage2 = (TextView) findViewById(com.cricflame.cricflame.R.id.customData2);
        custommessage3 = (TextView) findViewById(com.cricflame.cricflame.R.id.customData3);
        custommessage4 = (TextView) findViewById(com.cricflame.cricflame.R.id.customData4);

        pageTitle = (TextView) findViewById(com.cricflame.cricflame.R.id.page_title);
        tv1 = (TextView) findViewById(com.cricflame.cricflame.R.id.LiveData);
        tv2 = (TextView) findViewById(com.cricflame.cricflame.R.id.tvCountry);
        tv3 = (TextView) findViewById(com.cricflame.cricflame.R.id.tvScorerun);
        tv4 = (TextView) findViewById(com.cricflame.cricflame.R.id.textwicket);
        tv5 = (TextView) findViewById(com.cricflame.cricflame.R.id.textover);
        tv6 = (TextView) findViewById(com.cricflame.cricflame.R.id.RunRate);
        tv7 = (TextView) findViewById(com.cricflame.cricflame.R.id.BallsRemaining);
        tv8 = (TextView) findViewById(com.cricflame.cricflame.R.id.fav_team);
        tv9 = (TextView) findViewById(com.cricflame.cricflame.R.id.Marketrate1);
        tv10 = (TextView) findViewById(com.cricflame.cricflame.R.id.Marketrate_two);
        tv11 = (TextView) findViewById(com.cricflame.cricflame.R.id.ssnover);
        tv12 = (TextView) findViewById(com.cricflame.cricflame.R.id.session1);
        tv13 = (TextView) findViewById(com.cricflame.cricflame.R.id.session2);
        tv14 = (TextView) findViewById(com.cricflame.cricflame.R.id.RBox);
        tv15 = (TextView) findViewById(com.cricflame.cricflame.R.id.BBox);
        tv16 = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman1);
        tv17 = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman1score);
        tv18 = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman1ball);
        tv19 = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman2);
        tv20 = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman2run);
        tv21 = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman2over);
        tv22 = (TextView) findViewById(com.cricflame.cricflame.R.id.bowler1);

        Ball1 = (TextView) findViewById(com.cricflame.cricflame.R.id.ball1);
        Ball2 = (TextView) findViewById(com.cricflame.cricflame.R.id.ball2);
        Ball3 = (TextView) findViewById(com.cricflame.cricflame.R.id.ball3);
        Ball4 = (TextView) findViewById(com.cricflame.cricflame.R.id.ball4);
        Ball5 = (TextView) findViewById(com.cricflame.cricflame.R.id.ball5);
        Ball6 = (TextView) findViewById(com.cricflame.cricflame.R.id.ball6);

        B1R = (TextView) findViewById(com.cricflame.cricflame.R.id.B1R);
        B1SR = (TextView) findViewById(com.cricflame.cricflame.R.id.B1SR);
        B1Ball = (TextView) findViewById(com.cricflame.cricflame.R.id.B1Ball);
        B2SR = (TextView) findViewById(com.cricflame.cricflame.R.id.B2SR);
        B2R = (TextView) findViewById(com.cricflame.cricflame.R.id.B2R);
        B2Ball = (TextView) findViewById(com.cricflame.cricflame.R.id.B2Ball);

        bl_over = (TextView) findViewById(com.cricflame.cricflame.R.id.bl_over);
        bl_maiden = (TextView) findViewById(com.cricflame.cricflame.R.id.bl_maiden);
        bl_run = (TextView) findViewById(com.cricflame.cricflame.R.id.bl_run);
        bl_wkt = (TextView) findViewById(com.cricflame.cricflame.R.id.bl_wkt);
        bl_econo = (TextView) findViewById(com.cricflame.cricflame.R.id.bl_econo);


        BatsmanFirst = (TextView) findViewById(com.cricflame.cricflame.R.id.BatsmanFirst);
        BatsmanSecond = (TextView) findViewById(com.cricflame.cricflame.R.id.BatsmanSecond);
        currentBowler = (TextView) findViewById(com.cricflame.cricflame.R.id.BowlerName);

        B1Strikerast = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman1Strike);
        B2Strikerast = (TextView) findViewById(com.cricflame.cricflame.R.id.batsman2strike);

        B14 = (TextView) findViewById(com.cricflame.cricflame.R.id.B14);
        B16 = (TextView) findViewById(com.cricflame.cricflame.R.id.B16);
        B24 = (TextView) findViewById(com.cricflame.cricflame.R.id.B24);
        B26 = (TextView) findViewById(com.cricflame.cricflame.R.id.B26);
        scorecard = (TextView) findViewById(com.cricflame.cricflame.R.id.scoreboard);
        // pitchReport = (TextView) findViewById(com.cricflame.cricket.R.id.pitch_report);

        country2tv = (TextView) findViewById(com.cricflame.cricflame.R.id.country2tv);
        socresecond = (TextView) findViewById(com.cricflame.cricflame.R.id.scoresecond);
        secondover = (TextView) findViewById(com.cricflame.cricflame.R.id.secondover);
        secondinnwicket = (TextView) findViewById(com.cricflame.cricflame.R.id.secondinnwicket);

        RRR = (TextView) findViewById(com.cricflame.cricflame.R.id.RRR);
        RRRvalue = (TextView) findViewById(com.cricflame.cricflame.R.id.RRRvalue);
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);

        //lv=(ListView)findViewById(R.id.lvruns);

        dummy11 = (TextView) findViewById(R.id.dummy11);
        country_1 = (TextView) findViewById(R.id.country_1);
        country_2 = (TextView) findViewById(R.id.country_2);

        img_country_1 = (ImageView) findViewById(R.id.img_country_1);
        img_country_2 = (ImageView) findViewById(R.id.img_country_2);

        tv_tips = (TextView) findViewById(R.id.tv_tips);


        //done by shiddhant
        sound = (ImageView) findViewById(com.cricflame.cricflame.R.id.sound);
    }


    private void playsound(final String s) {
        inttest += 1;

        // soundPool.setVolume(streamID, 0f, 0f);
        if(lang.equals("Hindi")) {


            if (!ismute && inttest > 1 && loaded) {
                // soundPool.play(soundPool.load(this, R.raw.chalu, 1), volume, volume, 1, 0, 1f);
                if (s.contains("Ball Started")) {
                    //streamID = soundPool.play(chalue, volume, volume, 1, 0, 1f);
                    tts1= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {
                                if(lang.equals("Hindi")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("hin-IND"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                            tts1.speak(" Ball Chalu Ball",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }else if(lang.equals("English")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("eng-US"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Ball Started",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }



                            }
                        }
                    });


                } else if (s.contains("0 Run")) {
                    //streamID = soundPool.play(khali, volume, volume, 1, 0, 1f);
                    //tts.speak("Khali",TextToSpeech.QUEUE_FLUSH,null);

                    tts1= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {
                                if(lang.equals("Hindi")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("hin-IND"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Khali khali khali",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }else if(lang.equals("English")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("eng-US"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Dot Ball",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }



                            }
                        }
                    });

                } else if (s.contains("1 Run")) {
                    //streamID = soundPool.play(single, volume, volume, 1, 0, 1f);
                    tts1= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {
                                if(lang.equals("Hindi")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("hin-IND"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Single aya single",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }else if(lang.equals("English")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("eng-US"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Single",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }



                            }
                        }
                    });



                } else if (s.contains("2 Runs")) {
                    //streamID = soundPool.play(double_run, volume, volume, 1, 0, 1f);
                    tts1= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {
                                if(lang.equals("Hindi")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("hin-IND"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Double Aya Double",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }else if(lang.equals("English")&&!ismute){
                                    int result = tts1.setLanguage(new Locale("eng-US"));
                                    tts1.setSpeechRate(Float.parseFloat("0.85"));
                                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                        Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        tts1.speak("Double",TextToSpeech.QUEUE_FLUSH,null);
                                    }
                                }



                            }
                        }
                    });


                } else if (s.contains("3 Runs")) {


                    //streamID = soundPool.play(teen, volume, volume, 1, 0, 1f);
                } else if (s.contains("4 Runs")) {

                   // streamID = soundPool.play(chauka, volume, volume, 1, 0, 1f);
                } else if (s.contains("6 Runs")) {

                    //streamID = soundPool.play(six, volume, volume, 1, 0, 1f);

                } else if (s.contains("NB") || s.contains("0NB") || s.contains("1NB") || s.contains("2NB") || s.contains("3NB") || s.contains("4NB") || s.contains("6NB")) {
                   // streamID = soundPool.play(noball, volume, volume, 1, 0, 1f);


                } else if (s.contains("Wide") || s.contains("0 Wide") || s.contains("1 Wide") || s.contains("2 Wide") || s.contains("3 Wide") || s.contains("4 Wide")) {

                    //streamID = soundPool.play(wide, volume, volume, 1, 0, 1f);
                } else if (s.contains("Freehit")) {

                   // streamID = soundPool.play(freehit, volume, volume, 1, 0, 1f);
                } else if (s.contains("Catch Out") || s.contains("L B W Out") ||
                        s.contains("Bowled Out") || s.contains("Out Stumpped") ||
                        s.contains("Hit-Wicket Out") || s.contains("Retired Hurt Out")) {

                    //streamID = soundPool.play(wicket, volume, volume, 1, 0, 1f);

                }
            }
        }else if(!ismute){
            tts2= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        tts2.speak(s,TextToSpeech.QUEUE_FLUSH,null);
                    }
                }
            });
        }


    }


    private void doAllThings() {

        tips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    tv_tips.setText(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        team1name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                country_1.setText(dataSnapshot.getValue(String.class));
                bet_team1.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        team2name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                country_2.setText(dataSnapshot.getValue(String.class));
                bet_team2.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        customMessage4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                    if (dataSnapshot.exists()) {
                        String s = dataSnapshot.getValue(String.class);
                        custommessage4.setText(s.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        customMessage3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    custommessage3.setText(s.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        customMessage2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    custommessage2.setText(s.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        customMessage1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    custommessage1.setText(s.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        customMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    custommessage.setText(s.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bt_sr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float s = dataSnapshot.getValue(Float.class);
                String s1 = String.format("%.2f", s);
                B1SR.setText(s1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt2_sr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float s = dataSnapshot.getValue(Float.class);
                String f = String.format("%.2f", s);
                B2SR.setText(f);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Batsman1striker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean stBatsman1 = dataSnapshot.getValue(Boolean.class);
                if (stBatsman1) {
                    B1Strikerast.setVisibility(View.VISIBLE);
                    B2Strikerast.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Batsman2striker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean stBatsman2 = dataSnapshot.getValue(Boolean.class);
                if (stBatsman2) {
                    B1Strikerast.setVisibility(View.INVISIBLE);
                    B2Strikerast.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tv1.setText(value.toString());
                String s = value.toString();
                playsound(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ball1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.getValue(String.class);

                    Ball1.setText(a);

                    //Ball1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ball2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.getValue(String.class);

                    Ball2.setText(a);

                    // Ball2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ball3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.getValue(String.class);

                    Ball3.setText(a);

                    // Ball3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ball4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.getValue(String.class);

                    Ball4.setText(a);

                    //Ball4.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ball5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.getValue(String.class);

                    Ball5.setText(a);

                    //Ball5.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ball6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.getValue(String.class);

                    Ball6.setText(a);

                    // Ball6.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        matchname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //value1 = dataSnapshot.getValue(String.class);
                // tv3.setText(value.toString());
                //changeName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tv2.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_score.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv3.setText(value.toString() + "/");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_wiket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                //wicket=value.toString();
                tv4.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_over.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float value = dataSnapshot.getValue(Float.class);
                tv5.setText("(" + value.toString() + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_runrate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float value = dataSnapshot.getValue(Float.class);
                tv6.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_favourite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tv8.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_rate1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv9.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_rate2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv10.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_ssnover.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv11.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_ssn1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Integer value = dataSnapshot.getValue(Integer.class);
                tv12.setText(value.toString());
                tts= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            if(lang.equals("Hindi")&&!ismute){
                                int result = tts.setLanguage(new Locale("hin-IND"));
                                if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                    Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();

                                }else{
                                    tts.speak(value.toString(),TextToSpeech.QUEUE_FLUSH,null);
                                }
                            }else if(lang.equals("English")&&!ismute){
                                int result = tts.setLanguage(new Locale("eng-US"));
                                if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                    Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                }else{
                                    tts.speak(value.toString(),TextToSpeech.QUEUE_FLUSH,null);
                                }
                            }



                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_ssn2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Integer value = dataSnapshot.getValue(Integer.class);
                tv13.setText(value.toString());
                tts1= new TextToSpeech(LiveLine.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            if(lang.equals("Hindi")&&!ismute){
                                int result = tts1.setLanguage(new Locale("hin-IND"));
                                if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                    Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                }else{
                                    tts1.speak(value.toString(),TextToSpeech.QUEUE_FLUSH,null);
                                }
                            }else if(lang.equals("English")&&!ismute){
                                int result = tts1.setLanguage(new Locale("eng-US"));
                                if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                                    Toast.makeText(LiveLine.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                                }else{
                                    tts1.speak(value.toString(),TextToSpeech.QUEUE_FLUSH,null);
                                }
                            }



                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_rbox.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv14.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        m1_bbox.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv15.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tv16.setText(value.toString());
                BatsmanFirst.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt_runs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv17.setText(value.toString());
                B1R.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt_balls.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv18.setText("(" + value.toString() + ")");
                B1Ball.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt2_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tv19.setText(value.toString());
                BatsmanSecond.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt2_runs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv20.setText(value.toString());
                B2R.setText(value.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt2_balls.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                tv21.setText("(" + value.toString() + ")");
                B2Ball.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bowler.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tv22.setText(value.toString());
                currentBowler.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b1_4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = dataSnapshot.getValue(Integer.class);
                B14.setText(i.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b1_6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = dataSnapshot.getValue(Integer.class);
                B16.setText(i.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b2_4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = dataSnapshot.getValue(Integer.class);
                B24.setText(i.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b2_6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = dataSnapshot.getValue(Integer.class);
                B26.setText(i.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dummy11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To Do For Pitch Report
                startActivity(new Intent(LiveLine.this, PitchReport.class));
            }
        });



        blw_over.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Float value = dataSnapshot.getValue(Float.class);
                    bl_over.setText(value.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        blw_maiden.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Integer value = dataSnapshot.getValue(Integer.class);
                    bl_maiden.setText(value.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        blw_run.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Integer i = dataSnapshot.getValue(Integer.class);
                    bl_run.setText(i.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        blw_wicket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Integer i = dataSnapshot.getValue(Integer.class);
                    bl_wkt.setText(i.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        blw_eco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Float i = dataSnapshot.getValue(Float.class);
                    bl_econo.setText(i.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        df_t1_lay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String i = dataSnapshot.getValue(String .class);
                    bet_lay_team1.setText(i);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        df_t1_back.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String i = dataSnapshot.getValue(String .class);
                    bet_back_team1.setText(i);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        df_t2_back.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String i = dataSnapshot.getValue(String .class);
                    bet_back_team2.setText(i);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        df_t2_lay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String i = dataSnapshot.getValue(String .class);
                    bet_lay_team2.setText(i);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        //Toast.makeText(this, "On start running", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loaded = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaded = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        loaded = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        loaded = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaded = false;
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        if(tts1 !=null){
            tts1.stop();
            tts1.shutdown();
        }

        if(tts2 !=null){
            tts2.stop();
            tts2.shutdown();
        }
    }

}