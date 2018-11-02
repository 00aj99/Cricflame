package com.cricflame.cricflame.Adapter;

/**
 * Created by webore on 12/15/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Bhoopendra on 06-07-2016.
 */
    public class Tour_Adapter extends RecyclerView.Adapter<Tour_Adapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<TourData> products;
        String textAlready = "0";
        TourData tourData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView MatchDate,SeriesName,MatchName,Team_One_name,Team_Two_name,Match_Time,Venue;
            ImageView Team_One_Flag, Team_Two_Flag;
            public MyViewHolder(View view) {
                super(view);
                MatchDate = itemView.findViewById(R.id.month);
                SeriesName = itemView.findViewById(R.id.series_name);
                MatchName = itemView.findViewById(R.id.match_name);
                Team_One_name = itemView.findViewById(R.id.team_one_name);
                Team_Two_name = itemView.findViewById(R.id.team_two_name);
                Team_One_Flag = itemView.findViewById(R.id.flag_team_one);
                Team_Two_Flag = itemView.findViewById(R.id.flag_team_two);
                Match_Time = itemView.findViewById(R.id.match_time);
                Venue = itemView.findViewById(R.id.match_venue);
            }
        }
        public Tour_Adapter(Context mContext, ArrayList<TourData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }
        @Override
        public Tour_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.international_match_view, parent, false);


            return new Tour_Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Tour_Adapter.MyViewHolder holder, int position) {
           tourData = products.get(position);
          /*  holder.Id.setText(products.get(position).getId());
            holder.Name.setText(products.get(position).getLeague_name());
            if (products.get(position).getMonthId().equals("0")){
                holder.month.setText(products.get(position).getMonth());
            }
            else{
                holder.month.setVisibility(View.GONE);
            }

            holder.TourDate.setText(products.get(position).getStrat_date()+" - "+products.get(position).getEnd_date());*/

          try{

              String toCompare = tourData.getDate();
              if(textAlready.equalsIgnoreCase(toCompare)){
                  holder.MatchDate.setVisibility(View.GONE);
              }else{
                  holder.MatchDate.setVisibility(View.VISIBLE);
                  textAlready = tourData.getDate();
                  holder.MatchDate.setText(returnDate(tourData.getDate())+", "+returnDateone(tourData.getDate()));

              }

              // holder.MatchDate.setText(returnDate(matchdate));
              holder.SeriesName.setText(tourData.getSeriesName());
              holder.MatchName.setText(tourData.getMatchName());

              try{
                  if(tourData.getTeam_One_Flag().equalsIgnoreCase("")){

                  }else{
                      Glide.with(mContext).load(tourData.getTeam_One_Flag()).into(holder.Team_One_Flag);
                  }
                  if(tourData.getTeam_Two_Flag().equalsIgnoreCase("")){

                  }else{
                      Glide.with(mContext).load(tourData.getTeam_Two_Flag()).into(holder.Team_Two_Flag);
                  }


              }catch (Exception e){
                  e.printStackTrace();
              }


              holder.Team_One_name.setText(tourData.getTeam_One_Name());
              holder.Team_Two_name.setText(tourData.getTeam_Two_Name());
              holder.Venue.setText(tourData.getMatch_Venue());
              holder.Match_Time.setText(returnTime(tourData.getMatchTime()));


          }catch (Exception e){
              e.printStackTrace();
          }


        }


        @Override
        public int getItemCount() {
            return products.size();
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



