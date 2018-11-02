package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.ICC_Ranking_Details;
import com.cricflame.cricflame.MainActivity;
import com.cricflame.cricflame.Model.RankingModel;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Rank_Sub_Adapter extends RecyclerView.Adapter<Rank_Sub_Adapter.MyViewHolder> {
    List<RankingModel> Team_player_list;
    Context context;
    RankingModel rankingModel;


    public Rank_Sub_Adapter(Context context, List<RankingModel> Team_player_list){
        this.context = context;
        this.Team_player_list = Team_player_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_sub,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        rankingModel = Team_player_list.get(position);


        Glide.with(context).load(rankingModel.getFlag()).into(holder.Flag);
        holder.TeamName.setText(rankingModel.getTeamName());
        if(rankingModel.getType().equalsIgnoreCase("teams")){
            holder.Rating.setText("Ratings : "+rankingModel.getRating());
        }else{
            holder.Rating.setText(rankingModel.getRating());
        }

        holder.Format.setText(rankingModel.getPlayerType());

        holder.Main_Lay.setTag(position);


        holder.Main_Lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ICC_Ranking_Details.class);
                i.putExtra("gender",Team_player_list.get(Integer.parseInt(view.getTag().toString())).getGender());
                i.putExtra("type",Team_player_list.get(Integer.parseInt(view.getTag().toString())).getType());
                i.putExtra("format",Team_player_list.get(Integer.parseInt(view.getTag().toString())).getFormatType());
                i.putExtra("playertype",Team_player_list.get(Integer.parseInt(view.getTag().toString())).getPlayerType());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Team_player_list == null ? 0 : Team_player_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TeamName,Rating, Format;
        ImageView Flag;
        LinearLayout Main_Lay;
        public MyViewHolder(View itemView) {
            super(itemView);
            TeamName = itemView.findViewById(R.id.team_name);
            Rating = itemView.findViewById(R.id.rating);
            Format= itemView.findViewById(R.id.format);
            Flag = itemView.findViewById(R.id.flag);
            Main_Lay = itemView.findViewById(R.id.main_lay_rank);
        }
    }

}
