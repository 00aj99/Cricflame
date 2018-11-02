package com.cricflame.cricflame.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.AllMatchResultAdapter;
import com.cricflame.cricflame.Adapter.SeriesResultAdapter;
import com.cricflame.cricflame.Adapter.TourAdapter_New;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.AllMatchData;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.Model.TeamWiseModel;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.betfair.BetfairActivity;
import com.cricflame.cricflame.betfair.BetfairAllMatch;
import com.cricflame.cricflame.betfair.BettingListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Deepak Sharma on 05/10/2017.
 */

public class International_Schedule_Fragment extends Fragment {
    /* public  ArrayList<AllMatchData> tourData=new ArrayList<AllMatchData>();
     ListView tour_list;
     AllMatch_Adapter tour_adapter;
     ImageView no_data;*/
    public  ArrayList<TourDataNew> tourData=new ArrayList<TourDataNew>();
    RecyclerView tour_list;
    AllMatchResultAdapter allMatchResultAdapter;
    TourAdapter_New tourAdapter_new;
    ImageView no_data;
    DatabaseReference mRootref;
    DatabaseReference childref;
    String setDate ="0";
    LoadingDialog loadingDialog;
    Button DateWise,SeriesWise,TeamWise;
    String TourName,Parent;
    int data;
    RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mRootref = FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();
            loadingDialog = new LoadingDialog(getActivity());
            requestQueue = Volley.newRequestQueue(getActivity());
            Intent intent= getActivity().getIntent();
            Bundle bundle=intent.getExtras();
            TourName = bundle.getString("tour_name");
            Parent = bundle.getString("Type");
            data = bundle.getInt("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.international_match_layout,container,false);
        tour_list= (RecyclerView) view.findViewById(R.id.tour_list);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        DateWise = view.findViewById(R.id.date_wise_data);
        SeriesWise = view.findViewById(R.id.series_wise_data);
        TeamWise = view.findViewById(R.id.team_wise_data);

        DateWise.setVisibility(View.GONE);
        SeriesWise.setVisibility(View.GONE);
        TeamWise.setVisibility(View.GONE);

        tourAdapter_new = new TourAdapter_New(getActivity(), tourData);
        allMatchResultAdapter = new AllMatchResultAdapter(getActivity(),tourData);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tour_list.setLayoutManager(mLayoutManager);

        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        tour_list.setItemAnimator(new DefaultItemAnimator());

        if (data == 1) {
            getAllFBMatchesList();
            tour_list.setAdapter(tourAdapter_new);
            tourAdapter_new.notifyDataSetChanged();
        }
        else if (data == 0) {
            GetResults();
            tour_list.setAdapter(allMatchResultAdapter);
            allMatchResultAdapter.notifyDataSetChanged();
        }else if(data == 3){
            getTeamResults();
            tour_list.setAdapter(allMatchResultAdapter);
            allMatchResultAdapter.notifyDataSetChanged();
        }
        else {
            new getAllAPIMatches().execute();
            tour_list.setAdapter(tourAdapter_new);
            tourAdapter_new.notifyDataSetChanged();

        }

        return view;
    }


