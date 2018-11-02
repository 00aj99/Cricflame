package com.cricflame.cricflame.News;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.R;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsDetail extends AppCompatActivity {
    TextView heading,time,description;
    ImageView news_image,back;
    private TextView source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        source= (TextView) findViewById(R.id.source);
        heading= (TextView) findViewById(R.id.heading);
        description= (TextView) findViewById(R.id.description);
        time= (TextView) findViewById(R.id.time);
        news_image= (ImageView) findViewById(R.id.news_image);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(getIntent().getStringExtra("dataFrom").equals("firebase")){
            source.setText("Source: "+getIntent().getStringExtra("Source"));
            heading.setText(getIntent().getStringExtra("Headline"));
            time.setText(getIntent().getStringExtra("Date"));
            description.setText(getIntent().getStringExtra("Para"));
            if(getIntent().getStringExtra("Image").equals(" ") || getIntent().getStringExtra("Image").equals(null)){
                Glide.with(NewsDetail.this).load(R.drawable.app_logo).into(news_image);
            }else{
                Glide.with(NewsDetail.this).load(getIntent().getStringExtra("Image")).into(news_image);
            }
        }else if(getIntent().getStringExtra("dataFrom").equals("mysql")){
            getNewsDetails(getIntent().getStringExtra("id"));
        }



    }

    public void getNewsDetails(String id){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"full_news.php?id="+ id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("data");

                        heading.setText(jsonObject.getString("heading"));
                        time.setText(jsonObject.getString("time"));
                        description.setText(Html.fromHtml(jsonObject.getString("long_desp")));

                        Glide.with(NewsDetail.this).load(Global.IMG_URL+jsonObject.getString("image")).into(news_image);

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
        Volley.newRequestQueue(NewsDetail.this).add(jsonObjectRequest);

    }

}
