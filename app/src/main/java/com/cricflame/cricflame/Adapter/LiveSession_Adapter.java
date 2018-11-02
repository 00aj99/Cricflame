package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricflame.cricflame.LiveLine1.Sessions.Session;
import com.cricflame.cricflame.Model.Session_Model;
import com.cricflame.cricflame.R;

import java.util.List;

public class LiveSession_Adapter extends RecyclerView.Adapter<LiveSession_Adapter.MyViewHolder> {
    Context context;
    List<Session_Model> SessionList;
    Session_Model session_model;

    public LiveSession_Adapter(Context context, List<Session_Model> SessionList){
        this.context = context;
        this.SessionList = SessionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_line_session, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        session_model = SessionList.get(position);

        if(position%2==0){
            holder.ssnBack.setBackground(context.getResources().getDrawable(R.drawable.live_match_highightedbackground));
        }else  holder.ssnBack.setBackground(context.getResources().getDrawable(R.drawable.transparent));

        if(session_model.getStatus().equalsIgnoreCase("Ball Running")){
            holder.Ball_Running_Status.setVisibility(0);
        }else{
            holder.Ball_Running_Status.setVisibility(8);
        }

        holder.RateTeam.setText(session_model.getRateTeam());
        if(session_model.getLineLeft().contains(".")){
            holder.LineLeft.setText(String.valueOf(Math.round(Double.parseDouble(session_model.getLineLeft()))));
        }else{
            holder.LineLeft.setText(session_model.getLineLeft());
        }
        if(session_model.getPriceLeft().contains(".")){
            holder.PriceLeft.setText(String.valueOf(Math.round(Double.parseDouble(session_model.getPriceLeft()))));
        }else{
            holder.PriceLeft.setText(session_model.getPriceLeft());
        }

        if(session_model.getPriceRight().contains(".")){
            holder.PriceRight.setText(String.valueOf(Math.round(Double.parseDouble(session_model.getPriceRight()))));
        }else{
            holder.PriceRight.setText(session_model.getPriceRight());
        }

        if(session_model.getLineRight().contains(".")){
            holder.LineRight.setText(String.valueOf(Math.round(Double.parseDouble(session_model.getLineRight()))));
        }else{
            holder.LineRight.setText(session_model.getLineRight());
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


    @Override
    public int getItemCount() {
        return SessionList == null ? 0 : SessionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView RateTeam, LineLeft, PriceLeft, PriceRight, LineRight,Ball_Running_Status;
        RelativeLayout ssnBack;

        public MyViewHolder(View itemView) {
            super(itemView);
            RateTeam = itemView.findViewById(R.id.rateTeam);
            LineLeft = itemView.findViewById(R.id.line_left);
            PriceLeft = itemView.findViewById(R.id.price_left);
            PriceRight = itemView.findViewById(R.id.price_right);
            ssnBack = itemView.findViewById(R.id.ssn_back);
            LineRight = itemView.findViewById(R.id.line_right);
            Ball_Running_Status = itemView.findViewById(R.id.ball_running_status);;
        }
    }
}
