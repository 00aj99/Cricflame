package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.BrowsePlayer;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.PlayerDetail;
import com.cricflame.cricflame.Adapter.Squad_adapter;
import com.cricflame.cricflame.Model.SquadsData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Fragment.All_Matches_Fragment.BMatchId;


/**
 * Created by Deepak Sharma on 07/10/2017.
 */

public class Browse_Squad_Info extends Fragment {
    TextView Team1,Team2;
    ListView team1_list,team2_list;
    public  ArrayList<SquadsData> squadArrayList1=new ArrayList<>();
    public  ArrayList<SquadsData> squadArrayList2=new ArrayList<>();
    Squad_adapter squad_adapter;
    ImageView no_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.squads_layout,container,false);
        team1_list= (ListView) view.findViewById(R.id.team1_list);
        team2_list= (ListView) view.findViewById(R.id.team2_list);
        Team1= (TextView) view.findViewById(R.id.team1);
        no_data= (ImageView) view.findViewById(R.id.no_data);

        Team2= (TextView) view.findViewById(R.id.team2);

        team1_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                squad_adapter= (Squad_adapter) parent.getAdapter();
                SquadsData squads= (SquadsData) squad_adapter.getItem(position);
                BrowsePlayer.Player_Id=squads.getId();
                startActivity(new Intent(getActivity(), PlayerDetail.class));
            }
        });
        team2_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                squad_adapter= (Squad_adapter) parent.getAdapter();
                SquadsData squads= (SquadsData) squad_adapter.getItem(position);
                BrowsePlayer.Player_Id=squads.getId();
                startActivity(new Intent(getActivity(), PlayerDetail.class));
            }
        });

        team2_list.setVisibility(View.GONE);
        getTeamSquads();
        Team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team1_list.setVisibility(View.VISIBLE);
                Team1.setTextColor(getResources().getColor(R.color.white));
                Team2.setTextColor(getResources().getColor(R.color.black));

                team2_list.setVisibility(View.GONE);

            }
        });
        Team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team2_list.setVisibility(View.VISIBLE);
                team1_list.setVisibility(View.GONE);
                Team1.setTextColor(getResources().getColor(R.color.black));
                Team2.setTextColor(getResources().getColor(R.color.white));

            }
        });
        return view;
    }

    public void getTeamSquads(){
        squadArrayList1.clear();
        squadArrayList2.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"match_info.php?id="+ BMatchId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");
                    Log.d("response",""+response.toString());
                    JSONObject teams=response.getJSONObject("data");
                    JSONObject team1squads=teams.getJSONObject("team1Squads");
                    JSONObject team2squads=teams.getJSONObject("team2Squads");
                    JSONArray team1Array=team1squads.getJSONArray("teams");
                    JSONArray team2Array=team2squads.getJSONArray("teams");
                    if (team1Array.length()<=0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    }
                    else {
                        for (int j = 0; j < team1Array.length(); j++) {
                            JSONObject jsonObject1 = team1Array.getJSONObject(j);
                            SquadsData squadsData = new SquadsData();
                            squadsData.setId(jsonObject1.getString("id"));
                            squadsData.setPlayer_image(jsonObject1.getString("player_img"));
                            squadsData.setPlayer_type(jsonObject1.getString("player_role"));
                            squadsData.setPlayer_name(jsonObject1.getString("player_name"));
                            Team1.setText(jsonObject1.getString("team_name"));
                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            squadArrayList1.add(squadsData);
                        }
                        for (int i = 0; i < team2Array.length(); i++) {
                            JSONObject jsonObject1 = team2Array.getJSONObject(i);
                            SquadsData squadsData = new SquadsData();
                            squadsData.setId(jsonObject1.getString("id"));
                            squadsData.setPlayer_image(jsonObject1.getString("player_img"));
                            squadsData.setPlayer_type(jsonObject1.getString("player_role"));
                            squadsData.setPlayer_name(jsonObject1.getString("player_name"));
                            Team2.setText(jsonObject1.getString("team_name"));
                            // squadsData.setPlayer_image(jsonObject1.getString("id"));

                            squadArrayList2.add(squadsData);
                        }

                        squad_adapter = new Squad_adapter(getActivity(), squadArrayList1);
                        team1_list.setAdapter(squad_adapter);
                        squad_adapter = new Squad_adapter(getActivity(), squadArrayList2);
                        team2_list.setAdapter(squad_adapter);
                    } // months.length();
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
