
package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Adapter.VideoViewAdapter;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Deepak Sharma on 22/10/2017.
 */


 public class Video_Fragment extends Fragment {
    RecyclerView recyclerView;
    VideoViewAdapter videoViewAdapter;
    public ArrayList<TourData> videoList=new ArrayList<>();
    public static String VIDEO_URL="";
    ImageView no_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        videoViewAdapter= (VideoViewAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) videoViewAdapter.getItem(position);
                        VIDEO_URL=product.getMonth();
                       // VideoPlayerActivity.VIDEO_HEADING=product.getHeading();
                        //startActivity(new Intent(getActivity(),VideoViewActivity.class));
                        //startActivity(new Intent(getActivity(),VideoPlayerActivity.class));

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URL));
                        startActivity(i);

                        // do it
                    }
                });


        getAllVideo();
        return view;


    }
    public void getAllVideo() {
        videoList.clear();

        JsonObjectRequest productRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "videos.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Log.e("Response from Server :","  ₹ "+jsonObject.toString());

                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data.length()<=0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    }
                    else {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject video = data.getJSONObject(i);
                            TourData videoData = new TourData();
                            videoData.setId(video.getString("id"));
                            videoData.setLeague_name(video.getString("img"));
                            videoData.setMonth(video.getString("video"));
                            videoData.setHeading(video.getString("heading"));
                            videoList.add(videoData);

                        }
                        videoViewAdapter = new VideoViewAdapter(getActivity(), videoList);
                        recyclerView.setAdapter(videoViewAdapter);
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
        productRequest.setShouldCache(false);
        productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(productRequest);

    }
}
