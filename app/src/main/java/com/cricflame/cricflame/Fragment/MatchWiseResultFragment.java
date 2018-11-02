package com.cricflame.cricflame.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.AllMatchResultAdapter;
import com.cricflame.cricflame.DayByDay.DaybydayMatchesResult;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.News.EndlessRecyclerOnScrollListener;
import com.cricflame.cricflame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class MatchWiseResultFragment extends Fragment {

    public ArrayList<TourDataNew> allMatchArrayList = new ArrayList<>();
    private AllMatchResultAdapter allMatch_adapter;
    private RecyclerView all_match_list;
    DatabaseReference mRootref;
    DatabaseReference childref;
    int currentItem = 0;
    DaybydayMatchesResult activity;
    private int mPostsPerPage = 10;
    private LoadingDialog loadingDialog;
    int checkScrolling;
    LinearLayout Main_lay;
    String setDate ="0";
    RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_wise_result, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        init(view);
        GetResult();

        return view;
    }

    private void init(View view){

        activity = (DaybydayMatchesResult) getActivity();

        all_match_list= (RecyclerView) view.findViewById(R.id.all_match_list);
        Main_lay = view.findViewById(R.id.main_layout);

        try{
            mRootref = FirebaseUtils.getSecondaryDatabase(activity).getReference();
            loadingDialog = new LoadingDialog(activity);

        }catch (Exception e){
            e.printStackTrace();
        }

        loadingDialog.show();
        allMatch_adapter = new AllMatchResultAdapter(activity,allMatchArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        all_match_list.setLayoutManager(linearLayoutManager);
        allMatch_adapter.notifyDataSetChanged();
        allMatch_adapter.setHasStableIds(true);
        all_match_list.setHasFixedSize(true);
        all_match_list.setNestedScrollingEnabled(false);
        all_match_list.setAdapter(allMatch_adapter);

        all_match_list.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if(checkScrolling>=10){
                    loadMoreData();
                }

            }
        });

    }
    void GetResult () {
     /*   this.requestQueue.add(new JsonArrayRequest(Global.RESULT_URL+"ALL/DATEWISE.json", new Response.Listener<JSONArray>() {

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
                        allMatchArrayList.add(list);
                        allMatch_adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


                if(loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));*/
        checkScrolling =0;
        childref = mRootref.child("Result").child("ALL").child("DATEWISE");
        childref.orderByKey()
                .startAt(String.valueOf(currentItem))
                .limitToFirst(mPostsPerPage)
                .addListenerForSingleValueEvent(new ValueEventListener() {
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
                                allMatchArrayList.add(list);
                                if(currentItem == 0){
                                    allMatch_adapter.notifyDataSetChanged();
                                }else{
                                    allMatch_adapter.notifyItemInserted(allMatchArrayList.size() - 1);
                                }

                                checkScrolling++;
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
    }
    private void loadMoreData() {
        currentItem = currentItem + 10;
        GetResult();
    }
}
