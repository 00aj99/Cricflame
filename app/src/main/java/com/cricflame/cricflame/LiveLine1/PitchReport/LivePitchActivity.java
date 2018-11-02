package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class LivePitchActivity extends AppCompatActivity {
    TextView match,series,date,time,venue,umpires,third_umpire,referee,stadium,city,host,capacity;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_pitch);

        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        init();
        getMatchInfo();
    }

    private void init() {
        match= (TextView) findViewById(R.id.match);
        series= (TextView)findViewById(R.id.series);
        date= (TextView) findViewById(R.id.date);
        time= (TextView) findViewById(R.id.time);
        venue= (TextView) findViewById(R.id.venue);
        umpires= (TextView) findViewById(R.id.umpire);
        third_umpire= (TextView) findViewById(R.id.third_umpire);
        referee= (TextView) findViewById(R.id.referee);
        stadium= (TextView) findViewById(R.id.stadium);
        city= (TextView) findViewById(R.id.city);
        host= (TextView) findViewById(R.id.host);
        capacity= (TextView)findViewById(R.id.capacity);
    }

    public void getMatchInfo(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL+getIntent().getStringExtra("filename"), null, new Response.Listener<JSONObject>() {
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
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
}
