package com.cricflame.cricflame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Adapter.PointTable_Series_Adapter;
import com.cricflame.cricflame.Adapter.SeriesWise_Adapter;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.DBHelper.DBHelper;
import com.cricflame.cricflame.Fragment.LiveMatchFragment;
import com.cricflame.cricflame.LiveLine1.PitchReport.AnalysisActivity;
import com.cricflame.cricflame.Model.LiveMatchDetails;
import com.cricflame.cricflame.DayByDay.DaybydayMatchesResult;
import com.cricflame.cricflame.DayByDay.DaybydayScheduleMatches;
import com.cricflame.cricflame.DummyBooks.MakeMatchActivity;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.News.NewsDetail;
import com.cricflame.cricflame.Model.AllMatchData;
import com.cricflame.cricflame.Model.BottomNavigationViewHelper;
import com.cricflame.cricflame.Notification.Model.Config;
import com.cricflame.cricflame.Notification.Utils.NotificationUtils;
import com.cricflame.cricflame.News.NewsActivity;
import com.cricflame.cricflame.News.NewsModel;
import com.cricflame.cricflame.Notification.NotificationListActivity;
import com.cricflame.cricflame.Quiz.Activity_Play_Login;
import com.cricflame.cricflame.Quiz.Activity_play_now;
import com.cricflame.cricflame.Records.RecordActivity;
import com.cricflame.cricflame.ScheduleMatches.ScheduleMatches;
import com.cricflame.cricflame.Standing.StandingActivity;
import com.cricflame.cricflame.TipsTricks.GameListActivity;
import com.cricflame.cricflame.Adapter.ViewImagePager;
import com.cricflame.cricflame.Model.NewsData;
import com.cricflame.cricflame.Model.TourData;
//import com.cricflame.cricket.livestream.LadoPlayer;
import com.cricflame.cricflame.Util.ConnectionDetector;
import com.cricflame.cricflame.Util.SessionManager_Play_Now;
import com.cricflame.cricflame.betfair.BetfairMainActivity;
import com.cricflame.cricflame.betfair.BetfairMarketPrice;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import com.cricflame.cricflame.Chat.login.LoginActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.datatype.Duration;

import static com.cricflame.cricflame.Fragment.Team1ScoreBoard_Fragment.setListViewHeightBasedOnChildren;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SwitchCompat switcher;


    public ArrayList<NewsModel> newsArrayData = new ArrayList<NewsModel>();
    //public ArrayList<NewsData> newsArrayData = new ArrayList<NewsData>();
    RecyclerView news_list;
    DBHelper dbHelper;
    News_Adapter news_adapter;
    SeriesWise_Adapter seriesWise_adapter;
    ViewPager viewPagerImage;
    RecyclerView rcvSeries;
    ViewPager videosPager;
    VideoPagerAdapter videoPagerAdapter;
    TabLayout videoTabs;
    TabLayout imagwSliderDots;

    // LinearLayout getSliderDotspanelImage,getSliderDotspanelVideo;
    private int dotscount1,dotcount2;
    private ImageView[] dots, dots1;
    //    VideoListAdapter1 mHAdapter;
    ImageView notification;
    Handler mHandler;
    // Runnable m_Runnable;
    int scrollDy = 0;
    int moveTo = 0;
    ViewPagerAdapter viewPagerAdapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Parcelable recyclerViewState;
    public ArrayList<AllMatchData> allMatches = new ArrayList<>();
    public ArrayList<TourData> allMatchImages;
    public ArrayList<TourData> videoList;
    public ArrayList<SeriesWise_Model> seriesList;
    public ArrayList<String> photoListUrl = new ArrayList<>();

    public static String MatchId = "";

    private DatabaseReference mRootref;
    private DatabaseReference childref;
    ArrayList<LiveMatchDetails> fliveMatchDetailsArrayList;
    ArrayList<LiveMatchDetails> liveMatchDetailsArrayList;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    ArrayList<Pair<DatabaseReference, ValueEventListener>> pairsOfDatabaseandListener;
    ScoreAdapter scoreAdapter;
    ViewPager liveMatchviewPager;
    TabLayout tabLayout;
    boolean shouldMove = false;
    boolean shouldReload = false;
    String connectionUrl="";
    FirebaseDatabase firebaseDatabase=null;
    int movetoLive=0;
    int dbInstance;
    int UniversalPosition;
    SessionManager_Play_Now sessionManager;
    public static Handler handler = new Handler();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (drawer.isDrawerOpen(GravityCompat.END)) {
                        drawer.closeDrawer(GravityCompat.END);
                    } else {
                        drawer.openDrawer(GravityCompat.END);
                    }
                    return true;


                case R.id.navigation_liveline:

                    /*Intent liveLineIntent = new Intent(MainActivity.this, LiveLineActivity.class);*/
                    Intent liveLineIntent = new Intent(MainActivity.this, LiveLineActivity2.class);
                    startActivity(liveLineIntent);
                    return true;
                case R.id.navigation_betfair:

                    Intent betfairIntent = new Intent(MainActivity.this, BetfairMainActivity.class);
                    startActivity(betfairIntent);

                    return true;

                case R.id.navigation_chat:
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);

                    startActivity(loginIntent);
                    return true;

