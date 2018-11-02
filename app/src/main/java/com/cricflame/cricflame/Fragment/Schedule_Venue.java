package com.cricflame.cricflame.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.VenueData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Fragment.VenueFragment.VenueId;
import static com.cricflame.cricflame.Global.SCHEDULE_ID;


/**
 * Created by Deepak Sharma on 25/11/2017.
 */

public class Schedule_Venue extends Fragment {
    public ArrayList<VenueData> tourData=new ArrayList<VenueData>();
    ListView venue_list;
    Venue_Adapter venue_adapter;

    ImageView no_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venue_layout, container, false);
        venue_list = (ListView) view.findViewById(R.id.venue_list);
        no_data = (ImageView) view.findViewById(R.id.no_data);
        getAllVenues();

        venue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                venue_adapter = (Venue_Adapter) parent.getAdapter();
                VenueData venueData = (VenueData) venue_adapter.getItem(position);
                VenueId = venueData.getId();
                startActivity(new Intent(getActivity(), Venue_Details_Fragment.class));
            }
        });
        return view;
    }

    public void getAllVenues() {
        tourData.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "schedule_match.php?id="+SCHEDULE_ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");
                    JSONObject ground=response.getJSONObject("data");
                    JSONArray jsonArray = ground.getJSONArray("ground");
                    if (jsonArray.length() <= 0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    } else {
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                            VenueData venue = new VenueData();
                            venue.setId(jsonObject1.getString("id"));
                            venue.setGround_name(jsonObject1.getString("venue"));
                           // venue.setGround_city(jsonObject1.getString("city"));
                            venue.setGround_image(jsonObject1.getString("img"));

                            tourData.add(venue);
                        }


                        venue_adapter = new Venue_Adapter(getActivity(), tourData);
                        venue_list.setAdapter(venue_adapter);
                    } // months.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setShouldCache(false); jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

    public class Venue_Adapter extends BaseAdapter {

        private Activity activity;

        private LayoutInflater inflater;
        private ArrayList<VenueData> Items;


        public Venue_Adapter(Activity activity, ArrayList<VenueData> Items) {
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
                convertView = inflater.inflate(R.layout.news_layout_view, null);

            TextView Id = (TextView) convertView.findViewById(R.id.id);
            TextView Title = (TextView) convertView.findViewById(R.id.title);
            TextView Duration = (TextView) convertView.findViewById(R.id.duration);
            TextView Description = (TextView) convertView.findViewById(R.id.description);
            ImageView Image = (ImageView) convertView.findViewById(R.id.news_image);

            Id.setText(Items.get(position).getId());
            Title.setText(Items.get(position).getGround_name());

          //  Description.setText(Items.get(position).getGround_city());

            Glide.with(activity).load(Global.IMG_VENUE_URL + Items.get(position).getGround_image()).into(Image);

            return convertView;

        }


    }
}
