
package com.cricflame.cricflame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.AbsSavedState;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.PointTable_Series_Adapter;
import com.cricflame.cricflame.Adapter.TourAdapter_New;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.SeriesWise_Model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PointsTable_Activity extends AppCompatActivity {
    private ImageView back;
    RecyclerView SeriesName_Recycler;
    PointTable_Series_Adapter pointTable_series_adapter;
    LoadingDialog loadingDialog;
    ArrayList<SeriesWise_Model> SeriesList = new ArrayList<>();
    RequestQueue requestQueue;
    ProgressBar pb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.requestQueue = Volley.newRequestQueue(this);
        pb = findViewById(R.id.serieslist_progress);
        pb.setVisibility(0);
        SeriesName_Recycler = findViewById(R.id.series_list);
        loadingDialog = new LoadingDialog(PointsTable_Activity.this);


       // new GetSeries().execute();
        GetSeries();
    }

    public void GetSeries(){
        loadingDialog.show();
        JsonArrayRequest getSeries = new JsonArrayRequest(Global.JAVA_URL+"seriesList", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0; i<response.length(); i++){
                        SeriesWise_Model list = new SeriesWise_Model();
                        list.date_time = response.getJSONObject(i).getString("seriesId");
                        list.tourname = response.getJSONObject(i).getString("seriesName");
                        list.timeperiod = response.getJSONObject(i).getString("url");
                        SeriesList.add(list);
                    }

                    pointTable_series_adapter = new PointTable_Series_Adapter(PointsTable_Activity.this, SeriesList);
                    SeriesName_Recycler.setHasFixedSize(true);
                    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(PointsTable_Activity.this,LinearLayoutManager.VERTICAL, false);
                    //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    SeriesName_Recycler.setLayoutManager(mLayoutManager);
                    SeriesName_Recycler.setAdapter(pointTable_series_adapter);
                    pointTable_series_adapter.notifyDataSetChanged();
                    pb.setVisibility(8);
                    SeriesName_Recycler.setNestedScrollingEnabled(false);
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if(loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }
        });
        getSeries.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getSeries);
    }

    public class GetSeries extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler sh = new ServiceHandler();
            HashMap<String, String> args1 = new HashMap<>();
            args1.put("", "");
            String resultServer = sh.makePostCall("http://192.168.1.104:8081/api/seriesList", args1);
            SeriesList = new ArrayList<>();
            try{
                JSONArray seriesArray = new JSONArray(resultServer);
                for(int i = 0; i<seriesArray.length(); i++){
                    SeriesWise_Model list = new SeriesWise_Model();
                    list.date_time = seriesArray.getJSONObject(i).getString("seriesId");
                    list.tourname = seriesArray.getJSONObject(i).getString("seriesName");
                    list.timeperiod = seriesArray.getJSONObject(i).getString("");
                    SeriesList.add(list);
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

        }
    }
}
