package com.cricflame.cricflame.betfair;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Model.MarketData;
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

public class BetfairMarketPrice extends AppCompatActivity {
    RecyclerView recyclerView,recyclerView2;
    public ArrayList<MarketData> teamArrayList=new ArrayList<>();
    public ArrayList<MarketData> teamArrayList1=new ArrayList<>();
    MarketPriceAdapter adapter;
    MarketPriceSelectionIdAdapter adapter1;
    ImageView back;
   // LoadingDialog progressDialog;
    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betfair_market_price);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(new CheckProxy().isProxyDisabled()){
            getBettingData();
            //getRecords();
            getSelectionName();
           // Toast.makeText(this, BettingListData.MarketId, Toast.LENGTH_SHORT).show();
            this.mHandler = new Handler();
        }else {
            Toast.makeText(this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }






    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mHandler.removeCallbacks(m_Runnable);
      //  this.mHandler.removeMessages(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mHandler.removeCallbacks(m_Runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mHandler.postDelayed(m_Runnable,3000);
    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            getBettingData();
            BetfairMarketPrice.this.mHandler.postDelayed(m_Runnable, 3000);
        }

    };


    public void getBettingData(){
        teamArrayList.clear();

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("MatchId", getIntent().getExtras().getString("matchId"));
        jsonParams.put("TokenId", getIntent().getExtras().getString("tokenId"));
        jsonParams.put("MarketId", getIntent().getExtras().getString("marketId"));

        //JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.POST, Global.BETFAIR_URL_GETLAYS+"getBackLaysOfMarket/",  new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
        StringRequest jsonArrayRequest=new StringRequest(Request.Method.POST, Global.WEB_URL+"/bfodds.php?marketid="+getIntent().getExtras().getString("marketId"),  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.e("market price",""+response.toString());
               // for (int i=0;i<response.length();i++){

                try {
                   // JSONObject jsonObject = response.getJSONObject("MarketRunner");
                    try {

                        //JSONArray runners=jsonObject.getJSONArray("runners");
                        JSONArray res = new JSONArray(response);

                        JSONArray runners=res.getJSONObject(0).getJSONArray("runners");





                        for (int i=0;i<runners.length();i++){
                            JSONObject runnerData=runners.getJSONObject(i);
                            MarketData marketData=new MarketData();

                            // result+=cursor.getString(0)+" "+cursor.getString(1)+"\n";



                            //  marketData.setId(cursor.getString(0));


                            JSONObject runnerView=runnerData.getJSONObject("ex");
                            JSONArray DataArray1=runnerView.getJSONArray("availableToBack");
                            JSONArray DataArray2=runnerView.getJSONArray("availableToLay");
                            JSONArray DataArray3=runnerView.getJSONArray("tradedVolume");

                            //   for (int k=0;j<DataArray1.length();k++){
                            if (DataArray1.length()>0) {
                                JSONObject backData1 = DataArray1.getJSONObject(0);

                                marketData.setBack1(backData1.getString("price"));
                                marketData.setBack2(backData1.getString("size"));
                            }
                            //  teamArrayList.add(marketData);

                            //   }
                            //   for (int l=0;j<DataArray2.length();l++){
                            if (DataArray2.length()>0) {

                                JSONObject backData2 = DataArray2.getJSONObject(0);

                                marketData.setCurrent1(backData2.getString("price"));
                                marketData.setCurrent2(backData2.getString("size"));
                            }
                            //  teamArrayList.add(marketData);

                            //  }
                            //  for (int m=0;j<DataArray3.length();m++){
                            if (DataArray3.length()>0) {

                                JSONObject backData3 = DataArray3.getJSONObject(0);

                                marketData.setNext1(backData3.getString("price"));
                                marketData.setNext2(backData3.getString("size"));
                            }
                            //teamArrayList.add(marketData);

                            //  }
                            teamArrayList.add(marketData);


                        }
                        //  teamArrayList.remove(cursor.getPosition());
                        //  recyclerView.removeViewAt(cursor.getPosition());
                        //  adapter.notifyItemRemoved(cursor.getPosition());
                        //  adapter.notifyItemRangeChanged(cursor.getPosition(), teamArrayList.size());

                        adapter = new MarketPriceAdapter(BetfairMarketPrice.this, teamArrayList);
                        adapter.notifyDataSetChanged();
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BetfairMarketPrice.this, 1);
                        recyclerView2.setLayoutManager(mLayoutManager);
                        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                        recyclerView2.setItemAnimator(new DefaultItemAnimator());
                        recyclerView2.setAdapter(adapter);
                        recyclerView2.invalidate();

                        // progressDialog.dismiss();




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


              //  }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  progressDialog.dismiss();

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
        Volley.newRequestQueue(BetfairMarketPrice.this).add(jsonArrayRequest);
    }

    public void getSelectionName(){
        teamArrayList1.clear();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("TokenId", getIntent().getExtras().getString("tokenId"));
        jsonParams.put("MarketId", getIntent().getExtras().getString("marketId"));
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.POST, Global.BETFAIR_URL+"getBackLaysOfMarketSelectionName/",  new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("runnerSlName");
                    try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        JSONArray runners=jsonObject1.getJSONArray("runners");
                        for (int i=0;i<runners.length();i++){
                            JSONObject runnerData=runners.getJSONObject(i);
                            MarketData marketData=new MarketData();
                            marketData.setSelectId(runnerData.getString("selectionId"));
                            marketData.setRunnerName(runnerData.getString("runnerName"));
                            teamArrayList1.add(marketData);


                        }
                        adapter1 = new MarketPriceSelectionIdAdapter(BetfairMarketPrice.this, teamArrayList1);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BetfairMarketPrice.this, 1);
                        recyclerView.setLayoutManager(mLayoutManager);
                        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter1);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //  }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e( "onErrorResponse: ",error.getMessage() );
                Toast.makeText(BetfairMarketPrice.this, "fuccing enter errr"+error.getMessage(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(BetfairMarketPrice.this).add(jsonArrayRequest);
    }





    public class MarketPriceAdapter extends RecyclerView.Adapter<MarketPriceAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<MarketData> products;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView backPrice1,Id,backPrice2,currentPrice1,currentPrice2,nextPrice1,nextPrice2;



            public MyViewHolder(View view) {
                super(view);

                backPrice1 = (TextView) view.findViewById(R.id.backPrice1);
                backPrice2 = (TextView) view.findViewById(R.id.backPrice2);
                currentPrice1 = (TextView) view.findViewById(R.id.currentPrice1);
                currentPrice2 = (TextView) view.findViewById(R.id.currentPrice2);
                nextPrice1 = (TextView) view.findViewById(R.id.nextPrice1);
                nextPrice2 = (TextView) view.findViewById(R.id.nextPrice2);


            }
        }


        public MarketPriceAdapter(Context mContext, ArrayList<MarketData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.market_price_view, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            //swap(products);
            holder.backPrice1.setText(products.get(position).getBack1());
            holder.backPrice2.setText(products.get(position).getBack2());
            holder.currentPrice1.setText(products.get(position).getCurrent1());
            holder.currentPrice2.setText(products.get(position).getCurrent2());
            holder.nextPrice1.setText(products.get(position).getNext1());
            holder.nextPrice2.setText(products.get(position).getNext2());



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
        public void swap(ArrayList<MarketData> datas)
        {
            if(datas == null || datas.size()==0)
                return;
            if (products != null && products.size()>0)
                products.clear();
            products.addAll(datas);
            notifyDataSetChanged();

        }


        @Override
        public int getItemCount() {
            return products.size();
        }
    }
    public class MarketPriceSelectionIdAdapter extends RecyclerView.Adapter<MarketPriceSelectionIdAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<MarketData> products;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, Id;



            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.selectionId);


            }
        }


        public MarketPriceSelectionIdAdapter(Context mContext, ArrayList<MarketData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.market_price_selection_id, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            //swap(products);

            holder.title.setText(products.get(position).getRunnerName());

        }
        public void swap(ArrayList<MarketData> datas)
        {
            if(datas == null || datas.size()==0)
                return;
            if (products != null && products.size()>0)
                products.clear();
            products.addAll(datas);
            notifyDataSetChanged();

        }


        @Override
        public int getItemCount() {
            return products.size();
        }
    }

}
