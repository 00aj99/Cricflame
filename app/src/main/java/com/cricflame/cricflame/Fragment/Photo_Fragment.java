package com.cricflame.cricflame.Fragment;

        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.DefaultItemAnimator;
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
        import com.cricflame.cricflame.Adapter.PhotoViewAdapter;
        import com.cricflame.cricflame.Common.ServiceHandler;
        import com.cricflame.cricflame.PhotoDetailActivity;
        import com.cricflame.cricflame.Global;
        import com.cricflame.cricflame.Model.TourData;
        import com.cricflame.cricflame.R;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;

/**
 * Created by Deepak Sharma on 22/10/2017.
 */

public class Photo_Fragment extends Fragment {

    PhotoViewAdapter videoViewAdapter;
    public  ArrayList<TourData> videoList=new ArrayList<>();
    RecyclerView gridView;
    ImageView no_data;
    public static String PHOTO_ID="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_fragment_layout, container, false);
        gridView = (RecyclerView) view.findViewById(R.id.recycler_view);
        no_data= (ImageView) view.findViewById(R.id.no_data);

        ItemClickSupport.addTo(gridView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        videoViewAdapter= (PhotoViewAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) videoViewAdapter.getItem(position);
                        PHOTO_ID=product.getLeague_name();
                        startActivity(new Intent(getActivity(), PhotoDetailActivity.class));


                        // do it
                    }
                });

        //getAllPhoto();
        new GetAllPhoto().execute();
        return view;


    }

    public void getAllPhoto() {
        videoList.clear();

        JsonObjectRequest productRequest = new JsonObjectRequest(Request.Method.POST, Global.URL + "images.php", null, new Response.Listener<JSONObject>() {
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
                            videoData.setHeading(video.getString("heading"));
                            videoData.setDescription(video.getString("description"));
                            videoData.setStrat_date(video.getString("date"));
                            videoList.add(videoData);

                        }
                        videoViewAdapter = new PhotoViewAdapter(getActivity(), videoList);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                        gridView.setLayoutManager(mLayoutManager);
                        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                        gridView.setItemAnimator(new DefaultItemAnimator());
                        gridView.setAdapter(videoViewAdapter);
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
        productRequest.setShouldCache(false); productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(productRequest);

    }


    public class GetAllPhoto extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler sh = new ServiceHandler();
             String url=Global.URL+"images.php";
            HashMap<String, String> args1 = new HashMap<>();
            args1.put("", "");
            videoList.clear();
            String resultServer = sh.makePostCall(url, args1);
            try {

                if (resultServer != null) {
                    JSONObject jObj = new JSONObject(resultServer);
                    JSONArray data = jObj.getJSONArray("data");
                    if (data.length() <= 0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    } else {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject video = data.getJSONObject(i);
                            TourData videoData = new TourData();
                            videoData.setId(video.getString("id"));
                            videoData.setLeague_name(video.getString("img"));
                            videoData.setHeading(video.getString("heading"));
                            videoData.setDescription(video.getString("description"));
                            videoData.setStrat_date(video.getString("date"));
                            videoList.add(videoData);

                        }

                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


            @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(videoList.size()>0){
                videoViewAdapter = new PhotoViewAdapter(getActivity(), videoList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                gridView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                gridView.setItemAnimator(new DefaultItemAnimator());
                gridView.setAdapter(videoViewAdapter);
            }

        }
    }
}
