package com.cricflame.cricflame.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.VenueData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Fragment.VenueFragment.VenueId;
import static com.cricflame.cricflame.Global.URL;

/**
 * Created by Deepak Sharma on 06/10/2017.
 */

public class Venue_Details_Fragment extends AppCompatActivity {
    public  ArrayList<VenueData> tourData=new ArrayList<VenueData>();
    TextView opened,capacity,known,ends,location,home;
    ImageView groundImage,back;
    ArrayList<String> endsArray=new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_detail_layout);
        opened= (TextView) findViewById(R.id.opened);
        capacity= (TextView) findViewById(R.id.capacity);
        known= (TextView) findViewById(R.id.known);
        ends= (TextView) findViewById(R.id.ends);
        location= (TextView) findViewById(R.id.location);
        home= (TextView) findViewById(R.id.home);
        groundImage= (ImageView) findViewById(R.id.ground_image);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getVenueDetails();
    }

    public void getVenueDetails(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL+"full_venue.php?id="+VenueId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");
                    JSONArray jsonArray=response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        opened.setText(jsonObject.getString("opened"));
                        Glide.with(Venue_Details_Fragment.this).load(Global.IMG_VENUE_URL+jsonObject.getString("img")).into(groundImage);
                        capacity.setText(jsonObject.getString("capacity"));
                        known.setText(jsonObject.getString("known_as"));
                        ends.setText(jsonObject.getString("ends1"));
                        location.setText(jsonObject.getString("location"));
                        home.setText(jsonObject.getString("home to"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(Venue_Details_Fragment.this).add(jsonObjectRequest);

    }

}
