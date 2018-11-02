package com.cricflame.cricflame;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.AllMatchData;
import com.cricflame.cricflame.LiveLine1.PitchReport.AnalysisActivity;

import com.cricflame.cricflame.Model.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Global.Tour_Id;
import static com.cricflame.cricflame.Global.Tour_name;

class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder> {
        Context context;
        ArrayList<AllMatchData> leavelist = new ArrayList<AllMatchData>();



        public ViewPagerAdapter(Context context, ArrayList<AllMatchData> leavelist) {
            this.context = context;
            this.leavelist = leavelist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(com.cricflame.cricflame.R.layout.viewpager_item, parent, false);
            MyViewHolder myholder = new MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final AllMatchData leave = leavelist.get(position);
            MainActivity.MatchId = leavelist.get(position).getId();

            holder.notification.setTag(position);

            holder.notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNotificationUpdate(leavelist.get(Integer.parseInt(v.getTag().toString())).getId(), SharedPrefManager.getInstance(context).getDeviceToken(), "current");
                }
            });

            holder.btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,AnalysisActivity.class));
                }
            });

            holder.btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,TourDetailsActivity.class);
                    Tour_Id = leavelist.get(position).getSeries_id();
                    Tour_name = leavelist.get(position).getMatch_date();
                   context.startActivity(intent);
                }
            });

            holder.txtpopulation.setText(leavelist.get(position).getMatch_date());
            holder.team1_name.setText(leavelist.get(position).getTeam1_name());
            holder.team2_name.setText(leavelist.get(position).getTeam2_name());
            holder.team1_score.setText(leavelist.get(position).getTeam1_score());
            holder.team2_score.setText(leavelist.get(position).getTeam2_score());
            holder.txtrank.setText(leavelist.get(position).getResult());
            Glide.with(context).load(Global.PHOTO_URL + leavelist.get(position).getTeam1_flag()).into(holder.team1_flag);
            Glide.with(context).load(Global.PHOTO_URL + leavelist.get(position).getTeam2_flag()).into(holder.team2_flag);


        }

        @Override
        public int getItemCount() {
            return leavelist.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txtrank, team1_name, team1_score;
            ImageView team1_flag, team2_flag, notification;
            TextView txtpopulation, team2_name, team2_score;

            TextView btn_1,btn_2;

            public MyViewHolder(View view) {
                super(view);

                notification = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.notification);
                team1_flag = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.team1_flag);
                team2_flag = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.team2_flag);
                team1_name = (TextView) view.findViewById(com.cricflame.cricflame.R.id.team1_name);
                team2_name = (TextView) view.findViewById(com.cricflame.cricflame.R.id.team2_name);
                team1_score = (TextView) view.findViewById(com.cricflame.cricflame.R.id.team1_score);
                team2_score = (TextView) view.findViewById(com.cricflame.cricflame.R.id.team2_score);
                txtrank = (TextView) view.findViewById(com.cricflame.cricflame.R.id.rank);

                txtpopulation = (TextView) view.findViewById(com.cricflame.cricflame.R.id.country);

                btn_1 = (TextView) view.findViewById(R.id.btn_1);
                btn_2 = (TextView) view.findViewById(R.id.btn_2);
               // LinearLayout pager = (LinearLayout) view.findViewById(com.cricflame.cricket.R.id.pager_view);


            }
        }


        public Object getItem(int location) {
            return leavelist.get(location);
        }

    public void setNotificationUpdate(String matchId, String token, String status) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Global.URL + "set-notification.php?match_id=" + matchId + "&device_token=" + token + "&status=" + status, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("response", "" + response.toString());
                    if (response.getString("msg").equals("SUCCESS")) {
                        Toast.makeText(context, "Notification set for this match", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error in connection", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

}