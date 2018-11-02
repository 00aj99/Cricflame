package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Adapter.Tour_Adapter;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.TourDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Global.Tour_Id;
import static com.cricflame.cricflame.Global.Tour_name;

/**
 * Created by Deepak Sharma on 06/10/2017.
 */

public class Domestic_Match_Fragment extends Fragment {
    public  ArrayList<TourData> tourData=new ArrayList<TourData>();
    RecyclerView tour_list;
    Tour_Adapter tour_adapter;
    ImageView no_data;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.international_match_layout,container,false);
        tour_list= (RecyclerView) view.findViewById(R.id.tour_list);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        if(new CheckProxy().isProxyDisabled()){
            getAllInternationalTours();
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();

        }


        ItemClickSupport.addTo(tour_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        tour_adapter= (Tour_Adapter) recyclerView.getAdapter();
                        TourData tourData= (TourData) tour_adapter.getItem(position);
                        Tour_Id=tourData.getId();
                        Tour_name = tourData.getLeague_name();
                        startActivity(new Intent(getActivity(), TourDetailsActivity.class));

                        // do it
                    }
                });
        return view;
    }


    public void getAllInternationalTours(){
        tourData.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"dom_league.php?status=series", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");
                    JSONArray months=response.getJSONArray("data");
                    if (months.length()<=0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    }
                    else {
                        for (int i = 0; i < months.length(); i++) {
                            JSONObject jsonObject = months.getJSONObject(i);
                            JSONArray jsonArray = jsonObject.getJSONArray("month");
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                TourData tourDatas = new TourData();
                                tourDatas.setId(jsonObject1.getString("id"));
                                tourDatas.setLeague_name(jsonObject1.getString("league_name"));
                                tourDatas.setMonth(jsonObject1.getString("month_name"));
                                tourDatas.setMonthId(String.valueOf(j));
                                tourDatas.setStrat_date(jsonObject1.getString("start_date"));
                                tourDatas.setEnd_date(jsonObject1.getString("end_date"));
                                tourData.add(tourDatas);
                            }
                            tour_adapter=new Tour_Adapter(getActivity(),tourData);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                            tour_list.setLayoutManager(mLayoutManager);

                            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                            tour_list.setItemAnimator(new DefaultItemAnimator());

                            tour_list.setAdapter(tour_adapter);

                            tour_adapter.notifyDataSetChanged();
                            tour_list.setAdapter(tour_adapter);

                        }
                    }
                    // months.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setShouldCache(false);jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }
}

