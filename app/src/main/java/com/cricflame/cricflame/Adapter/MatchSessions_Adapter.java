package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricflame.cricflame.Model.CricFlame_Session;
import com.cricflame.cricflame.R;

import java.util.List;

public class MatchSessions_Adapter extends RecyclerView.Adapter<MatchSessions_Adapter.MyViewHolder> {

    Context context;
    List<CricFlame_Session> Sessionlist;
    CricFlame_Session session;

    public MatchSessions_Adapter(Context context, List<CricFlame_Session> Sessionlist){
        this.context = context;
        this.Sessionlist = Sessionlist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        session = Sessionlist.get(position);

        holder.Over.setText(session.getOver()+" Overs Run"+" ["+session.getRate_Team()+"]");
        holder.RunsScored.setText("Runs Scored : "+session.getRunScored());
        holder.Open.setText("Open: "+session.getOpen());
        holder.Min.setText("Min: "+session.getMin());
        holder.Max.setText("Max: "+session.getMax());
    }

    @Override
    public int getItemCount() {
        return Sessionlist == null ? 0 : Sessionlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Over, RunsScored,Open , Min, Max;
        public MyViewHolder(View itemView) {
            super(itemView);

            Over = itemView.findViewById(R.id.session_overs);
            RunsScored = itemView.findViewById(R.id.sessionLeft);
            Open = itemView.findViewById(R.id.sessionExtraOpen);
            Min = itemView.findViewById(R.id.sessionExtraMin);
            Max = itemView.findViewById(R.id.sessionExtraMax);
        }
    }
}
