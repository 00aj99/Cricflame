package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.LiveLineActivity2;
import com.cricflame.cricflame.MainActivity;
import com.cricflame.cricflame.Model.LiveMatchDetails;
import com.cricflame.cricflame.NewLiveMatchActivity;
import com.cricflame.cricflame.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class LiveLineMatchAdapter extends RecyclerView.Adapter<LiveLineMatchAdapter.ViewHolder> {
    Context context;
    ArrayList<LiveMatchDetails> liveMatchList;

    public LiveLineMatchAdapter(Context context,ArrayList<LiveMatchDetails> liveMatchList){
        this.context = context;
        this.liveMatchList = liveMatchList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_live_match_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final LiveMatchDetails live = liveMatchList.get(position);

        if(position%2==0){
            holder.lytTeams.setBackground(context.getResources().getDrawable(R.drawable.series_back));
        }else  holder.lytTeams.setBackground(context.getResources().getDrawable(R.drawable.new_series));

        if (!live.getFlag1().trim().equals("")) {
            //flag1.setImageURI(Uri.parse(live.get(position).getFlag1()));
            Glide.with(context).load(live.getFlag1()).into(holder.imvTeam1Flag);
        }
        if (!live.getFlag2().trim().equals("")) {
            //flag2.setImageURI(Uri.parse(live.getFlag2()));
            Glide.with(context).load(live.getFlag2()).into(holder.imvTeam2Flag);
        }
        holder.txtVenueView.setText(live.getMatch_number());
        if (live.getStatus().equals("0")) {
            holder.txtMatchCardStatus.setText("UPCOMING");
            holder.txtTeam1NameShort.setText(live.getT1());
            holder.txtTeam2NameShort.setText(live.getT2());
            holder.txtTeam1NameFull.setText(live.getTeam1());
            holder.txtTeam2NameFull.setText(live.getTeam2());
        } else {
            if (live.getStatus().equals("1")) {
                holder.txtMatchCardStatus.setText("LIVE");
            } else {
                holder.txtMatchCardStatus.setText("RESULT");
            }
            holder.txtTeam1NameShort.setText(live.getT1());
            holder.txtTeam2NameShort.setText(live.getT2());
            holder.txtTeam1NameFull.setText(live.getTeam1());
            holder.txtTeam2NameFull.setText(live.getTeam2());
            }

        holder.lytTeams.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Toast.makeText(MainActivity.this, "Start Live Line",Toast.LENGTH_LONG).show();
                Bundle analyticBundle = new Bundle();
                analyticBundle.putString(FirebaseAnalytics.Param.ITEM_ID, live.getKey());
                analyticBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "match");
                context.startActivity(new Intent(context, NewLiveMatchActivity.class).putExtra("key", live.getKey()).putExtra("type", Integer.parseInt(live.getType())).putExtra("team1", live.getT1()).putExtra("team2", live.getT2()).putExtra("flag1", live.getFlag1()).putExtra("flag2", live.getFlag2()).putExtra("team1_full", live.getTeam1()).putExtra("team2_full", live.getTeam2()).putExtra("status", live.getStatus()).putExtra("matchStatus",holder.txtMatchCardStatus.getText().toString().trim()).putExtra("score1",live.getScore() + "/" + live.getWicket()).putExtra("over1",ballsToOver(Integer.parseInt(live.getBallsdone())))
                        .putExtra("score2",live.getScore2() + "/" + live.getWicket2()).putExtra("over2",ballsToOver(Integer.parseInt(live.getBallsdone2())))
                .putExtra("comment",live.getComment())
                        .putExtra("score1",live.getScore() + "/" + live.getWicket()).putExtra("over1",ballsToOver(Integer.parseInt(live.getBallsdone())))
                        .putExtra("score2",live.getScore2() + "/" + live.getWicket2()).putExtra("over2",ballsToOver(Integer.parseInt(live.getBallsdone2())))
                        .putExtra("id",live.getId())
                        .putExtra("scorecardid",live.getScoreCardId())
                .putExtra("ISCricBuzz",live.getIsCricBuzz())
                        .putExtra("EventID",live.getEventId())
                        .putExtra("MarketId",live.getMarket_Id())
                        .putExtra("i1",live.getI1())
                        .putExtra("i2",live.getI2())
                        .putExtra("i3",live.getI3())
                        .putExtra("oddsType",live.getOddstype())
                        .putExtra("sessionType",live.getSessionType()));
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static String ballsToOver(int balls) {
        return (balls / 6) + "." + (balls % 6);
    }

    @Override
    public int getItemCount() {
        return liveMatchList == null ? 0: liveMatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lytTeams;
        ImageView imvTeam1Flag, imvTeam2Flag;
        TextView txtTeam1NameShort, txtTeam1NameFull, txtTeam2NameShort, txtTeam2NameFull,txtVenueView,txtMatchCardStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            lytTeams = (LinearLayout) itemView.findViewById(R.id.lyt_teams);
            imvTeam1Flag = (ImageView) itemView.findViewById(R.id.imv_team1_flag);
            imvTeam2Flag = (ImageView) itemView.findViewById(R.id.imv_team2_flag);
            txtTeam1NameFull = (TextView) itemView.findViewById(R.id.txt_team1_name_full);
            txtTeam1NameShort = (TextView) itemView.findViewById(R.id.txt_team1_name_short);
            txtTeam2NameFull = (TextView) itemView.findViewById(R.id.txt_team2_name_full);
            txtTeam2NameShort = (TextView) itemView.findViewById(R.id.txt_team2_name_short);
            txtVenueView = (TextView) itemView.findViewById(R.id.txt_venue_view);
            txtMatchCardStatus = (TextView) itemView.findViewById(R.id.txt_match_card_status);
        }
    }
}
