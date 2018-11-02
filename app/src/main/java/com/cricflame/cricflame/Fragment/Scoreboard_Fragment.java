package com.cricflame.cricflame.Fragment;

import android.content.Intent;
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
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.ScoreboardActivity;
import com.cricflame.cricflame.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.cricflame.cricflame.MainActivity.MatchId;


/**
 * Created by Deepak Sharma on 07/10/2017.
 */

public class Scoreboard_Fragment extends Fragment {
    TextView team1,team2;


    public static String teamOne,teamTwo,team_score;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scoreboard_layout, container, false);
        team1= (TextView) view.findViewById(R.id.team1);
        team2= (TextView) view.findViewById(R.id.team2);

        getTeam1ScoreBoard();

        team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_score="1";
                startActivity(new Intent(getActivity(), ScoreboardActivity.class));

                //getTeam1ScoreBoard();
            }
        });
        team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_score="2";
                startActivity(new Intent(getActivity(), ScoreboardActivity.class));            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTeam1ScoreBoard();
    }

    public void getTeam1ScoreBoard(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"livematchscore.php?id="+ MatchId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String team1_name,team2_name;
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
                        teamOne = "1st Inning";
                        teamTwo = "2nd Inning";

                    }
                    else{
                        team1.setText(team1_name+ " "+team1score.getString("total"));
                        team2.setText(team2_name+ " "+team2score.getString("total"));
                        teamOne = team1_name;
                        teamTwo = team2_name;

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
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }



}