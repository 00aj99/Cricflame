package com.cricflame.cricflame;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;




public class LiveLineScoreboard extends AppCompatActivity {
    ImageView back;
    TextView team1,team2;
    public static String teamOne_name,teamTwo_name,team_score_live;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_live_line_scoreboard);

        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        team1= (TextView) findViewById(com.cricflame.cricflame.R.id.team1);
        team2= (TextView) findViewById(com.cricflame.cricflame.R.id.team2);

        getTeam1ScoreBoard();

        team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_score_live="1";
                startActivity(new Intent(LiveLineScoreboard.this, LiveLineDetailScore.class));

            }
        });
        team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_score_live="2";
                startActivity(new Intent(LiveLineScoreboard.this, LiveLineDetailScore.class));            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getTeam1ScoreBoard();
    }

    public void getTeam1ScoreBoard(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+getIntent().getStringExtra("filename"), null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("response",""+response.toString());
                    String team1_name="",team2_name="";
                    JSONObject teams=response.getJSONObject("data");
                    JSONObject scorecard=teams.getJSONObject("scorecard");
                    JSONObject team1score=scorecard.getJSONObject("team1_f");
                    JSONObject team2score=scorecard.getJSONObject("team2_f");
                    if (team1score.getString("team_name").equals("")){
                        team1_name=teams.getString("team1");
                    }
                    else{
                        team1_name=  team1score.getString("team_name");
                    }
                    if (team2score.getString("team_name").equals("")){
                        team2_name=teams.getString("team2");
                    }
                    else{
                        team2_name=  team2score.getString("team_name");
                    }


                    if (teams.getString("match_type").equals("TEST")){
                        JSONObject team1_sscore=scorecard.getJSONObject("team1_s");
                        JSONObject team2_sscore=scorecard.getJSONObject("team2_s");
                        if (team1score.getString("team_name").equals("")){
                            team1_name=teams.getString("team1");
                        }
                        else{
                            team1_name=  team1score.getString("team_name");
                        }
                        if (team2score.getString("team_name").equals("")){
                            team2_name=teams.getString("team2");
                        }
                        else{
                            team2_name=  team2score.getString("team_name");
                        }

                        if (team1_sscore.getString("total").equals("")){
                            team1.setText(team1_name+ " "+team1score.getString("total")+ " "+team1_sscore.getString("total"));

                        }
                        else{
                            team1.setText(team1_name+ " "+team1score.getString("total")+ " & "+team1_sscore.getString("total"));
                        }
                        if (team2_sscore.getString("total").equals("")){
                            team2.setText(team2_name+ " "+team2score.getString("total")+ " "+team2_sscore.getString("total"));

                        }
                        else{
                            team2.setText(team2_name+ " "+team2score.getString("total")+ " & "+team2_sscore.getString("total"));

                        }
                        teamOne_name = "1st Inning";
                        teamTwo_name = "2nd Inning";

                    }
                    else{
                        team1.setText(team1_name+ " "+team1score.getString("total"));
                        team2.setText(team2_name+ " "+team2score.getString("total"));
                        teamOne_name = teams.getString("team1");
                        teamTwo_name = teams.getString("team2");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error.toString());
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(LiveLineScoreboard.this).add(jsonObjectRequest);

    }


}
