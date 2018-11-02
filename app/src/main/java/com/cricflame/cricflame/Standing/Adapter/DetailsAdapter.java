package com.cricflame.cricflame.Standing.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Standing.Model.Standing;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    private Context activity;
    private ArrayList<Standing> Items;
    public DetailsAdapter(Context activity, ArrayList<Standing> Items) {
        this.activity = activity;
        this.Items = Items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_standing_layout, parent, false);
       MyViewHolder myholder = new MyViewHolder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text1.setText(Items.get(position).getName());
        holder.text2.setText(Items.get(position).getPlayed());
        holder.text3.setText(Items.get(position).getWon());
        holder.text4.setText(Items.get(position).getLost());
        holder.text5.setText(Items.get(position).getDraw());
        holder.text6.setText(Items.get(position).getPointScore());
        holder.text7.setText(Items.get(position).getRunRate());
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2,text3,text4,text5,text6,text7;
        public MyViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView)itemView.findViewById(R.id.text1);
            text2 = (TextView)itemView.findViewById(R.id.text2);
            text3 = (TextView)itemView.findViewById(R.id.text3);
            text4 = (TextView)itemView.findViewById(R.id.text4);
            text5 = (TextView)itemView.findViewById(R.id.text5);
            text6 = (TextView)itemView.findViewById(R.id.text6);
            text7 = (TextView)itemView.findViewById(R.id.text7);
        }
    }
}
