package com.cricflame.cricflame.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.BrowsePlayer;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Deepak Sharma on 29/10/2017.
 */

public class Player_Info  extends Fragment {
    TextView name,born,birth_place,height,role,batting_style,bowling_style,bat_test,bat_odi,bat_t20,bowl_test,bowl_odi,bowl_t20;
    ImageView player_image;
    TextView team_played,player_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_info_layout, container, false);
        name= (TextView) view.findViewById(R.id.player_name);
        born= (TextView) view.findViewById(R.id.born);
        birth_place= (TextView) view.findViewById(R.id.birth_place);
        height= (TextView) view.findViewById(R.id.height);
        role= (TextView) view.findViewById(R.id.role);
        team_played= (TextView) view.findViewById(R.id.team_played);
        player_profile= (TextView) view.findViewById(R.id.profile);
        batting_style= (TextView) view.findViewById(R.id.batting_style);
        bowling_style= (TextView) view.findViewById(R.id.bowling_style);
        bat_test= (TextView) view.findViewById(R.id.bat_test);
        bat_odi= (TextView) view.findViewById(R.id.bat_odi);
        bat_t20= (TextView) view.findViewById(R.id.bat_t20);
        bowl_test= (TextView) view.findViewById(R.id.bowl_test);
        bowl_odi= (TextView) view.findViewById(R.id.bowl_odi);
        bowl_t20= (TextView) view.findViewById(R.id.bowl_t20);
        player_image= (ImageView) view.findViewById(R.id.player_image);

        if(new CheckProxy().isProxyDisabled()){
            getAllPlayerData();
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            getActivity().finish();
        }


        return view;
    }

    public void getAllPlayerData(){

        JsonObjectRequest productRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "player.php?id="+ BrowsePlayer.Player_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Log.e("Response from Server :","  ₹ "+jsonObject.toString());

                    JSONObject data = jsonObject.getJSONObject("data");
                    name.setText(data.getString("name"));
                    Glide.with(getActivity()).load(Global.PLAYER_URL+data.getString("img")).into(player_image);

                    JSONObject profile=data.getJSONObject("personal_info");

                    born.setText(profile.getString("born"));
                    birth_place.setText(profile.getString("birthplace"));
                    height.setText(profile.getString("height"));
                    role.setText(profile.getString("role"));
                    batting_style.setText(profile.getString("batting_style"));
                    bowling_style.setText(profile.getString("bowling_style"));
                    player_profile.setText(Html.fromHtml(data.getString("profile")));

                    JSONObject played=data.getJSONObject("teams");
                    team_played.setText(played.getString("played_for"));

                    JSONObject ranking=data.getJSONObject("icc_ranking");
                    bat_test.setText(ranking.getString("test_bat"));
                    bat_odi.setText(ranking.getString("odi_bat"));
                    bat_t20.setText(ranking.getString("t20_bat"));
                    bowl_test.setText(ranking.getString("test_bowl"));
                    bowl_odi.setText(ranking.getString("odi_bowl"));
                    bowl_t20.setText(ranking.getString("t20_bowl"));



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

