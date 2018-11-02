package com.cricflame.cricflame.betfair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.DayByDay.Daybyday_Match_Fragment;
import com.cricflame.cricflame.DayByDay.Daybyday_Venue_Fragment;
import com.cricflame.cricflame.Fragment.BetfairFragment;
import com.cricflame.cricflame.Fragment.BetfairHorseFragment;
import com.cricflame.cricflame.Fragment.BetfairSoccerFragment;
import com.cricflame.cricflame.Fragment.BetfairTennisFragment;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BetfairMainActivity extends AppCompatActivity {

    private ImageView back;
    public ArrayList<TourData> game_info = new ArrayList<>();
    public String token="";
    LoadingDialog progressDialog;
    private TabLayout tblBetfair;
    private ViewPager vpBetfair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betfair_main);

        progressDialog=new LoadingDialog(BetfairMainActivity.this);
        tblBetfair = (TabLayout) findViewById(R.id.tbl_betfair);
        vpBetfair = (ViewPager) findViewById(R.id.vp_betfair);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //progressDialog.show();
        int p;

        getToken();

        tblBetfair.addTab(tblBetfair.newTab().setText("CRICKET"));
        tblBetfair.addTab(tblBetfair.newTab().setText("FOOTBALL"));
        tblBetfair.addTab(tblBetfair.newTab().setText("TENNIS"));
        tblBetfair.addTab(tblBetfair.newTab().setText("HORSE RACE"));
        tblBetfair.setTabGravity(TabLayout.GRAVITY_FILL);
        BetfairPager adapter = new BetfairPager(getSupportFragmentManager(), tblBetfair.getTabCount());

        vpBetfair.setAdapter(adapter);
        vpBetfair.setOffscreenPageLimit(3);
        vpBetfair.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tblBetfair));
        tblBetfair.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpBetfair.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



//                        switch (position){
//                            case 0:
//                                Intent intent=new Intent(BetfairMainActivity.this, BetfairActivity.class);
//                                intent.putExtra("token",token);
//                                intent.putExtra("gameId","4");
//
//                                startActivity(intent);
//                                break;
//                            case 1:
//                                Intent i=new Intent(BetfairMainActivity.this, BetfairActivity.class);
//                                //i.putExtra("url","http://35.176.69.80/serieslist.php?id=1");
//                                i.putExtra("token",token);
//                                i.putExtra("gameId","1");
//                                startActivity(i);
//                                break;
//                            case 2:
//                                Intent in=new Intent(BetfairMainActivity.this, BetfairActivity.class);
//                                //in.putExtra("url","http://35.176.69.80/serieslist.php?id=2");
//                                in.putExtra("token",token);
//                                in.putExtra("gameId","2");
//                                startActivity(in);
//                                break;
//                            case 3:
//                                Intent i3=new Intent(BetfairMainActivity.this, BetfairActivity.class);
//                                //i3.putExtra("url","http://35.176.69.80/serieslist.php?id=3");
//                                i3.putExtra("token",token);
//                                i3.putExtra("gameId","3");
//                                startActivity(i3);
//                                break;
//
    }

    public String getTokin(){
        return token;
    }

    private void getToken() {
        progressDialog.show();
        String url= Global.BETFAIR_URL_TOKEN+"chkLoginUser/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            token = jsonObject.getString("TokenId");
                            if(token.equalsIgnoreCase("null")){
                                token = "";
                            }
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(BetfairMainActivity.this).add(stringRequest);
    }

//    public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {
//
//        private Context mContext;
//        private ArrayList<TourData> products;
//
//
//        public class MyViewHolder extends RecyclerView.ViewHolder {
//            public TextView title;
//            public ImageView img_view;
//            public RelativeLayout layout_tips_heading;
//
//
//            public MyViewHolder(View view) {
//                super(view);
//                title = (TextView) view.findViewById(R.id.name);
//                img_view = (ImageView) view.findViewById(R.id.view_image);
//                layout_tips_heading = (RelativeLayout) view.findViewById(R.id.layout_tips_heading);
//                layout_tips_heading.getBackground().setAlpha(128);
//
//            }
//        }
//
//
//        public GameAdapter(Context mContext, ArrayList<TourData> products) {
//            this.mContext = mContext;
//            this.products = products;
//        }
//
//        public Object getItem(int location) {
//            return products.get(location);
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.sports_single_layout, parent, false);
//
//
//            return new MyViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, int position) {
//            TourData productItem = products.get(position);
//            holder.title.setText(productItem.getLeague_name());
//            Glide.with(mContext).load(productItem.getImageResource()).into(holder.img_view);
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return products == null ? 0 : products.size();
//        }
//    }

    public class BetfairPager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public BetfairPager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount= tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    BetfairFragment tab1 = new BetfairFragment();
                    return tab1;
                case 1:
                    BetfairSoccerFragment tab2 = new BetfairSoccerFragment();
                    return tab2;
                case 2:
                    BetfairTennisFragment tab3 = new BetfairTennisFragment();
                    return tab3;
                case 3:
                    BetfairHorseFragment tab4 = new BetfairHorseFragment();
                    return tab4;
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
