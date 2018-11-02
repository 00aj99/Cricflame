package com.cricflame.cricflame.Ranking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Ranking.Model.Ranking;

import java.util.ArrayList;

public class TeamsRankingAdapter extends RecyclerView.Adapter<TeamsRankingAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Ranking> list;
    public TeamsRankingAdapter(Context context,ArrayList<Ranking> list) {
        this.mContext = context;
        this.list = list;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_team_single_layout, parent, false);
        MyViewHolder myholder = new MyViewHolder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position == 0){
            holder.Main_Lay.setBackground(mContext.getResources().getDrawable(R.drawable.series_main_back));
        }

        holder.point.setText(list.get(position).point);
        holder.country.setText(list.get(position).name);
        holder.rating.setText(list.get(position).ratings);
        holder.matches.setText(list.get(position).matches);

        if(list.get(position).images.equalsIgnoreCase("")){

        }else{
            Glide.with(mContext).load(list.get(position).images).into(holder.image);
        }

        holder.rank.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rating,rank,point,country,matches;
        public ImageView image;
        LinearLayout Main_Lay;
        public MyViewHolder(View itemView) {
            super(itemView);

            rank = (TextView)itemView.findViewById(R.id.rank);
            rating = (TextView)itemView.findViewById(R.id.rating);
            point = (TextView)itemView.findViewById(R.id.point);
            country = (TextView)itemView.findViewById(R.id.country);
            image = (ImageView)itemView.findViewById(R.id.image);
            matches = itemView.findViewById(R.id.match);
            Main_Lay = itemView.findViewById(R.id.layout_team);

        }
    }
}