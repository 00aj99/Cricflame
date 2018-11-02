package com.cricflame.cricflame.LiveLine1.Commentries;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricflame.cricflame.R;

import java.util.List;

public class CommentaryAdapter extends RecyclerView.Adapter<CommentaryAdapter.ViewHolder>{

    Context mContext;
    List<CommentryDataPicker> commentary_list;
    CommentryDataPicker cdp;
    Boolean flag = false;

    public CommentaryAdapter(Context mContext, List<CommentryDataPicker> commentary_list) {
        this.mContext = mContext;
        this.commentary_list = commentary_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentary_list_single,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try{
            cdp = commentary_list.get(position);
            holder.over.setText("Over: "+cdp.over);
            holder.rate.setText("Rate: "+cdp.rate);
            holder.ssn.setText("SSN: "+cdp.ssn);
            holder.status.setText("Status: "+cdp.status);
            holder.score.setText("Score:"+cdp.score);
            holder.fav_team.setText("FAV: "+cdp.fav_team);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return commentary_list ==null?0:commentary_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView over,rate,status,ssn,fav_team,score;
        public ViewHolder(View itemView) {
            super(itemView);
            over = (TextView) itemView.findViewById(R.id.over);
            rate = (TextView) itemView.findViewById(R.id.rate);
            status = (TextView) itemView.findViewById(R.id.status);
            ssn = (TextView) itemView.findViewById(R.id.ssn);
            score = (TextView) itemView.findViewById(R.id.score);
            fav_team = (TextView) itemView.findViewById(R.id.fav_team);
        }
    }
}


