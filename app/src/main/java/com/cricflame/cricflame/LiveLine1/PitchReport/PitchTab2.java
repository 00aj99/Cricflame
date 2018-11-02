package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Deepak Sharma on 17/11/2017.
 */

public class PitchTab2 extends Fragment {

    private TextView tv_weather, tv_temprature_degree, tv_temprature_fahren, tv_avg_team_score1, tv_avg_team_score2, tv_batwin1, tv_batwin2, tv_highest_score, tv_highest_score_chase, tv_lowest_score, tv_lowest_score_defend,txtHeader;
    private DatabaseReference mRootref;
    private DatabaseReference childref;
    private DatabaseReference temp_fer,Weather,SBW,ASIS,AFIS,FBW,HS,LS,HSC,LSD,Name;
    private DatabaseReference temp_cel;
    String Key;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Key = getActivity().getIntent().getExtras().getString("key");


    }

    private void assignValue() {
        Name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtHeader.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        temp_cel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_temprature_degree.setText(dataSnapshot.getValue(String.class)+(char) 0x00B0+"C ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        temp_fer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_temprature_fahren.setText(dataSnapshot.getValue(String.class)+(char) 0x00B0+"F");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Weather.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_weather.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SBW.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_batwin2.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ASIS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_avg_team_score2.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AFIS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_avg_team_score1.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FBW.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_batwin1.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        HSC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_highest_score_chase.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        HS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_highest_score.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        LS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_lowest_score.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        LSD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_lowest_score_defend.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pitch_tab_layout2, container, false);

        try{
            mRootref= FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();


        childref = mRootref.child("Pitch_Report").child(Key);
        temp_cel = childref.child("temp_cel");
        temp_fer = childref.child("temp_far");
        Weather = childref.child("Weather");
        SBW = childref.child("SBW");
        ASIS = childref.child("ASIS");
        AFIS = childref.child("AFIS");
        FBW = childref.child("FBW");
        HS = childref.child("HS");
        LS = childref.child("LS");
        HSC = childref.child("HSC");
        LSD = childref.child("LSD");
        Name = childref.child("pitch");
        init(view);


        //getAllPitchReport();
        assignValue();

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        tv_weather = (TextView) view.findViewById(R.id.tv_weather);
        tv_temprature_degree = (TextView) view.findViewById(R.id.tv_temprature_degree);
        tv_temprature_fahren = (TextView) view.findViewById(R.id.tv_temprature_fahren);
        tv_avg_team_score1 = (TextView) view.findViewById(R.id.tv_avg_team_score1);
        tv_avg_team_score2 = (TextView) view.findViewById(R.id.tv_avg_team_score2);
        tv_batwin1 = (TextView) view.findViewById(R.id.tv_batwin1);
        tv_batwin2 = (TextView) view.findViewById(R.id.tv_batwin2);
        tv_highest_score = (TextView) view.findViewById(R.id.tv_highest_score);
        tv_highest_score_chase = (TextView) view.findViewById(R.id.tv_highest_score_chase);
        tv_lowest_score = (TextView) view.findViewById(R.id.tv_lowest_score);
        tv_lowest_score_defend = (TextView) view.findViewById(R.id.tv_lowest_score_defend);
        txtHeader = (TextView) view.findViewById(R.id.txt_pitch_report_header);
    }


    public void getAllPitchReport() {

        JsonArrayRequest productRequest = new JsonArrayRequest(Request.Method.GET, Global.URL + "get_pitch_report.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject stats = jsonArray.getJSONObject(i);
                        tv_weather.setText(stats.getString("weather"));
                        tv_temprature_degree.setText(stats.getString("temp_cel")+(char) 0x00B0+"C ");
                        tv_temprature_fahren.setText(stats.getString("temp_far")+(char) 0x00B0+"F");
                        tv_avg_team_score1.setText(stats.getString("ave_first"));
                        tv_avg_team_score2.setText(stats.getString("ave_sec"));
                        tv_batwin1.setText(stats.getString("first_bat"));
                        tv_batwin2.setText(stats.getString("sec_bat"));
                        tv_highest_score.setText(stats.getString("high_score"));
                        tv_highest_score_chase.setText(stats.getString("high_chase"));
                        tv_lowest_score.setText(stats.getString("low_score"));
                        tv_lowest_score_defend.setText(stats.getString("low_def"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        productRequest.setShouldCache(false);productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(productRequest);

    }


}
