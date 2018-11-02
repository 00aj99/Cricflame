package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.TeamWiseModel;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.ScheduleDetail;

import java.util.ArrayList;

public class TeamWiseResultAdapter extends RecyclerView.Adapter<TeamWiseResultAdapter.ViewHolder> {

    public ArrayList<TeamWiseModel> teamNames = new ArrayList<TeamWiseModel>();
    Context context;
    String FragmentInstance;


    public TeamWiseResultAdapter(Context context, ArrayList<TeamWiseModel> teamNames,String FragmentInstance){
        this.context = context;
        this.teamNames = teamNames;
        this.FragmentInstance = FragmentInstance;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_wise_match,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtTeamName.setText(teamNames.get(position).getName());
        Glide.with(context).load(teamNames.get(position).getImage()).into(holder.imvTeam);
        holder.lytMainTeam.setTag(position);
        holder.lytMainTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , ScheduleDetail.class);
                i.putExtra("tour_name",teamNames.get(Integer.parseInt(view.getTag().toString())).getName());
                i.putExtra("Type",FragmentInstance);
                i.putExtra("data",3);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamNames == null ? 0:teamNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTeamName;
        ImageView imvTeam;
        RelativeLayout lytMainTeam;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTeamName = itemView.findViewById(R.id.txt_team_name);
            imvTeam = itemView.findViewById(R.id.imv_team);
            lytMainTeam = itemView.findViewById(R.id.lyt_main_team);
        }
    }
}