    public void getTeamResults(){
        tourData.clear();
        loadingDialog.show();
        StringRequest getTeamResults = new StringRequest(Request.Method.POST, Global.Comman_Url+"matchSheduleByTeam",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray j = new JSONArray(response);
                    try {
                        for (int i = 0; i < j.length(); i++) {
                            JSONObject obj = j.getJSONObject(i);
                            TourDataNew list = new TourDataNew();
                            String[] splited = obj.getString("date_time").split("\\s+");
                            String date = splited[0];
                            String time = splited[1];
                            if (setDate.equalsIgnoreCase(date)) {
                                list.date_time = time;
                                setDate = date;
                            } else {
                                list.date_time = obj.getString("date_time");
                                setDate = date;
                            }
                            list.series_name = obj.getString("series_name");
                            list.match_number = obj.getString("match_number");
                            list.team1 = obj.getString("team1");
                            list.team1_flag = obj.getString("team1_flag");
                            list.team2 = obj.getString("team2");
                            list.team2_flag = obj.getString("team2_flag");
                            list.venue = obj.getString("venue");
                            list.f_key = "";
                            list.result = obj.getString("result");
                            list.t1_short_name = obj.getString("t1_short_name");
                            list.t2_short_name = obj.getString("t2_short_name");
                            tourData.add(list);
                            allMatchResultAdapter.notifyDataSetChanged();
                            loadingDialog.hide();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();
                //params.put("matchid", Match_Id);
                params.put("team", TourName);
                params.put("type", Parent.toLowerCase());
                params.put("schedule","1");
                return params;
            }
        };
        getTeamResults.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(getTeamResults);
    }

    public void GetResults(){
        tourData.clear();
        loadingDialog.show();
        this.requestQueue.add(new JsonArrayRequest(Global.RESULT_URL+"ALL/SERIEWISE/"+TourName+".json", new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
              for(int i = 0; i<response.length(); i++){
                  try{
                      TourDataNew list = new TourDataNew();
                      String[] splited =  response.getJSONObject(i).getString("date_time").split("\\s+");
                      String date=splited[0];
                      String time=splited[1];
                      if(setDate.equalsIgnoreCase(date)){
                          list.date_time = time;
                          setDate = date;
                      }else{
                          list.date_time = response.getJSONObject(i).getString("date_time");
                          setDate = date;
                      }
                      list.series_name = response.getJSONObject(i).getString("series_name");
                      list.match_number = response.getJSONObject(i).getString("match_number");
                      list.team1 =response.getJSONObject(i).getString("team1");
                      list.team1_flag = response.getJSONObject(i).getString("team1_flag");
                      list.team2 = response.getJSONObject(i).getString("team2");
                      list.team2_flag =response.getJSONObject(i).getString("team2_flag");
                      list.venue = response.getJSONObject(i).getString("venue");
                      list.f_key = response.getJSONObject(i).getString("f_key");
                      list.result = response.getJSONObject(i).getString("result");
                      list.t1_short_name = response.getJSONObject(i).getString("t1_short_name");
                      list.t2_short_name = response.getJSONObject(i).getString("t2_short_name");
                      tourData.add(list);
                      allMatchResultAdapter.notifyDataSetChanged();
                  }catch (Exception e){
                      e.printStackTrace();
                  }

              }


                if(loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if(loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        }));
    }

    private void getAllFBMatchesList() {
        tourData.clear();
        try {

            loadingDialog.show();
            childref = mRootref.child("Fixtures").child(Parent).child("DATEWISE");
            childref.orderByChild("tourname")
                    .startAt(TourName.trim())
                    .endAt(TourName.trim())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                try{
                                    TourDataNew list = new TourDataNew();
                                    String[] splited =  snapshot.child("date_time").getValue().toString().split("\\s+");
                                    String date=splited[0];
                                    String time=splited[1];
                                    if(setDate.equalsIgnoreCase(date)){
                                        list.date_time = time;
                                        setDate = date;
                                    }else{
                                        list.date_time = snapshot.child("date_time").getValue().toString();
                                        setDate = date;
                                    }
                                    list.series_name = snapshot.child("series_name").getValue().toString();
                                    list.match_number = snapshot.child("match_number").getValue().toString();
                                    list.team1 = snapshot.child("team1").getValue().toString();
                                    list.team1_flag = snapshot.child("team1_flag").getValue().toString();
                                    list.team2 = snapshot.child("team2").getValue().toString();
                                    list.team2_flag = snapshot.child("team2_flag").getValue().toString();
                                    list.venue = snapshot.child("venue").getValue().toString();
                                    list.f_key = snapshot.child("f_key").getValue().toString();
                                    list.result = snapshot.child("result").getValue().toString();
                                    list.t1_short_name = snapshot.child("t1_short_name").getValue().toString();
                                    list.t2_short_name = snapshot.child("t2_short_name").getValue().toString();
                                    tourData.add(list);
                                    tourAdapter_new.notifyDataSetChanged();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }

                            if(loadingDialog.isShowing())
                                loadingDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class getAllAPIMatches extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler sh = new ServiceHandler();
            HashMap<String, String> args1 = new HashMap<>();
            args1.put("team", TourName);
            args1.put("type", Parent.toLowerCase());
            args1.put("schedule","0");
            String resultServer = sh.makePostCall(Global.Comman_Url+"matchSheduleByTeam", args1);
            try{
                JSONArray MatchArray = new JSONArray(resultServer);
                for(int i = 0; i <MatchArray.length(); i++){
                    TourDataNew list = new TourDataNew();
                    list.date_time = MatchArray.getJSONObject(i).getString("date_time");
                    list.series_name = MatchArray.getJSONObject(i).getString("series_name");
                    list.match_number = MatchArray.getJSONObject(i).getString("match_number");
                    list.team1 = MatchArray.getJSONObject(i).getString("team1");
                    list.team1_flag = MatchArray.getJSONObject(i).getString("team1_flag");
                    list.team2 = MatchArray.getJSONObject(i).getString("team2");
                    list.team2_flag = MatchArray.getJSONObject(i).getString("team2_flag");
                    list.venue = MatchArray.getJSONObject(i).getString("venue");
                    tourData.add(list);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(loadingDialog.isShowing())
                loadingDialog.dismiss();
            tourAdapter_new.notifyDataSetChanged();


        }
    }

}
