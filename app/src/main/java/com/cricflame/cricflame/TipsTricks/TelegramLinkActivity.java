package com.cricflame.cricflame.TipsTricks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.TipsTricks.GameListActivity.GAME_ID;

public class TelegramLinkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TipsAdapter adapter;
    public ArrayList<TourData> link=new ArrayList<>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_link);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        OnClick();
    }

    public void OnClick(){
        recyclerView = (RecyclerView) findViewById(com.cricflame.cricflame.R.id.recycler_view);
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        adapter= (TipsAdapter) recyclerView.getAdapter();
                        TourData product= (TourData) adapter.getItem(position);
                        GAME_ID=product.getId();
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(GAME_ID));
                        startActivity(i);
                    }
                });

        if(new CheckProxy().isProxyDisabled()){
            getTimePassData();
        }else {
            Toast.makeText(TelegramLinkActivity.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }
    }

    public void getTimePassData(){
        link.clear();
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"telegram_link.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonObject=response.getJSONArray("data");
                    for (int i=0;i<jsonObject.length();i++){
                        JSONObject team=jsonObject.getJSONObject(i);

                        TourData betfair=new TourData();
                        betfair.setLeague_name(team.getString("heading"));
                        betfair.setImage(team.getString("img"));

                        betfair.setId(team.getString("link"));
                        betfair.setDescription(team.getString("desp"));
                        link.add(betfair);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new TipsAdapter(TelegramLinkActivity.this, link);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TelegramLinkActivity.this, 1);
                recyclerView.setLayoutManager(mLayoutManager);

                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                //progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();

            }
        });
        jsonArrayRequest.setShouldCache(false);jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(TelegramLinkActivity.this).add(jsonArrayRequest);
    }
}
