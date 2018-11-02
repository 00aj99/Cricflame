package com.cricflame.cricflame.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.betfair.BetfairAllMatch;
import com.cricflame.cricflame.betfair.BetfairMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetfairTennisFragment extends Fragment {

    RecyclerView recyclerView;
    public ArrayList<TourData> teamArrayList=new ArrayList<>();
    BetfairAdapter adapter;
    ImageView back,no_data;
    LoadingDialog progressDialog;
    public static String SeriesId="";
    public static String SportId="2";
    public static String TokenId="";
    boolean visibleToUser;
    View view = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_betfair, container, false);
//        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        no_data=(ImageView) view.findViewById(R.id.no_data);
        back= (ImageView) view.findViewById(R.id.back);

        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        BetfairMainActivity activity = (BetfairMainActivity) getActivity();
                        TokenId = activity.getTokin();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

            if(new CheckProxy().isProxyDisabled()){
                getBetfairData();
            }else {
                        try{
                            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

            }

                }
            },1500);


            ItemClickSupport.addTo(recyclerView)
                    .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            recyclerView.setAdapter(adapter);
                            TourData product= (TourData) adapter.getItem(position);
                            SeriesId=product.getId();
                            Intent i = new Intent(getActivity(), BetfairAllMatch.class);
                            i.putExtra("seriesId",product.getId());
                            i.putExtra("tokenId",TokenId);
                            i.putExtra("sportId",SportId);
                            startActivity(i);
                            //startActivity(new Intent(getActivity(),BetfairAllMatch.class));
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void onResume() {
        super.onResume();
    }

    public void onStart() {
        super.onStart();
        if (this.visibleToUser) {
            onVisible();
        }
    }

    public void onStop() {
        super.onStop();
        if (this.visibleToUser) {
            onInvisible();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.visibleToUser = isVisibleToUser;
        if (!isResumed()) {
            return;
        }
        if (this.visibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    private void onInvisible() {
    }

    public void onVisible() {
        if(teamArrayList==null || teamArrayList.size()==0) {
            init(view);
        }
    }

    public void getBetfairData(){

        // progressDialog
        try{
            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("sprtId", SportId);
            jsonParams.put("TokenId", TokenId);

            progressDialog = new LoadingDialog(getActivity());
            progressDialog.show();

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


                    adapter = new BetfairAdapter(getActivity(), teamArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(mLayoutManager);


                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{
                        Glide.with(getActivity()).load(R.drawable.betfarenodata).into(no_data);
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


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
            Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public class BetfairAdapter extends RecyclerView.Adapter<BetfairAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<TourData> products;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, Description,Id;
            public RelativeLayout lltBetfairItem;
            public ImageView img;


            public MyViewHolder(View view) {
                super(view);
                lltBetfairItem =  view.findViewById(R.id.llt_betfair_item);
                title = (TextView) view.findViewById(R.id.name);
                img = (ImageView) view.findViewById(R.id.item_info);

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
        public BetfairAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.betfare_allmatch_layout_view, parent, false);


            return new BetfairAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final BetfairAdapter.MyViewHolder holder, int position) {
            TourData productItem = products.get(position);

            holder.title.setText(productItem.getLeague_name());

            if(position%2==0){
                holder.lltBetfairItem.setBackground(getActivity().getResources().getDrawable(R.drawable.series_back));
            }else  holder.lltBetfairItem.setBackground(getActivity().getResources().getDrawable(R.drawable.new_series));

            try{
                holder.img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.tennis));
            }catch (Exception e){
                e.printStackTrace();
            }
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
