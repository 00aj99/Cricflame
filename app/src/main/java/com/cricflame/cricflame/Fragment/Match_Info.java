package com.cricflame.cricflame.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.R;
import org.json.JSONException;
import org.json.JSONObject;


import static com.cricflame.cricflame.Global.URL;
import static com.cricflame.cricflame.MainActivity.MatchId;

/**
 * Created by Deepak Sharma on 07/10/2017.
 */

public class Match_Info extends Fragment {
    TextView match,series,date,time,venue,umpires,third_umpire,referee,stadium,city,host,capacity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.match_info_layout,container,false);
        match= (TextView) view.findViewById(R.id.match);
        series= (TextView) view.findViewById(R.id.series);
        date= (TextView) view.findViewById(R.id.date);
        time= (TextView) view.findViewById(R.id.time);
        venue= (TextView) view.findViewById(R.id.venue);
        umpires= (TextView) view.findViewById(R.id.umpire);
        third_umpire= (TextView) view.findViewById(R.id.third_umpire);
        referee= (TextView) view.findViewById(R.id.referee);
        stadium= (TextView) view.findViewById(R.id.stadium);
        city= (TextView) view.findViewById(R.id.city);
        host= (TextView) view.findViewById(R.id.host);
        capacity= (TextView) view.findViewById(R.id.capacity);
        getMatchInfo();
        return view;
    }

    public void getMatchInfo(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL+"livematchinfo.php?id="+MatchId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("response",""+response.toString());
                    //JSONObject data=response.getJSONObject("data");
                    JSONObject jsonArray=response.getJSONObject("data");
                 //   for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject("info");
                        match.setText(jsonObject.getString("Match"));

                        series.setText(jsonObject.getString("Series"));
                        date.setText(jsonObject.getString("Date"));
                        time.setText(jsonObject.getString("Time"));
                        venue.setText(jsonObject.getString("Venue"));
                        umpires.setText(jsonObject.getString("Umpires"));
                        third_umpire.setText(jsonObject.getString("3rd Umpire"));
                        referee.setText(jsonObject.getString("Referee"));
                    JSONObject venueDetail=jsonArray.getJSONObject("Venue Guide");
                        stadium.setText(venueDetail.getString("Stadium"));
                        city.setText(venueDetail.getString("City"));
                        host.setText(venueDetail.getString("Hosts to"));
                    capacity.setText(venueDetail.getString("Capacity"));

                       // Glide.with(Venue_Details_Fragment.this).load(Global.IMG_VENUE_URL+jsonObject.getString("img")).into(groundImage);
                 //   }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",""+error.toString());

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

}
