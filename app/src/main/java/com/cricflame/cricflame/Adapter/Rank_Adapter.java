package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cricflame.cricflame.ICC_Ranking_Details;
import com.cricflame.cricflame.Model.Rank_Main_Model;
import com.cricflame.cricflame.R;

import java.util.List;

public class Rank_Adapter extends RecyclerView.Adapter<Rank_Adapter.MyViewHolder> {

    List<Rank_Main_Model> Header_list;
    Context context;
    Rank_Main_Model rank_main_model;
    Rank_Sub_Adapter rank_sub_adapter;

    public Rank_Adapter(Context context,List<Rank_Main_Model> Header_list){
        this.context = context;
        this.Header_list = Header_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            try{
                rank_main_model  = Header_list.get(position);
                holder.ViewAll.setTag(position);
                holder.Header.setText(rank_main_model.getHeader());
                holder.Lists.setHasFixedSize(true);
                rank_sub_adapter = new Rank_Sub_Adapter(context,rank_main_model.getRanking_List());
                holder.Lists.setLayoutManager(new GridLayoutManager(context,3));
                holder.Lists.setAdapter(rank_sub_adapter);
                holder.Lists.setNestedScrollingEnabled(false);


            holder.ViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ICC_Ranking_Details.class);
                    i.putExtra("gender",Header_list.get(Integer.parseInt(view.getTag().toString())).getGender());
                    i.putExtra("type",Header_list.get(Integer.parseInt(view.getTag().toString())).getType());
                    i.putExtra("format",Header_list.get(Integer.parseInt(view.getTag().toString())).getFormat());
                    i.putExtra("playertype","");
                    context.startActivity(i);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return Header_list == null ? 0 : Header_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Header;
        RecyclerView Lists;
        Button ViewAll;
        public MyViewHolder(View itemView) {
            super(itemView);

            Header = itemView.findViewById(R.id.header_txt);
            Lists = itemView.findViewById(R.id.item_rank_recycler);
            ViewAll = itemView.findViewById(R.id.button_view);
        }
    }

    public void refreshRanks(  List<Rank_Main_Model> Header_list) {
        this.Header_list.clear();
        this.Header_list.addAll(Header_list);
        notifyDataSetChanged();
    }
}
