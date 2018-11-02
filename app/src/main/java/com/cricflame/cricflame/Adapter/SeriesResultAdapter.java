package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.ScheduleDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SeriesResultAdapter extends RecyclerView.Adapter<SeriesResultAdapter.MyViewHolder> {
    public ArrayList<SeriesWise_Model> SereisList;
    Context context;
    String textAlready = "0";
    String FragmentInstance;

    public SeriesResultAdapter(Context context, ArrayList<SeriesWise_Model> SereisList,String FragmentInstance){
        this.context = context;
        this.SereisList = SereisList;
        this.FragmentInstance = FragmentInstance;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_wise_match,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try{
            if(position%2==0){
                holder.Main_Lay.setBackground(context.getResources().getDrawable(R.drawable.series_back));
            }else  holder.Main_Lay.setBackground(context.getResources().getDrawable(R.drawable.new_series));

            if(SereisList.get(position).date_time.equalsIgnoreCase("hide Date")){
                holder.DateTime.setVisibility(View.GONE);
                //holder.TimePeriod.setVisibility(View.GONE);
            }else{
               // holder.TimePeriod.setVisibility(View.VISIBLE);
                String date = null;
                String time = null;
                if(SereisList.get(position).date_time.length()>10){
                    String[] splited =  SereisList.get(position).date_time.split("\\s+");
                    date=splited[0];
                    time=splited[1];
                    holder.DateTime.setVisibility(View.VISIBLE);
                    holder.DateTime.setText(returnDateone(date));
                }else{
                    time = SereisList.get(position).date_time;
                    holder.DateTime.setVisibility(View.GONE);
                }
            }

            holder.TourName.setText(SereisList.get(position).tourname);
            holder.TimePeriod.setText(SereisList.get(position).timeperiod);

            holder.Main_Lay.setTag(position);


            holder.Main_Lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context , ScheduleDetail.class);
                    i.putExtra("tour_name",SereisList.get(Integer.parseInt(view.getTag().toString())).tourname);
                    i.putExtra("Type",FragmentInstance);
                    i.putExtra("data",0);
                    i.putExtra("SeriesId",SereisList.get(Integer.parseInt(view.getTag().toString())).seriesid);
                    context.startActivity(i);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return SereisList == null ? 0 : SereisList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DateTime,TourName,TimePeriod;
        RelativeLayout Main_Lay;
        public MyViewHolder(View itemView) {
            super(itemView);
            DateTime = itemView.findViewById(R.id.month);
            TourName = itemView.findViewById(R.id.series_name);
            TimePeriod = itemView.findViewById(R.id.time_period);
            Main_Lay = itemView.findViewById(R.id.main_click_lay);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public String returnDateone(String input){
        String goal = null;
        try{
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("MMMM yyyy");
            goal = outFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return goal;
    }
}
