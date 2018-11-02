package com.cricflame.cricflame.Standing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Standing.Adapter.DetailsAdapter;
import com.cricflame.cricflame.Standing.Model.Standing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StandingFragment extends Fragment {
    private String key2;
    RecyclerView rv_standing;
    private ArrayList<Standing> details_list = new ArrayList<Standing>();


    public StandingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standing, container, false);
        key2 = getArguments().getString("key");
        rv_standing = (RecyclerView) view.findViewById(R.id.rv_standing);
        getSandingDetails();
        return view;
    }





    private void getSandingDetails() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, StandingActivity.url, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject pointsTable = response.getJSONObject("group");
                            Iterator<?> keys = pointsTable.keys();
                            JSONArray jsonArray = null;
                            while(keys.hasNext() ) {
                                String key1 = (String)keys.next();

                                if(key2.equals(key1)){
                                    jsonArray = pointsTable.getJSONArray(key1);
                                    details_list.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Standing standing = new Standing();
                                        standing.setId(jsonObject.getString("teamId"));
                                        standing.setName(jsonObject.getString("teamName"));
                                        standing.setPlayed(jsonObject.getString("played"));
                                        standing.setWon(jsonObject.getString("won"));
                                        standing.setLost(jsonObject.getString("lost"));
                                        standing.setNoResult(jsonObject.getString("noresults"));
                                        standing.setTie(jsonObject.getString("tie"));
                                        standing.setPointScore(jsonObject.getString("pointsscored"));
                                        standing.setRunRate(jsonObject.getString("runrate"));
                                        standing.setDraw(jsonObject.getString("draw"));
                                        standing.setQuotiient(jsonObject.getString("quotient"));
                                        standing.setForValue(jsonObject.getString("forValue"));
                                        standing.setAgainstValue(jsonObject.getString("againstValue"));
                                        details_list.add(standing);
                                    }


                                    DetailsAdapter adapter = new DetailsAdapter(getActivity(),details_list);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    rv_standing.setLayoutManager(mLayoutManager);
                                    rv_standing.setItemAnimator(new DefaultItemAnimator());
                                    rv_standing.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

}
