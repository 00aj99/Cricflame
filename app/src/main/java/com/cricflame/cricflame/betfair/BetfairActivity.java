package com.cricflame.cricflame.betfair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BetfairActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public ArrayList<TourData> teamArrayList=new ArrayList<>();
    BetfairAdapter adapter;
    ImageView back,no_data;
    LoadingDialog progressDialog;
    public static String SeriesId="";
    public static String SportId="";
    public static String TokenId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betfair);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        no_data=(ImageView) findViewById(R.id.no_data);
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
                        adapter= (BetfairAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) adapter.getItem(position);
                        SeriesId=product.getId();
                        SportId = getIntent().getStringExtra("gameId");
                        TokenId = getIntent().getStringExtra("token");
                        startActivity(new Intent(BetfairActivity.this,BetfairAllMatch.class));
                    }
                });

        
        if(new CheckProxy().isProxyDisabled()){
            getBetfairData();
        }else {
            Toast.makeText(this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }

    }

    public void getBetfairData(){

       // progressDialog


        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("sprtId", getIntent().getStringExtra("gameId"));
        jsonParams.put("TokenId", getIntent().getStringExtra("token"));


        teamArrayList.clear();

        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.POST, Global.BETFAIR_URL+"getSeriesOfSport/",  new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e( "onResponse: ",response.toString() );
                if(response.length()==0){
                    no_data.setImageResource(R.drawable.betfarenodata);
                }

                JSONArray jsonArray = null;
                try {
                     jsonArray = response.getJSONArray("seriesfrmApi");
                    for (int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            JSONObject team=jsonObject.getJSONObject("competition");
                            TourData betfair=new TourData();
                            betfair.setLeague_name(team.getString("name"));
                            betfair.setId(team.getString("id"));
                            teamArrayList.add(betfair);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter = new BetfairAdapter(BetfairActivity.this, teamArrayList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BetfairActivity.this, 1);
                recyclerView.setLayoutManager(mLayoutManager);


                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Glide.with(BetfairActivity.this).load(R.drawable.betfarenodata).into(no_data);
                Toast.makeText(BetfairActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
                    };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(BetfairActivity.this).add(jsonArrayRequest);
    }

    public class BetfairAdapter extends RecyclerView.Adapter<BetfairAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<TourData> products;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, Description,Id;



            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.name);


            }
        }


        public BetfairAdapter(Context mContext, ArrayList<TourData> products) {
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



            // loading album cover using Glide library
//            try {
//                java.net.URL url = new URL(productItem.getImage());
//                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                holder.thumbnail.setImageBitmap(bmp);
//               // imageloadurl=String.valueOf(json_data.getString("largeimage"));
//              //  imageshow.setTag(imageloadurl);
//            } catch (Exception e) {
//                System.out.println("Loading issue");
//            }


        }


        @Override
        public int getItemCount() {
            return products.size();
        }
    }

}