//                case com.cricflame.cricflame.R.id.navigation_videos:
//
//
//                    Intent galleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
//                    startActivity(galleryIntent);
//                    return true

                case R.id.navigation_fixture:
                    startActivity(new Intent(MainActivity.this, ScheduleMatches.class));
                    return true;
            }
            return false;

        }


    };
    private Parcelable recyclerViewState_video;
    private DrawerLayout drawer;

    private DatabaseReference mRootref1;
    private DatabaseReference df_news;
    private DatabaseReference mRootRef;
    ConnectionDetector cd;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_main);
        cd = new ConnectionDetector(MainActivity.this);
        sessionManager = new SessionManager_Play_Now(MainActivity.this);
        init();

        try{
            connectionUrl = Global.LIVEMATCH_URL;
            firebaseDatabase = FirebaseDatabase.getInstance(connectionUrl);
        }catch (Exception e){
            e.printStackTrace();
            try{
                if((dbHelper.getFieldValueUser("SplashUrl")!=null)){
                    JSONObject jObj = new JSONObject(dbHelper.getFieldValueUser("SplashUrl"));
                    Global.LIVEMATCH_URL = jObj.getString("LIVEMATCH_URL");
                    connectionUrl = Global.LIVEMATCH_URL;
                    firebaseDatabase = FirebaseDatabase.getInstance(connectionUrl);
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }

        }



        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.cricflame.cricflame.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(true);
        }
        BottomNavigationViewHelper.disableShiftMode(navigation);

        setTitle(null);

        Toolbar toolbar = (Toolbar) findViewById(com.cricflame.cricflame.R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.cricflame.cricflame.R.string.navigation_drawer_open, com.cricflame.cricflame.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.cricflame.cricflame.R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_right_view);

        /*For Live-line sound*/
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_switch);
        View actionView = MenuItemCompat.getActionView(menuItem);
        switcher = (SwitchCompat) actionView.findViewById(R.id.switcher);
        final SharedPreferences sharedPrefManager = getSharedPreferences("ABC", MODE_PRIVATE);

        String lang = sharedPrefManager.getString("lang", null);
        if (lang != null) {
            if (lang.equals("Hindi")) {
                switcher.setChecked(true);
            } else {
                switcher.setChecked(false);
            }
        } else {
            switcher.setChecked(true);
            SharedPreferences.Editor editor = sharedPrefManager.edit();
            editor.putString("lang", "Hindi");
            editor.apply();
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPrefManager.edit();
                editor.putString("lang", switcher.isChecked() ? "Hindi" : "English");
                editor.apply();
                // Toast.makeText(MainActivity.this, switcher.isChecked()?"checked":"not checked", Toast.LENGTH_SHORT).show();
            }
        });

        navigationView2.setNavigationItemSelectedListener(this);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationListActivity.class));
            }
        });

        NotificationBroadcastReceiver();

        if (dbHelper.getFieldValueUser("HomeArray")!= null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    homeArrayLocal();
                }
            }, 1000);

        }
        else Homearray();

        /*for news*/
        GetNewsFirebase();

        this.shouldReload = true;
        this.shouldMove = true;

    }


    public void GetNewsFirebase(){
        try{
            mRootref1 = FirebaseUtils.getThirdDatabase(MainActivity.this).getReference();
            df_news = mRootref1.child("News");

            ItemClickSupport.addTo(news_list)
                    .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            news_adapter = (News_Adapter) recyclerView.getAdapter();
                            NewsModel newsData = (NewsModel) newsArrayData.get(position);
                            Intent intent = new Intent(MainActivity.this, NewsDetail.class);
                            intent.putExtra("Date", newsData.getDate());
                            intent.putExtra("Headline", newsData.getHeadline());
                            intent.putExtra("Para", newsData.getPara());
                            intent.putExtra("Image", newsData.getImage());
                            intent.putExtra("Intro", newsData.getIntro());
                            intent.putExtra("Source", newsData.getSource());
                            intent.putExtra("dataFrom","firebase");
                            startActivity(intent);
                        }
                    });
            df_news.orderByChild("id").limitToLast(5)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newsArrayData.clear();
                            if (!dataSnapshot.hasChildren()) {
                                Toast.makeText(MainActivity.this, "No Record Found...", Toast.LENGTH_SHORT).show();
                            }

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                newsArrayData.add(snapshot.getValue(NewsModel.class));
                            }

                            Collections.reverse(newsArrayData);

                            news_adapter = new News_Adapter(MainActivity.this, newsArrayData);
                            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
                            //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            news_list.setLayoutManager(mLayoutManager);
                            news_list.setNestedScrollingEnabled(false);
                            news_list.setAdapter(news_adapter);
                            //news_adapter.notifyDataSetChanged();
                            //setListViewHeightBasedOnChildren(news_list);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class News_Adapter extends RecyclerView.Adapter<News_Adapter.MyViewHolder>{
        Context context;
        ArrayList<NewsModel> Items;
        NewsModel newsModel;

        public News_Adapter(Context context, ArrayList<NewsModel> Items) {
            this.context = context;
            this.Items = Items;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_home_layout_view,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            newsModel = Items.get(position);
            holder.subheading.setText(newsModel.getIntro());
            holder.Title.setText(newsModel.getHeadline());
            holder.Duration.setText(newsModel.getDate());
            holder.Description.setText(newsModel.getPara());

            if(newsModel.getImage() == null || newsModel.getImage().equalsIgnoreCase("")){
                Glide.with(context).load(R.drawable.app_logo).into(holder.Image);
            }else{
                Glide.with(context).load(newsModel.getImage()).into(holder.Image);
            }
        }

        @Override
        public int getItemCount() {
            return Items == null ? 0: Items.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView subheading,Title,Duration,Description;
            ImageView Image;
            public MyViewHolder(View itemView) {
                super(itemView);
                subheading = itemView.findViewById(R.id.news_heading);
                Title = itemView.findViewById(R.id.title);
                Duration = itemView.findViewById(R.id.duration);
                Description = itemView.findViewById(R.id.description);
                Image = itemView.findViewById(R.id.news_image);
            }
        }
    }



    private void init() {

        dbHelper = new DBHelper(this);

        notification = (ImageView) findViewById(com.cricflame.cricflame.R.id.notification);
        viewPagerImage = (ViewPager) findViewById(com.cricflame.cricflame.R.id.viewPagerImage);
        rcvSeries = (RecyclerView) findViewById(R.id.rcv_series);
        imagwSliderDots = findViewById(R.id.SliderDotsImage);
        //getSliderDotspanelImage = (LinearLayout) findViewById(com.cricflame.cricflame.R.id.SliderDotsImage);
        videosPager = (ViewPager) findViewById(R.id.videosPager);
        news_list =  findViewById(R.id.latest_news);
        videoTabs = (TabLayout) findViewById(R.id.videoTabs);

        this.requestQueue = Volley.newRequestQueue(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        this.liveMatchDetailsArrayList = new ArrayList();
        this.fliveMatchDetailsArrayList = new ArrayList();
        this.pairsOfDatabaseandListener = new ArrayList();
        liveMatchviewPager = (ViewPager) findViewById(R.id.scores_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //progressBar.setVisibility(View.GONE);

        MainActivity.this.liveMatchviewPager.addOnPageChangeListener(new C13831());

    }


    class C13831 implements ViewPager.OnPageChangeListener {
        C13831() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            MainActivity.this.moveTo = position;
            Log.d("moversz_ch", position + "");
        }

        public void onPageScrollStateChanged(int state) {
        }
    }
    public void NotificationBroadcastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // this.mHandler.postDelayed(m_Runnable,5000);
        //  this.mHandler.postDelayed(m_Runnable,5000);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());


        if (cd.isConnectingToInternet()){

            if (this.shouldReload) {
                getLiveMatches(this.shouldMove);
                this.shouldMove = false;
            } else {
                this.shouldReload = true;
                this.shouldMove = false;
            }
        }else {
            Toast.makeText(this, "Please check your internet connection and Restart the app", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(8);
            liveMatchviewPager.setVisibility(0);
            tabLayout.setVisibility(0);
        }

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        for (int i = 0; i < this.pairsOfDatabaseandListener.size(); i++) {
            ((DatabaseReference) ((Pair) this.pairsOfDatabaseandListener.get(i)).first).removeEventListener((ValueEventListener) ((Pair) this.pairsOfDatabaseandListener.get(i)).second);
        }
        this.pairsOfDatabaseandListener.clear();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.cricflame.cricflame.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == com.cricflame.cricflame.R.id.browse_series) {
            Intent home = new Intent(MainActivity.this, BrowseSeries.class);
            startActivity(home);

        } else if (id == com.cricflame.cricflame.R.id.browse_team) {
            startActivity(new Intent(MainActivity.this, BrowseTeamActivity.class));

        } else if (id == com.cricflame.cricflame.R.id.browse_player) {
            startActivity(new Intent(MainActivity.this, BrowsePlayer.class));

        } else if (id == com.cricflame.cricflame.R.id.time_pass) {
            startActivity(new Intent(MainActivity.this, GameListActivity.class));

        }else if (id == com.cricflame.cricflame.R.id.daybyday_schedule) {
            startActivity(new Intent(MainActivity.this, DaybydayScheduleMatches.class));

        }else if (id == com.cricflame.cricflame.R.id.daybyday_result) {
            startActivity(new Intent(MainActivity.this, DaybydayMatchesResult.class));

        } else if (id == com.cricflame.cricflame.R.id.live_video) {
            //startActivity(new Intent(MainActivity.this, LiveVideoActivity.class));
        } else if (id == com.cricflame.cricflame.R.id.cricket_polls) {
            startActivity(new Intent(MainActivity.this, PollActivity.class));

        } else if (id == com.cricflame.cricflame.R.id.dummy_books) {
            startActivity(new Intent(MainActivity.this, MakeMatchActivity.class));

        } else if (id == com.cricflame.cricflame.R.id.schedule_match) {
            startActivity(new Intent(MainActivity.this, ScheduleMatches.class));

        } else if (id == com.cricflame.cricflame.R.id.news) {
            startActivity(new Intent(MainActivity.this, NewsActivity.class));

        } else if (id == R.id.icc_ranking) {
            // startActivity(new Intent(MainActivity.this, ICCRanking.class));
            startActivity(new Intent(MainActivity.this, ICCRanking_Activity.class));

        } else if (id == R.id.standing) {
            // startActivity(new Intent(MainActivity.this, StandingActivity.class));
            startActivity(new Intent(MainActivity.this, PointsTable_Activity.class));

        } else if (id == com.cricflame.cricflame.R.id.share) {
            try {
                final String appPackageName = getPackageName();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "CricFlame");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + appPackageName;
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Share Via"));
            } catch (Exception e) {

            }
        } else if (id == com.cricflame.cricflame.R.id.rate_us) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }


        } else if (id == R.id.feedback) {
            startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
        } else if (id == R.id.right_records) {
            startActivity(new Intent(MainActivity.this, RecordActivity.class));
        } else if (id == R.id.right_helps) {
            Intent help = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(help);
        }else if (id == R.id.upcoming_rates) {
            Intent help = new Intent(MainActivity.this, UpcomingRatesActivity.class);
            startActivity(help);
        }  else if (id == R.id.games) {
          /*  Intent help = new Intent(MainActivity.this, GamesActivity.class);
            startActivity(help);*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.cricflame.cricflame.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private class VideoPagerAdapter extends PagerAdapter {

        Context context1;
        LayoutInflater layoutInflater1;

        public  ArrayList<TourData> videoList;

        public VideoPagerAdapter(Context context,ArrayList<TourData> products) {

            this.context1 = context;
            this.videoList = products;
        }

        @Override
        public int getCount() {
            return videoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView viewImage;

            layoutInflater1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater1.inflate(R.layout.cutome_video_list, container, false);
            // ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //imageView.setImageResource(images[position]);

            // Locate the TextViews in viewpager_item.xml
            viewImage = (ImageView) view.findViewById(R.id.videoImage);
            TextView txtVideoTitle = (TextView) view.findViewById(R.id.videoTitle);

            txtVideoTitle.setText(videoList.get(position).getHeading());
            Glide.with(context1).load(Global.VIDEODATA_URL+videoList.get(position).getLeague_name()).into(viewImage);

            ((ViewPager) container).addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(videoList.get(position).getMonth()));
                    startActivity(i);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }


    }


    private class ScoreAdapter extends PagerAdapter {

        Context context1;
        List<LiveMatchDetails> live;
        LayoutInflater layoutInflater1;


        public ScoreAdapter(Context context,List<LiveMatchDetails> live) {

            this.context1 = context;
            this.live  = live;
        }

        public int getItemPosition(Object object) {
            LiveMatchDetails liveMatchDetails = (LiveMatchDetails) ((View) object).getTag();
            // Log.d("issuez", "" + MainActivity.this.fliveMatchDetailsArrayList.indexOf(liveMatchDetails));
            if (MainActivity.this.fliveMatchDetailsArrayList.contains(liveMatchDetails)) {
                return MainActivity.this.fliveMatchDetailsArrayList.indexOf(liveMatchDetails);
            }
            return -2;
        }

        @Override
        public int getCount() {
            return live.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final LiveMatchDetails live = (LiveMatchDetails) MainActivity.this.fliveMatchDetailsArrayList.get(position);
            layoutInflater1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = this.layoutInflater1.inflate(R.layout.match_score_test, container, false);
            // view.setTag(live);
            final LinearLayout MainViewlay = view.findViewById(R.id.main_match_view_lay);
            LinearLayout Analysis = view.findViewById(R.id.analysis_lay);
            LinearLayout Sereis_Home = view.findViewById(R.id.serieshome_lay);
            LinearLayout Play_Lay = view.findViewById(R.id.play_lay);
            MainViewlay.setTag(live);
            Analysis.setTag(live);
            Play_Lay.setTag(live);
            Sereis_Home.setTag(live);
            ImageView flag1 =  view.findViewById(R.id.team1_card_logo_image_view);
            ImageView flag2 =  view.findViewById(R.id.team2_card_logo_image_view);
            TextView team1 = (TextView) view.findViewById(R.id.team1_name_card);
            TextView team2 = (TextView) view.findViewById(R.id.team2_name_card);
            TextView score1 = (TextView) view.findViewById(R.id.score1_card);
            TextView score2 = (TextView) view.findViewById(R.id.score2_card);
            TextView over1 = (TextView) view.findViewById(R.id.over1_card);
            TextView over2 = (TextView) view.findViewById(R.id.over2_card);
            TextView odds1 = (TextView) view.findViewById(R.id.odds1_card);
            TextView odds2 = (TextView) view.findViewById(R.id.odds2_card);
            TextView topcomment = (TextView) view.findViewById(R.id.top_match_card_details);
            TextView venue = (TextView) view.findViewById(R.id.venue_view);
            final TextView matchStatus = (TextView) view.findViewById(R.id.match_card_status);
            TextView bottomcomment = (TextView) view.findViewById(R.id.bottom_match_card_details);
            if (!live.getFlag1().trim().equals("")) {
                //flag1.setImageURI(Uri.parse(live.get(position).getFlag1()));
                Glide.with(context1).load(live.getFlag1()).into(flag1);
            }
            if (!live.getFlag2().trim().equals("")) {
                //flag2.setImageURI(Uri.parse(live.getFlag2()));
                Glide.with(context1).load(live.getFlag2()).into(flag2);
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(live.getSeries_name());
            spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 33);
            topcomment.setText(spannableStringBuilder);
            bottomcomment.setText(live.getComment());
            if (live.getType().trim().equals("1") || live.getRate().trim().equals("") || (Double.parseDouble(live.getRate()) == 0.0d && Double.parseDouble(live.getRate2()) == 0.0d)) {
                ((LinearLayout) view.findViewById(R.id.odds_card_layout)).setBackground(context1.getResources().getDrawable(R.drawable.transparent));
                odds1.setText("");
                odds2.setText("");
                view.findViewById(R.id.white_separator).setVisibility(8);
                ((TextView) view.findViewById(R.id.card_rate_team)).setText("");
            } else {
                odds1.setText(live.getRate());
                odds2.setText(live.getRate2());
                ((TextView) view.findViewById(R.id.card_rate_team)).setText(live.getRate_team());
            }
            venue.setText(live.getMatch_number());
            if (live.getStatus().equals("0")) {
                matchStatus.setText("UPCOMING");
                over1.setVisibility(8);
                over2.setVisibility(8);
                score1.setVisibility(8);
                score2.setVisibility(8);
                ((TextView) view.findViewById(R.id.full_team1_name_card)).setVisibility(0);
                ((TextView) view.findViewById(R.id.full_team1_name_card)).setText(live.getTeam1());
                ((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);
                ((TextView) view.findViewById(R.id.full_team2_name_card)).setText(live.getTeam2());
                team1.setText(live.getT1());
                team2.setText(live.getT2());
            } else {
                if (live.getStatus().equals("1")) {
                    matchStatus.setText("LIVE");
                } else {
                    matchStatus.setText("RESULT");
                }
                team1.setText(live.getT1());
                team2.setText(live.getT2());
                if (live.getInning().equals("1")) {
                    if(live.getType().equalsIgnoreCase("1")){
                        if(live.getI2().equalsIgnoreCase("0") || live.getI2().equalsIgnoreCase("")){
                            score1.setText(live.getScore() + "/" + live.getWicket() + (live.getWicket().trim().equals("10") ? "" : "*"));
                            score2.setText("- / -");
                            if(live.getBallsdone().equalsIgnoreCase("")){
                                over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                            }else{
                                over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                            }

                            over2.setText("Yet to bat");

                        }else{
                            if(live.getIsSuperOver().equalsIgnoreCase("1")){
                                score1.setText(live.getScore() + "/" + live.getWicket() + (live.getWicket().trim().equals("10") ? "" : "*"));
                                score2.setText(live.getScore2() + "/" + live.getWicket2());
                                if(live.getBallsdone().equalsIgnoreCase("")){
                                    over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                }else{
                                    over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                                }

                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setText("Inn 1 - "+live.getI1());
                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setVisibility(0);
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setText("Inn 2 - "+live.getI2());
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);
                                if(live.getBallsdone2().equalsIgnoreCase("")){
                                    over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                }else{
                                    over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                                }
                            } else{
                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setText("Inn 1 - "+live.getI1());
                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setVisibility(0);
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setText("Inn 2 - "+live.getI2());
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);
                                score1.setText(live.getScore() + "/" + live.getWicket() + (live.getWicket().trim().equals("10") ? "" : "*"));
                                score2.setText("- / -");
                                if(live.getBallsdone().equalsIgnoreCase("")){
                                    over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                }else{
                                    over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                                }


                                over2.setText("Yet to bat");
                            }

                        }
                    }else{
                        score1.setText(live.getScore() + "/" + live.getWicket() + (live.getWicket().trim().equals("10") ? "" : "*"));
                        score2.setText("- / -");
                        if(live.getBallsdone().equalsIgnoreCase("")){
                            over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                        }else{
                            over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                        }

                        over2.setText("Yet to bat");
                    }


                } else {
                    if(live.getType().equalsIgnoreCase("1")) {
                        if (live.getI3().equalsIgnoreCase("0") && live.getI2().equalsIgnoreCase("0")) {
                            score1.setText(live.getScore() + "/" + live.getWicket());
                            score2.setText(live.getScore2() + "/" + live.getWicket2() + (live.getWicket2().trim().equals("10") ? "" : "*"));
                            if(live.getBallsdone().equalsIgnoreCase("")){
                                over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                            }else{
                                over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                            }

                            if(live.getBallsdone2().equalsIgnoreCase("")){
                                over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                            }else{
                                over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                            }
                        }else{
                            if(live.getIsCricBuzz().equalsIgnoreCase("0")){

                                if(live.getI2().equalsIgnoreCase("0")){
                                    score1.setText(live.getScore() + "/" + live.getWicket());
                                    score2.setText(live.getScore2() + "/" + live.getWicket2() + (live.getWicket2().trim().equals("10") ? "" : "*"));

                                    if(live.getBallsdone().equalsIgnoreCase("")){
                                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                    }else{
                                        over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                                    }

                                    if(live.getBallsdone2().equalsIgnoreCase("")){
                                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                    }else{
                                        over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                                    }
                                }else if(!live.getI3().equalsIgnoreCase("0") && (!live.getI2().equalsIgnoreCase("0"))){
                                    score1.setText(live.getI3());
                                    ((TextView) view.findViewById(R.id.full_team2_name_card)).setText("Inn 2 - "+live.getI2());
                                    ((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);
                                    score2.setText(live.getScore2() + "/" + live.getWicket2() + (live.getWicket2().trim().equals("10") ? "" : "*"));
                                    if(live.getBallsdone().equalsIgnoreCase("")){
                                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                    }else{
                                        over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                                    }

                                    if(live.getBallsdone2().equalsIgnoreCase("")){
                                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                    }else{
                                        over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                                    }


                                    ((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);

                                } else{
                                    score1.setText(live.getScore() + "/" + live.getWicket() + (live.getWicket().trim().equals("10") ? "" : "*"));
                                    score2.setText(live.getI2());

                                    if(live.getBallsdone().equalsIgnoreCase("")){
                                        over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                    }else{
                                        over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                                    }

                                    if(live.getBallsdone2().equalsIgnoreCase("")){
                                        over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                    }else{
                                        over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                                    }
                                    over2.setVisibility(View.GONE);
                                }



                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setText("Inn 1 - "+live.getI1());
                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setVisibility(0);
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setText("Inn 2 - "+live.getI2());
                                //((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);

                            }else{
                                score1.setText(live.getScore() + "/" + live.getWicket());
                                score2.setText(live.getScore2() + "/" + live.getWicket2() + (live.getWicket2().trim().equals("10") ? "" : "*"));
                                if(live.getBallsdone().equalsIgnoreCase("")){
                                    over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                }else{
                                    over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                                }

                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setText("Inn 1 - "+live.getI1());
                                ((TextView) view.findViewById(R.id.full_team1_name_card)).setVisibility(0);
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setText("Inn 1 - "+live.getI2());
                                ((TextView) view.findViewById(R.id.full_team2_name_card)).setVisibility(0);
                                if(live.getBallsdone().equalsIgnoreCase("")){
                                    over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                                }else{
                                    over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                                }
                            }


                        }
                    }else{
                        score1.setText(live.getScore() + "/" + live.getWicket());
                        score2.setText(live.getScore2() + "/" + live.getWicket2() + (live.getWicket2().trim().equals("10") ? "" : "*"));
                        if(live.getBallsdone().equalsIgnoreCase("")){
                            over1.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                        }else{
                            over1.setText(ballsToOver(Integer.parseInt(live.getBallsdone())) + " OVERS");
                        }

                        if(live.getBallsdone().equalsIgnoreCase("")){
                            over2.setText(ballsToOver(Integer.parseInt("0")) + " OVERS");
                        }else{
                            over2.setText(ballsToOver(Integer.parseInt(live.getBallsdone2())) + " OVERS");
                        }

                    }
                    try{
                        if (live.getComment().trim().equals("")) {
                            //if(live.getIsCricBuzz().equalsIgnoreCase("0")){
                            try{
                                bottomcomment.setText((Integer.parseInt(live.getTarget()) - Integer.parseInt(live.getScore2())) + " runs needed in " + (Integer.parseInt(live.getTotal_balls()) - Integer.parseInt(live.getBallsdone2())) + " balls");
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            //}

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
            container.addView(view);
            MainViewlay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Bundle analyticBundle = new Bundle();
                    analyticBundle.putString(FirebaseAnalytics.Param.ITEM_ID, live.getKey());
                    analyticBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "match");
                    startActivity(new Intent(MainActivity.this, NewLiveMatchActivity.class).putExtra("key", live.getKey()).putExtra("type", Integer.parseInt(live.getType())).putExtra("team1", live.getT1()).putExtra("team2", live.getT2()).putExtra("flag1", live.getFlag1()).putExtra("flag2", live.getFlag2()).putExtra("team1_full", live.getTeam1()).putExtra("team2_full", live.getTeam2()).putExtra("status", live.getStatus()).putExtra("matchStatus",matchStatus.getText().toString().trim()).putExtra("comment",live.getComment())
                            .putExtra("score1",live.getScore() + "/" + live.getWicket()).putExtra("over1",ballsToOver(Integer.parseInt(live.getBallsdone())))
                            .putExtra("score2",live.getScore2() + "/" + live.getWicket2()).putExtra("over2",ballsToOver(Integer.parseInt(live.getBallsdone2())))
                            .putExtra("id",live.getId())
                            .putExtra("scorecardid",live.getScoreCardId())
                            .putExtra("ISCricBuzz",live.getIsCricBuzz())
                            .putExtra("EventID",live.getEventId())
                            .putExtra("MarketId",live.getMarket_Id())
                            .putExtra("i1",live.getI1())
                            .putExtra("i2",live.getI2())
                            .putExtra("i3",live.getI3())
                            .putExtra("oddsType",live.getOddstype())
                            .putExtra("sessionType",live.getSessionType()));
                    // Toast.makeText(MainActivity.this, "Start Live Line",Toast.LENGTH_LONG).show();

                }
            });

            Sereis_Home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* Intent i = new Intent(MainActivity.this , ScheduleDetail.class);
                    i.putExtra("tour_name",live.getSeries_name());
                    i.putExtra("Type","ALL");
                    i.putExtra("data",1);
                    startActivity(i);*/
                    TastyToast.makeText(MainActivity.this, "Coming Soon", TastyToast.LENGTH_LONG, TastyToast.INFO);
                }
            });

            Analysis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this , AnalysisActivity.class);
                    i.putExtra("id",live.getId());
                    startActivity(i);

                }
            });

            Play_Lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TastyToast.makeText(MainActivity.this, "Coming Soon", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    if(sessionManager.isLoggedIn()){
                        Intent i = new Intent(MainActivity.this , Activity_play_now.class);
                        i.putExtra("key",live.getKey());
                        i.putExtra("team1", live.getT1());
                        i.putExtra("team2", live.getT2());
                        i.putExtra("flag1", live.getFlag1());
                        i.putExtra("flag2", live.getFlag2());
                        startActivity(i);
                    }else{
                        Intent i = new Intent(MainActivity.this , Activity_Play_Login.class);
                        i.putExtra("key",live.getKey());
                        i.putExtra("team1", live.getT1());
                        i.putExtra("team2", live.getT2());
                        i.putExtra("flag1", live.getFlag1());
                        i.putExtra("flag2", live.getFlag2());
                        startActivity(i);
                    }

                }
            });

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }


    }

    public static String ballsToOver(int balls) {
        return (balls / 6) + "." + (balls % 6);
    }

    public void getLiveMatches(final boolean move) {
        //requestQueue.add(new JsonObjectRequest(connectionUrl+"/liveMatches.json", null, new Response.Listener<JSONObject>() {
        requestQueue.add(new JsonObjectRequest(connectionUrl+"/liveMatchWithCricExch.json", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try{

                    int i;
                    if (cd.isConnectingToInternet()){

                        progressBar.setVisibility(0);
                        liveMatchviewPager.setVisibility(8);
                        tabLayout.setVisibility(8);
                    }else {
                        progressBar.setVisibility(8);
                        liveMatchviewPager.setVisibility(0);
                        tabLayout.setVisibility(0);
                    }

                    Iterator objects = response.keys();
                    MainActivity.this.liveMatchDetailsArrayList.clear();
                    for (i = 0; i < MainActivity.this.pairsOfDatabaseandListener.size(); i++) {
                        ((DatabaseReference) ((Pair) MainActivity.this.pairsOfDatabaseandListener.get(i)).first).removeEventListener((ValueEventListener) ((Pair) MainActivity.this.pairsOfDatabaseandListener.get(i)).second);
                    }
                    MainActivity.this.pairsOfDatabaseandListener.clear();
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
                                String id  = match.getString("id");
                                String ScoreCardid = match.getString("id1");
                                String IsCricBuzz = match.getString("cb");
                                String EventId = match.getString("eventid");
                                String MarketId = match.getString("market_Id");

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

                                LiveMatchDetails liveMatchDetails = new LiveMatchDetails(key, ballsdone, ballsdone2, comment, date, str, str2, inning, match_number, rate, rate2, rate_team, score, score2, series_name, status, t1, t2, team1, team2, title, type, venue, wicket, wicket2, target, totalballs, order,id, ScoreCardid,IsCricBuzz,EventId,MarketId,i1,i2,i3,oddsType,SessionType,IsSuperOver);
                                MainActivity.this.liveMatchDetailsArrayList.add(liveMatchDetails);
                                MainActivity.this.setListener(liveMatchDetails);
                                //Has to Implement Later
                                // MainActivity.this.setListener(liveMatchDetails);
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        } catch (JSONException e22) {
                            e22.printStackTrace();
                        }
                    }
                    Collections.sort(MainActivity.this.liveMatchDetailsArrayList);
                    Log.d("lcount", "listner live " + MainActivity.this.liveMatchDetailsArrayList.size());
                    if (MainActivity.this.liveMatchDetailsArrayList.size() > 0) {
                        // MainActivity.this.shouldReload = false;
                    }
                    fliveMatchDetailsArrayList = new ArrayList(MainActivity.this.liveMatchDetailsArrayList);
                    //For Removing Dublicate Values
                    for (int in = 0; in <= fliveMatchDetailsArrayList.size() - 1; in++) {
                        for (int j = fliveMatchDetailsArrayList.size() - 1; j > in; j--) {
                            if (fliveMatchDetailsArrayList.get(j).getT1().trim().equalsIgnoreCase(fliveMatchDetailsArrayList.get(in).getT1().trim())
                                    && fliveMatchDetailsArrayList.get(j).getT2().trim().equalsIgnoreCase(fliveMatchDetailsArrayList.get(in).getT2().trim())
                                    && fliveMatchDetailsArrayList.get(j).getStatus().equalsIgnoreCase(fliveMatchDetailsArrayList.get(in).getStatus())) {
                                if(fliveMatchDetailsArrayList.get(j).getIsCricBuzz().equalsIgnoreCase("1")){
                                    fliveMatchDetailsArrayList.remove(j);

                                }else{
                                    fliveMatchDetailsArrayList.remove(in);
                                    if(in == 0){
                                        j = fliveMatchDetailsArrayList.size();
                                    }
                                }

                            }
                        }
                    }
                    //For Removing Dublicate Values
                    scoreAdapter = new ScoreAdapter(MainActivity.this,fliveMatchDetailsArrayList);
                    liveMatchviewPager.setOffscreenPageLimit(4);
                    tabLayout.setupWithViewPager(liveMatchviewPager, true);
                    liveMatchviewPager.setPageMargin(dpToPx(-16, getApplicationContext()));
                    liveMatchviewPager.setAdapter(scoreAdapter);
                    progressBar.setVisibility(8);
                    liveMatchviewPager.setVisibility(0);
                    tabLayout.setVisibility(0);
                    try {
                        Log.d("crit", "load1 arr " + MainActivity.this.fliveMatchDetailsArrayList.size() + "  adapter " + MainActivity.this.scoreAdapter.getCount() + " pager " + liveMatchviewPager.getChildCount());
                    } catch (Exception e3) {
                        Log.d("crit", "onLoad1 exception");
                    }

                    if (move) {
                        for (i = 0; i < MainActivity.this.liveMatchDetailsArrayList.size(); i++) {
                            if (((LiveMatchDetails) MainActivity.this.liveMatchDetailsArrayList.get(i)).getOrder().trim().equals("0")) {
                                MainActivity.this.moveTo = i;
                                if (MainActivity.this.liveMatchviewPager != null) {
                                    MainActivity.this.liveMatchviewPager.setCurrentItem(MainActivity.this.moveTo);
                                }
                                Log.d("moversz", "onLoad yesMove" + MainActivity.this.moveTo);
                                //Toast.makeText(MainActivity.this,liveMatchDetailsArrayList.get(moveTo).getDate(),Toast.LENGTH_SHORT).show();
                                //GetMarketPrice("0",moveTo);
                                return;
                            }
                        }
                        Log.d("moversz", "onLoad yesMove" + MainActivity.this.moveTo);
                        return;
                    }
                    if (MainActivity.this.liveMatchviewPager != null) {
                        MainActivity.this.liveMatchviewPager.setCurrentItem(MainActivity.this.moveTo);
                    }
                    Log.d("moversz", "onLoad noMove" + MainActivity.this.moveTo);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    public static int dpToPx(int dp, Context context) {
        return Math.round(((float) dp) * (context.getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    public void setListener(final LiveMatchDetails l) {
        if (!l.isAd() && l.getStatus().trim().equals("1")) {
            ValueEventListener valueEventListener = new C13818();
            if(firebaseDatabase!=null){
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("liveMatchWithCricExch").child(l.getKey());
                databaseReference.addValueEventListener(valueEventListener);
                pairsOfDatabaseandListener.add(new Pair(databaseReference, valueEventListener));
            }
        }
    }

    class C13818 implements ValueEventListener {
        C13818() {
        }
        public void onDataChange(DataSnapshot dataSnapshot) {
            try{

                final String key = dataSnapshot.getKey();
                String ballsdone = dataSnapshot.child("ballsdone").getValue().toString();
                String ballsdone2 = dataSnapshot.child("ballsdone2").getValue().toString();
                String comment = dataSnapshot.child("comment").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String flag1 = "";
                String flag2 = "";
                String target = "";
                String totalballs = "";
                try {
                    flag1 = dataSnapshot.child("flag1").getValue().toString();
                    flag2 = dataSnapshot.child("flag2").getValue().toString();
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
                String MarketId = dataSnapshot.child("market_Id").getValue().toString();

                String i1="0";
                String i2="0";
                String i3="0";
                if(type.equalsIgnoreCase("1")){
                    i1 = dataSnapshot.child("i1").getValue().toString();
                    i2 = dataSnapshot.child("i2").getValue().toString();
                    i3 = dataSnapshot.child("i3").getValue().toString();
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

                String oddsType = dataSnapshot.child("oddstype").getValue().toString();
                String SessionType = dataSnapshot.child("sessiontype").getValue().toString();
                String IsSuperOver = "0";
                if(dataSnapshot.hasChild("isSuperOver")){
                    IsSuperOver = dataSnapshot.child("isSuperOver").getValue().toString();
                }

                LiveMatchDetails liveMatchDetails = new LiveMatchDetails(dataSnapshot.getKey(), ballsdone, ballsdone2, comment, date, flag1, flag2, inning, match_number, rate, rate2, rate_team, score, score2, series_name, status, t1, t2, team1, team2, title, type, venue, wicket, wicket2, target, totalballs, order,id, ScoreCardid,IsCricBuzz,EventId,MarketId,i1,i2,i3,oddsType,SessionType,IsSuperOver);
                int i = 0;
                while (i < MainActivity.this.liveMatchDetailsArrayList.size()) {
                    if (((LiveMatchDetails) MainActivity.this.liveMatchDetailsArrayList.get(i)).getKey().equals(key)) {
                        MainActivity.this.liveMatchDetailsArrayList.set(i, liveMatchDetails);
                        break;
                    }
                    i++;
                }
                Log.d("Check  : ", "Outside");
                if (i == MainActivity.this.liveMatchDetailsArrayList.size()) {
                    Log.d("Check: ", "Inside");
                    MainActivity.this.liveMatchDetailsArrayList.add(liveMatchDetails);
                    Collections.sort(MainActivity.this.liveMatchDetailsArrayList);
                }
                Log.d("lcount", "listner live " + MainActivity.this.liveMatchDetailsArrayList.size());

                MainActivity.this.fliveMatchDetailsArrayList = new ArrayList(MainActivity.this.liveMatchDetailsArrayList);
                //For Removing Dublicate Values
                for (int in = 0; in <= fliveMatchDetailsArrayList.size() - 1; in++) {
                    for (int j = fliveMatchDetailsArrayList.size() - 1; j > in; j--) {
                        if (fliveMatchDetailsArrayList.get(j).getT1().trim().equalsIgnoreCase(fliveMatchDetailsArrayList.get(in).getT1().trim())
                                && fliveMatchDetailsArrayList.get(j).getT2().trim().equalsIgnoreCase(fliveMatchDetailsArrayList.get(in).getT2().trim())
                                && fliveMatchDetailsArrayList.get(j).getStatus().equalsIgnoreCase(fliveMatchDetailsArrayList.get(in).getStatus())) {
                            if(fliveMatchDetailsArrayList.get(j).getIsCricBuzz().equalsIgnoreCase("1")){
                                fliveMatchDetailsArrayList.remove(j);

                            }else{
                                fliveMatchDetailsArrayList.remove(in);
                                if(in == 0){
                                    j = fliveMatchDetailsArrayList.size();
                                }
                            }

                        }
                    }
                }
                //For Removing Dublicate Values
                MainActivity.this.scoreAdapter.notifyDataSetChanged();
                if (MainActivity.this.liveMatchviewPager != null) {
                    MainActivity.this.liveMatchviewPager.setCurrentItem(MainActivity.this.moveTo);
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }


        public void onCancelled(DatabaseError databaseError) {
        }
    }


    public void homeArrayLocal(){
        allMatchImages = new ArrayList<>();
        videoList = new ArrayList<>();
        seriesList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(dbHelper.getFieldValueUser("HomeArray"));
            JSONArray jsonImages = jsonObject.getJSONArray("images");
            JSONArray jsonVideos = jsonObject.getJSONArray("videos");
            JSONArray jsonSeries = jsonObject.getJSONArray("series");

            for (int i=0;i<jsonImages.length();i++){
                TourData videoData = new TourData();
                videoData.setId(jsonImages.getJSONObject(i).getString("id"));
                videoData.setLeague_name(jsonImages.getJSONObject(i).getString("img"));
                videoData.setHeading(jsonImages.getJSONObject(i).getString("heading"));
                videoData.setDescription(jsonImages.getJSONObject(i).getString("description"));
                videoData.setStrat_date(jsonImages.getJSONObject(i).getString("date"));
                allMatchImages.add(videoData);
            }

            for (int i=0;i<jsonSeries.length();i++){
                SeriesWise_Model seriesWise_model = new SeriesWise_Model();
                seriesWise_model.tourname = jsonSeries.getJSONObject(i).getString("tourname");
                seriesWise_model.date_time = "hide date";
                seriesWise_model.seriesid = jsonSeries.getJSONObject(i).getString("seriesid");
                seriesList.add(seriesWise_model);
            }


            for (int j=0;j<jsonVideos.length();j++){
                TourData videoData = new TourData();
                videoData.setId(jsonVideos.getJSONObject(j).getString("id"));
                videoData.setLeague_name(jsonVideos.getJSONObject(j).getString("img"));
                videoData.setMonth(jsonVideos.getJSONObject(j).getString("video"));
                videoData.setHeading(jsonVideos.getJSONObject(j).getString("heading"));
                videoList.add(videoData);
            }

            ViewImagePager viewPagerAdapter = new ViewImagePager(MainActivity.this, allMatchImages);
            viewPagerImage.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
            viewPagerImage.setClipToPadding(false);
            viewPagerImage.setPadding(32, 0, 32, 0);
            viewPagerImage.setPageMargin(20);
            imagwSliderDots.setupWithViewPager(viewPagerImage, true);

            videoPagerAdapter = new VideoPagerAdapter(MainActivity.this,videoList);
            videosPager.setAdapter(videoPagerAdapter);
            videoPagerAdapter.notifyDataSetChanged();
            videosPager.setClipToPadding(false);
            videosPager.setPadding(32, 0, 32, 0);
            videosPager.setPageMargin(20);
            videoTabs.setupWithViewPager(videosPager);

            seriesWise_adapter = new SeriesWise_Adapter(MainActivity.this,seriesList,"ALL");
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
            rcvSeries.setLayoutManager(mLayoutManager);
            rcvSeries.setNestedScrollingEnabled(false);
            rcvSeries.setAdapter(seriesWise_adapter);
            seriesWise_adapter.notifyDataSetChanged();

            if(cd.isConnectingToInternet()){
                //new Homearray().execute();
                Homearray();
            }else Toast.makeText(this, "Please chcek your internet connection and Restart the app", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void Homearray(){
        JsonObjectRequest homeJson =  new JsonObjectRequest(Global.Comman_Url+"imagesAndVideo", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    allMatchImages = new ArrayList<>();
                    videoList = new ArrayList<>();
                    seriesList = new ArrayList<>();
                    if(response!=null){
                        dbHelper.insertFielduser("HomeArray",response.toString());

                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray jsonImages = jsonObject.getJSONArray("images");
                        JSONArray jsonVideos = jsonObject.getJSONArray("videos");
                        JSONArray jsonSeries = jsonObject.getJSONArray("series");

                        for (int i=0;i<jsonImages.length();i++){
                            TourData videoData = new TourData();
                            videoData.setId(jsonImages.getJSONObject(i).getString("id"));
                            videoData.setLeague_name(jsonImages.getJSONObject(i).getString("img"));
                            videoData.setHeading(jsonImages.getJSONObject(i).getString("heading"));
                            videoData.setDescription(jsonImages.getJSONObject(i).getString("description"));
                            videoData.setStrat_date(jsonImages.getJSONObject(i).getString("date"));
                            allMatchImages.add(videoData);
                        }


                        for (int j=0;j<jsonVideos.length();j++){
                            TourData videoData = new TourData();
                            videoData.setId(jsonVideos.getJSONObject(j).getString("id"));
                            videoData.setLeague_name(jsonVideos.getJSONObject(j).getString("img"));
                            videoData.setMonth(jsonVideos.getJSONObject(j).getString("video"));
                            videoData.setHeading(jsonVideos.getJSONObject(j).getString("heading"));
                            videoList.add(videoData);
                        }


                        for (int i=0;i<jsonSeries.length();i++){
                            SeriesWise_Model seriesWise_model = new SeriesWise_Model();
                            seriesWise_model.tourname = jsonSeries.getJSONObject(i).getString("tourname");
                            seriesWise_model.date_time = "hide Date";
                            seriesWise_model.seriesid = jsonSeries.getJSONObject(i).getString("seriesid");
                            seriesList.add(seriesWise_model);
                        }

                        ViewImagePager viewPagerAdapter = new ViewImagePager(MainActivity.this, allMatchImages);
                        viewPagerImage.setAdapter(viewPagerAdapter);
                        viewPagerAdapter.notifyDataSetChanged();
                        viewPagerImage.setClipToPadding(false);
                        viewPagerImage.setPadding(32, 0, 32, 0);
                        viewPagerImage.setPageMargin(20);
                        imagwSliderDots.setupWithViewPager(viewPagerImage, true);

                        videoPagerAdapter = new VideoPagerAdapter(MainActivity.this,videoList);
                        videosPager.setAdapter(videoPagerAdapter);
                        videoPagerAdapter.notifyDataSetChanged();
                        videosPager.setClipToPadding(false);
                        videosPager.setPadding(32, 0, 32, 0);
                        videosPager.setPageMargin(20);
                        videoTabs.setupWithViewPager(videosPager);

                        seriesWise_adapter = new SeriesWise_Adapter(MainActivity.this,seriesList,"ALL");
                        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
                        rcvSeries.setLayoutManager(mLayoutManager);
                        rcvSeries.setNestedScrollingEnabled(false);
                        rcvSeries.setAdapter(seriesWise_adapter);
                        seriesWise_adapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        });
        homeJson.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(homeJson);
    }


}