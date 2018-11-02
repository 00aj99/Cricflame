package com.cricflame.cricflame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Adapter.VideoViewAdapter;
import com.cricflame.cricflame.Model.TourData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pavneet on 12-Sep-17.
 */

public class VideoPage extends AppCompatActivity {


    //RECYCLER VIEW FIELD
    RecyclerView recyclerView;
    VideoViewAdapter videoViewAdapter;
    public  ArrayList<TourData> videoList=new ArrayList<>();


    //VECTOR FOR VIDEO URLS


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.video_main);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(com.cricflame.cricflame.R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        videoViewAdapter= (VideoViewAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) videoViewAdapter.getItem(position);
                      //  VIDEO_URL=product.getMonth();
                        startActivity(new Intent(VideoPage.this,VideoViewActivity.class));

                        // do it
                    }
                });


        getAllVideo();


    }
    public void getAllVideo() {
        videoList.clear();

        JsonObjectRequest productRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "videos.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Log.e("Response from Server :","  ₹ "+jsonObject.toString());

                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        JSONObject video=data.getJSONObject(i);
                        TourData videoData=new TourData();
                        videoData.setId(video.getString("id"));
                        videoData.setLeague_name(video.getString("img"));
                        videoData.setMonth(video.getString("video"));
                        videoData.setHeading(video.getString("heading"));
                        videoList.add(videoData);

                    }
                    videoViewAdapter=new VideoViewAdapter(VideoPage.this,videoList);
                    recyclerView.setAdapter(videoViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(VideoPage.this).add(productRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}