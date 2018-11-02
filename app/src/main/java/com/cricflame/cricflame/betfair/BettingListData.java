package com.cricflame.cricflame.betfair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BettingListData extends AppCompatActivity {
    RecyclerView recyclerView;
    public ArrayList<TourData> teamArrayList=new ArrayList<>();
    BettingAdapter adapter;
    ImageView back;
    LoadingDialog progressDialog;
    public static String MarketId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_list_data);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        adapter= (BettingAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) adapter.getItem(position);
                        MarketId=product.getId();
                        Intent i = new Intent(BettingListData.this,BetfairMarketPrice.class);
                        i.putExtra("marketId",product.getId());
                        i.putExtra("matchId",getIntent().getExtras().getString("matchId"));
                        i.putExtra("tokenId",getIntent().getExtras().getString("tokenId"));
                        startActivity(i);
                        // startActivity(new Intent(BettingListData.this,BetfairMarketPrice.class));

                        // do it
                    }
                });

        if(new CheckProxy().isProxyDisabled()){
            getBettingData();
        }else {
            Toast.makeText(this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }


    }

    public void getBettingData(){
        teamArrayList.clear();
        progressDialog=new LoadingDialog(BettingListData.this);
        progressDialog.show();


        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("MatchId", getIntent().getExtras().getString("matchId"));
        jsonParams.put("TokenId", getIntent().getExtras().getString("tokenId"));

        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.POST, Global.BETFAIR_URL+"getMarketOfMatch/",  new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("marketfrmApi");
                    for (int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            TourData betfair=new TourData();
                            betfair.setLeague_name(jsonObject.getString("marketName"));
                            betfair.setId(jsonObject.getString("marketId"));
                            teamArrayList.add(betfair);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter = new BettingAdapter(BettingListData.this, teamArrayList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BettingListData.this, 1);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

        };
        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(BettingListData.this).add(jsonArrayRequest);
    }


    public class BettingAdapter extends RecyclerView.Adapter<BettingAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<TourData> products;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, Description,Id;
            public RelativeLayout lltBetfairItem;



            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.name);
                lltBetfairItem =  view.findViewById(R.id.llt_betfair_item);


            }
        }


        public BettingAdapter(Context mContext, ArrayList<TourData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.betfare_allmatch_layout_view, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            TourData productItem = products.get(position);
            holder.title.setText(productItem.getLeague_name());

            if(position%2==0){
                holder.lltBetfairItem.setBackground(BettingListData.this.getResources().getDrawable(R.drawable.series_back));
            }else  holder.lltBetfairItem.setBackground(BettingListData.this.getResources().getDrawable(R.drawable.new_series));

        }


        @Override
        public int getItemCount() {
            return products.size();
        }
    }

}
