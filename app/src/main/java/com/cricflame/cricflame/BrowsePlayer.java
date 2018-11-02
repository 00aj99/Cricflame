package com.cricflame.cricflame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Model.PlayerData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BrowsePlayer extends AppCompatActivity {
    RecyclerView recyclerView;
    public ArrayList<PlayerData> playerArrayList=new ArrayList<>();
    PlayerAdapter adapter;
    ImageView back;
    SearchView searchView;
    public static String Player_Id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_browse_player);
        recyclerView = (RecyclerView) findViewById(com.cricflame.cricflame.R.id.recycler_view);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        searchView=(SearchView) findViewById(com.cricflame.cricflame.R.id.searchView);
        searchView.setQueryHint("Search Player");
        searchView.setIconified(false);
        //The above line will expand it to fit the area as well as throw up the keyboard

        //To remove the keyboard, but make sure you keep the expanded version:
        searchView.clearFocus();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.replaceAll(" ", "%20").toLowerCase();
                if(new CheckProxy().isProxyDisabled()){
                    getPlayerSearch(query);
                }else {
                    Toast.makeText(BrowsePlayer.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                }

               // Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

//        SearchView.OnCloseListener closeListener = new SearchView.OnCloseListener(){
//
//            @Override
//            public boolean onClose() {
//                return false;
//            }
//        };
       // searchView.setOnCloseListener(closeListener);

        if(new CheckProxy().isProxyDisabled()){
            getAllPlayerData();
        }else {
            Toast.makeText(BrowsePlayer.this, "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        adapter= (PlayerAdapter) recyclerView.getAdapter();
                        PlayerData product= (PlayerData) adapter.getItem(position);
                        Player_Id=product.getId();
                        startActivity(new Intent(BrowsePlayer.this,PlayerDetail.class));

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  getAllPlayerData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // finish();
    }


    public void getPlayerSearch(String query){
        playerArrayList.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL+"trending_player.php?name="+query, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject teamData = jsonArray.getJSONObject(i);
                        PlayerData playerData = new PlayerData();
                        playerData.setPlayer_name(teamData.getString("player"));
                        playerData.setId(teamData.getString("id"));
                        playerData.setPlayer_team(teamData.getString("team"));
                        playerData.setImage(teamData.getString("img"));



                        playerArrayList.add(playerData);

                    }

                    adapter = new PlayerAdapter(BrowsePlayer.this, playerArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BrowsePlayer.this, 1);
                    recyclerView.setLayoutManager(mLayoutManager);
                    //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",""+error.toString());
            }
        });
        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(BrowsePlayer.this).add(jsonObjectRequest);

    }

    public void getAllPlayerData(){


        playerArrayList.clear();


            JsonObjectRequest productRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "browse_player.php", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        // Log.e("Response from Server :","  ₹ "+jsonObject.toString());

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject teamData = jsonArray.getJSONObject(i);
                                PlayerData playerData = new PlayerData();
                                playerData.setPlayer_name(teamData.getString("player"));
                                playerData.setId(teamData.getString("id"));
                                playerData.setPlayer_team(teamData.getString("team"));
                                playerData.setImage(teamData.getString("img"));



                                playerArrayList.add(playerData);

                            }

                            adapter = new PlayerAdapter(BrowsePlayer.this, playerArrayList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BrowsePlayer.this, 1);
                            recyclerView.setLayoutManager(mLayoutManager);
                            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            recyclerView.invalidate();




                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("error",""+volleyError.toString());

                }
            });
        productRequest.setShouldCache(false);
        productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(BrowsePlayer.this).add(productRequest);



    }


    public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<PlayerData> products;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, Description,Id,team;
            public ImageView imageView;
            RatingBar ratingBar;

            public MyViewHolder(View view) {
                super(view);
                Id = (TextView) view.findViewById(com.cricflame.cricflame.R.id.id);
                title = (TextView) view.findViewById(com.cricflame.cricflame.R.id.name);
                team = (TextView) view.findViewById(com.cricflame.cricflame.R.id.team);
                imageView = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.team_info);


            }
        }


        public PlayerAdapter(Context mContext, ArrayList<PlayerData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(com.cricflame.cricflame.R.layout.browse_player_view, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.title.setText(products.get(position).getPlayer_name());
            holder.team.setText(products.get(position).getPlayer_team());
            holder.Id.setText(products.get(position).getId());
            if(!(Global.PLAYER_URL + products.get(position).getImage()).equals("")) {
                Glide.with(mContext).load(Global.PLAYER_URL + products.get(position).getImage()).centerCrop().into(holder.imageView);

            }
            else{
                Glide.with(mContext).load(com.cricflame.cricflame.R.drawable.default_player).error(com.cricflame.cricflame.R.drawable.default_player).centerCrop().into(holder.imageView);

            }

        }

        @Override
        public int getItemCount() {
            return products.size();
        }


    }


}
