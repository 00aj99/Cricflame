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

/**
 * Created by yashlam on 1/26/2018.
 */

public class TestRankingAdapter extends RecyclerView.Adapter<TestRankingAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Ranking> list;
    public TestRankingAdapter(Context context,ArrayList<Ranking> list) {
        this.mContext = context;
        this.list = list;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_single_layout, parent, false);
        MyViewHolder myholder = new MyViewHolder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(position == 0){
            holder.Main_Lay.setBackground(mContext.getResources().getDrawable(R.drawable.series_main_back));
        }

        holder.name.setText(list.get(position).name);
        holder.country.setText(list.get(position).team);
        holder.point.setText(list.get(position).rating);
        if(list.get(position).flag.equalsIgnoreCase("")){

        }else{
            Glide.with(mContext).load(list.get(position).flag).into(holder.image);
        }

        holder.rank.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public   TextView name,rank,point,country;
        public ImageView image;
        LinearLayout Main_Lay;
        public MyViewHolder(View itemView) {
            super(itemView);

            rank = (TextView)itemView.findViewById(R.id.rank);
            point = (TextView)itemView.findViewById(R.id.point);
            country = (TextView)itemView.findViewById(R.id.country);
            image = (ImageView)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.player);
            Main_Lay = itemView.findViewById(R.id.layout_batsman);
        }
    }

}
