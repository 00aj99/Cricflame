package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.Adapter.AllMatch_Adapter;
import com.cricflame.cricflame.BrowseMatchDetail;
import com.cricflame.cricflame.Model.AllMatchData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Global.Tour_Id;
import static com.cricflame.cricflame.Global.URL;

/**
 * Created by Deepak Sharma on 04/10/2017.
 */

public class All_Matches_Fragment extends Fragment {
    ListView all_match_list;
    AllMatch_Adapter allMatch_adapter;
    public  ArrayList<AllMatchData> allMatchArrayList=new ArrayList<>();
    public static String BMatchId="";
    ImageView no_data;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_matches_layout, container, false);
        all_match_list= (ListView) view.findViewById(R.id.all_match_list);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        getAllMatch();
        all_match_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allMatch_adapter= (AllMatch_Adapter) parent.getAdapter();
                AllMatchData allMatchData= (AllMatchData) allMatch_adapter.getItem(position);
                BMatchId=allMatchData.getId();
                startActivity(new Intent(getActivity(),BrowseMatchDetail.class));
            }
        });
        return view;
    }



    public void getAllMatch(){
        allMatchArrayList.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL+"match.php?id="+Tour_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");

                    JSONArray jsonArray=response.getJSONArray("data");
                    if (jsonArray.length()<=0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    }
                    else {
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                            AllMatchData matchData = new AllMatchData();
                            matchData.setId(jsonObject1.getString("id"));
                            matchData.setMatch_date(jsonObject1.getString("series"));
                            matchData.setMatch_name(jsonObject1.getString("match_no"));
                            matchData.setTeam1_name(jsonObject1.getString("team1"));
                            matchData.setTeam1_score(jsonObject1.getString("team1_run"));
                            matchData.setTeam1_flag(jsonObject1.getString("team1_flag"));
                            matchData.setTeam2_flag(jsonObject1.getString("team2_flag"));
                         //   matchData.setTeam1_over(jsonObject1.getString("team1_over"));
                         //   matchData.setTeam2_over(jsonObject1.getString("team2_over"));
                            matchData.setTeam2_name(jsonObject1.getString("team2"));
                            matchData.setTeam2_score(jsonObject1.getString("team2_run"));
                            matchData.setResult(jsonObject1.getString("status"));
                          //  matchData.setVenue(jsonObject1.getString("venue"));
                            allMatchArrayList.add(matchData);
                        }
                    }
                    allMatch_adapter=new AllMatch_Adapter(getActivity(),allMatchArrayList);
                    all_match_list.setAdapter(allMatch_adapter);
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
        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

}