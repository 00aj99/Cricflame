package com.cricflame.cricflame.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.SeriesResultAdapter;
import com.cricflame.cricflame.Adapter.SeriesWise_Adapter;
import com.cricflame.cricflame.DayByDay.DaybydayMatchesResult;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.News.EndlessRecyclerOnScrollListener;
import com.cricflame.cricflame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class SeriesWiseResultFragment extends Fragment {

    private ArrayList<SeriesWise_Model> SeriesData = new ArrayList<>();
    private RecyclerView rcvSeriesWiseReslut;
    private SeriesResultAdapter seriesWise_adapter;
    private DatabaseReference mRootref;
    private DatabaseReference childref;
    private LoadingDialog loadingDialog;
    private int currentItem = 0;
    private int mPostsPerPage = 10;
    private int checkScrolling;
    private String setDate ="0";
    private DaybydayMatchesResult activity;
    RequestQueue requestQueue;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series_wise_result, container, false);
        rcvSeriesWiseReslut = (RecyclerView) view.findViewById(R.id.rcv_series_wise_result);
        init(view);


        return view;
    }

    private void init(View view){
        activity = (DaybydayMatchesResult) getActivity();

        try{
            mRootref = FirebaseUtils.getSecondaryDatabase(activity).getReference();
            loadingDialog = new LoadingDialog(activity);

        }catch (Exception e){
            e.printStackTrace();
        }

        loadData();

    }

    private void loadData(){
        rcvSeriesWiseReslut.setHasFixedSize(true);
        seriesWise_adapter = new SeriesResultAdapter(activity,SeriesData,"ALL");
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false);
        //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcvSeriesWiseReslut.setLayoutManager(mLayoutManager);
        rcvSeriesWiseReslut.setAdapter(seriesWise_adapter);
        rcvSeriesWiseReslut.setNestedScrollingEnabled(false);
        GetMatches();
    }


    public void GetMatches() {
      //loadingDialog.show();
        this.requestQueue.add(new JsonObjectRequest(Global.RESULT_URL+"ALL/SERIEWISE.json", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Iterator objects = response.keys();
                 while (objects.hasNext()) {
                    try{
                        String key = objects.next().toString();
                        JSONArray match = response.getJSONArray(key);
                        SeriesWise_Model list = new SeriesWise_Model();
                        list.date_time ="hide date";
                        list.tourname = key;
                        list.seriesid = match.getJSONObject(0).getString("seriesid");
                        SeriesData.add(list);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


                seriesWise_adapter.notifyDataSetChanged();

             //if(loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(SeriesData.size()>0){

            }else{
                SeriesData.clear();
                currentItem = 0;
                setDate = "0";
                GetMatches();
            }

        }
    }*/


}
