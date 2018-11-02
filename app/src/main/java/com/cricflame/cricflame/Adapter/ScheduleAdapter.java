package com.cricflame.cricflame.Adapter;

/**
 * Created by webore on 12/15/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import com.cricflame.cricflame.MainActivity;
import com.cricflame.cricflame.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Bhoopendra on 06-07-2016.
 */
public class ScheduleAdapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<TourData> Items;



    public ScheduleAdapter(Activity activity, ArrayList<TourData> Items) {
        this.activity = activity;
        this.Items = Items;
    }



    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int location) {
        return Items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.schedule_match_view,null);

        TextView Id = (TextView) convertView.findViewById(R.id.id);
        TextView Name = (TextView) convertView.findViewById(R.id.name);
        TextView month = (TextView) convertView.findViewById(R.id.month);
        TextView TourDate = (TextView) convertView.findViewById(R.id.tour_date);
        ImageView notification= (ImageView) convertView.findViewById(R.id.notification);
        MainActivity.MatchId=Items.get(position).getId();
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNotificationUpdate(MainActivity.MatchId, SplashActivity.token,"upcoming");
            }
        });

        Id.setText(Items.get(position).getId());
        Name.setText(Items.get(position).getLeague_name());
        if (Items.get(position).getMonthId().equals("0")){
            month.setText(Items.get(position).getMonth());
        }
        else{
            month.setVisibility(View.GONE);
        }

        TourDate.setText(Items.get(position).getStrat_date()+" - "+Items.get(position).getEnd_date());
        // notifyDataSetChanged();

        return convertView;

    }
    public  void setNotificationUpdate(String matchId,String token,String status){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL + "set-notification.php?match_id="+matchId +"&device_token="+token+"&status="+status, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("error",""+response.toString());
                    if (response.getString("msg").equals("SUCCESS")){
                        Toast.makeText(activity,"Notification set for this match",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity,"Error in connection",Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(activity).add(jsonObjectRequest);

    }


}
