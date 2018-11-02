package com.cricflame.cricflame.TipsTricks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public ArrayList<TourData> teamArrayList=new ArrayList<>();
    public ArrayList<TourData> tips_page=new ArrayList<>();
    GameAdapter adapter;
    ImageView back;
    LoadingDialog progressDialog;
    public static String GAME_ID="";
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_game_list);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rc_game_list);
        String[] name = {"Interesting Website", "Tips of Tippers", "Interesting  Apps", "Telegram Links","Android Tricks"};
        int[] image = {R.drawable.web, R.drawable.tips, R.drawable.android_app, R.drawable.telegram,R.drawable.android_trick};
        for (p = 0; p < name.length; p++) {
            TourData td = new TourData();
            td.setLeague_name(name[p]);
            td.setImageResource(image[p]);
            tips_page.add(td);
        }

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        switch (position){
                            case 0:

                                Intent intent=new Intent(GameListActivity.this, WebsiteLinkActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent i=new Intent(GameListActivity.this, TippersLinkActivity.class);
                                startActivity(i);
                                break;
                            case 2:
                                Intent in=new Intent(GameListActivity.this, InterestingAppActivity.class);
                                startActivity(in);
                                break;
                            case 3:
                                Intent i3=new Intent(GameListActivity.this, TelegramLinkActivity.class);
                                startActivity(i3);
                                break;

                            case 4:
                                Intent i4=new Intent(GameListActivity.this, AndroidTrickLinkActivity.class);
                                startActivity(i4);
                                break;
                        }
                    }
                });

        adapter = new GameAdapter(this,tips_page);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



    }


    public void OnClick(){
        recyclerView = (RecyclerView) findViewById(com.cricflame.cricflame.R.id.recycler_view);

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        adapter= (GameAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) adapter.getItem(position);
                        GAME_ID=product.getId();
                        //startActivity(new Intent(GameListActivity.this,VideoPlayerActivity.class));
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(GAME_ID));
                        startActivity(i);
                    }
                });

        if(new CheckProxy().isProxyDisabled()){
            getTimePassData();
        }else {
            Toast.makeText(GameListActivity.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }
    }

    public void getTimePassData(){
        teamArrayList.clear();
        progressDialog=new LoadingDialog(GameListActivity.this);
        progressDialog.show();
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"timepass.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonObject=response.getJSONArray("data");
                    for (int i=0;i<jsonObject.length();i++){
                        JSONObject team=jsonObject.getJSONObject(i);

                        TourData betfair=new TourData();
                        betfair.setLeague_name(team.getString("heading"));
                        betfair.setImage(team.getString("img"));

                        betfair.setId(team.getString("link"));
                        betfair.setDescription(team.getString("desp"));
                        teamArrayList.add(betfair);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new GameAdapter(GameListActivity.this, teamArrayList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GameListActivity.this, 1);
                recyclerView.setLayoutManager(mLayoutManager);

                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        });
        jsonArrayRequest.setShouldCache(false);jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(GameListActivity.this).add(jsonArrayRequest);
    }
    public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<TourData> products;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView img_view;
            public RelativeLayout layout_tips_heading;


            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(com.cricflame.cricflame.R.id.name);
                img_view = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.view_image);
                layout_tips_heading = (RelativeLayout) view.findViewById(com.cricflame.cricflame.R.id.layout_tips_heading);
            }
        }


        public GameAdapter(Context mContext, ArrayList<TourData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }

        @Override
        public GameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tips_single_layout, parent, false);


            return new GameAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final GameAdapter.MyViewHolder holder, int position) {


            TourData productItem = products.get(position);
            holder.title.setText(productItem.getLeague_name());
            Glide.with(mContext).load(productItem.getImageResource()).into(holder.img_view);
        }


        @Override
        public int getItemCount() {
            return products.size();
        }
    }

}
