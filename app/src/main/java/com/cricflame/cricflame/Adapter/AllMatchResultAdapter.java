package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.DayByDay.DaybydayMatchesResult;
import com.cricflame.cricflame.Model.AllMatchData;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.NewLiveMatchActivity;
import com.cricflame.cricflame.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AllMatchResultAdapter extends RecyclerView.Adapter<AllMatchResultAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TourDataNew> products;
    String textAlready = "0";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView MatchDate,SeriesName,MatchName,Team_One_name,Team_Two_name,Match_Time,Venue,matchResult;
        ImageView Team_One_Flag, Team_Two_Flag;
        LinearLayout Main_lay;
        public MyViewHolder(View view) {
            super(view);
            MatchDate = itemView.findViewById(R.id.month);
            SeriesName = itemView.findViewById(R.id.series_name);
            MatchName = itemView.findViewById(R.id.match_name);
            matchResult = itemView.findViewById(R.id.match_result);
            Team_One_name = itemView.findViewById(R.id.team_one_name);
            Team_Two_name = itemView.findViewById(R.id.team_two_name);
            Team_One_Flag = itemView.findViewById(R.id.flag_team_one);
            Team_Two_Flag = itemView.findViewById(R.id.flag_team_two);
            Match_Time = itemView.findViewById(R.id.match_time);
            Venue = itemView.findViewById(R.id.match_venue);
            Main_lay = itemView.findViewById(R.id.main_date_wise_lay);

            matchResult.setVisibility(View.VISIBLE);
        }
    }
    public AllMatchResultAdapter(Context mContext, ArrayList<TourDataNew> products) {
        this.mContext = mContext;
        this.products = products;
    }

    public Object getItem(int location) {
        return products.get(location);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.international_match_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try{
            String date = null;
            String time = null;
            if(products.get(position).date_time.length()>10){
                String[] splited =  products.get(position).date_time.split("\\s+");
                date=splited[0];
                time=splited[1];
                holder.MatchDate.setVisibility(View.VISIBLE);
                textAlready = date;
                holder.MatchDate.setText(returnDate(date)+", "+returnDateone(date));
            }else{
                time = products.get(position).date_time;
                holder.MatchDate.setVisibility(View.GONE);
            }


          /*  String toCompare = date;

                if(textAlready.equalsIgnoreCase(toCompare))
                {
                    holder.MatchDate.setVisibility(View.GONE);
                }else{

                }*/



            holder.SeriesName.setText(products.get(position).series_name);
            holder.MatchName.setText(products.get(position).match_number);
            if (products.get(position).result.equalsIgnoreCase("0")|| products.get(position).result.equalsIgnoreCase("")){
                holder.matchResult.setText("N/A");
            }else holder.matchResult.setText(products.get(position).result);

            try{
                if(products.get(position).team1_flag.equalsIgnoreCase("")){

                }else{
                    Glide.with(mContext).load(products.get(position).team1_flag).into(holder.Team_One_Flag);
                }
                if(products.get(position).team2_flag.equalsIgnoreCase("")){

                }else{
                    Glide.with(mContext).load(products.get(position).team2_flag).into(holder.Team_Two_Flag);
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            holder.Team_One_name.setText(products.get(position).team1);
            holder.Team_Two_name.setText(products.get(position).team2);
            holder.Venue.setText(products.get(position).venue);
            holder.Match_Time.setText(returnTime(time));


            holder.Main_lay.setTag(position);


            holder.Main_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (products.get(position).f_key.equalsIgnoreCase(""))*/ TastyToast.makeText(mContext, "Match Unavailable", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    /*else {
                        Bundle analyticBundle = new Bundle();
                        analyticBundle.putString(FirebaseAnalytics.Param.ITEM_ID, products.get(position).f_key);
                        analyticBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "match");
                        mContext.startActivity(new Intent(mContext, NewLiveMatchActivity.class).putExtra("key", products.get(position).f_key).putExtra("type", Integer.parseInt("0")).putExtra("team1", products.get(position).t1_short_name).putExtra("team2", products.get(position).t2_short_name).putExtra("flag1", products.get(position).team1_flag).putExtra("flag2", products.get(position).team2_flag).putExtra("team1_full", products.get(position).team1).putExtra("team2_full", products.get(position).team2).putExtra("status", "2").putExtra("matchStatus","result").putExtra("comment",products.get(position).result)
                                .putExtra("score1","hide").putExtra("over1","hide")
                                .putExtra("score2","hide").putExtra("over2","hide"));
                    }*/
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return products == null ? 0:products.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public String returnDate(String input){
        String goal = null;
        try{
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
            goal = outFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return goal;
    }

    public String returnDateone(String input){
        String goal = null;
        try{
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("dd MMMM");
            goal = outFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return goal;
    }

    public String returnTime(String _24HourTime){
        SimpleDateFormat _24HourSDF;
        SimpleDateFormat _12HourSDF = null;
        Date _24HourDt = null;
        try {
            _24HourSDF = new SimpleDateFormat("HH:mm");
            _12HourSDF = new SimpleDateFormat("hh:mm a");
            _24HourDt = _24HourSDF.parse(_24HourTime);
            // System.out.println(_24HourDt);
            // System.out.println(_12HourSDF.format(_24HourDt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _12HourSDF.format(_24HourDt);
    }

}
