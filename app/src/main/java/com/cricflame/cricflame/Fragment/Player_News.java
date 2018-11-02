package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.BrowsePlayer;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Adapter.News_Adapter;
import com.cricflame.cricflame.Model.NewsData;
import com.cricflame.cricflame.News.NewsDetail;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Deepak Sharma on 29/10/2017.
 */

public class Player_News  extends Fragment {
    public ArrayList<NewsData> newsArrayData = new ArrayList<NewsData>();
    ListView news_list;
    News_Adapter news_adapter;
    ImageView no_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        news_list = (ListView) view.findViewById(R.id.news_List);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        if(new CheckProxy().isProxyDisabled()){
            getAllNews();
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            getActivity().finish();
        }

        news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                news_adapter = (News_Adapter) parent.getAdapter();
                NewsData newsData = (NewsData) news_adapter.getItem(position);
                Intent intent = new Intent(getActivity(), NewsDetail.class);
                intent.putExtra("id",newsData.getId());
                intent.putExtra("dataFrom","mysql");
                startActivity(intent);
            }
        });
        return view;
    }

    public void getAllNews(){
        newsArrayData.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"player_news.php?id="+ BrowsePlayer.Player_Id, null, new Response.Listener<JSONObject>() {
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
                            NewsData newsData = new NewsData();
                            newsData.setId(jsonObject1.getString("id"));
                            newsData.setTitle(jsonObject1.getString("heading"));
                            newsData.setDescription(jsonObject1.getString("desp"));
                            newsData.setDuration(jsonObject1.getString("time"));
                            newsData.setImage(jsonObject1.getString("image"));
                            newsArrayData.add(newsData);
                        }


                        news_adapter = new News_Adapter(getActivity(), newsArrayData);
                        news_list.setAdapter(news_adapter);
                    }// months.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setShouldCache(false);jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

}
