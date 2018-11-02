package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Global;

import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;

import java.util.ArrayList;


public class PitchTab3 extends Fragment{
    private RecyclerView rv_h2h;
    public ArrayList<H2H> h2h_list = new ArrayList<H2H>();
    private head2HeadAdapter mAdapter;

    private FirebaseApp secondApp;
    private FirebaseDatabase secondaryDatabase;
    private DatabaseReference mRootref;
    private DatabaseReference childref;
    String Key;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Key = getActivity().getIntent().getExtras().getString("key");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pitch_tab_layout3, container, false);
        try{
            mRootref= FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();

        init(view);
//        match_info.setText(PitchReport.status);
//        country1.setText(PitchReport.country1);
//        country2.setText(PitchReport.country2);
//        team1_run.setText(PitchReport.team1_score);
//        team2_run.setText(PitchReport.team2_score);
//        Glide.with(getActivity()).load(Global.PHOTO_URL+PitchReport.country1_image).into(country_logo1);
//        Glide.with(getActivity()).load(Global.PHOTO_URL+PitchReport.country2_image).into(country_logo2);

        SnapHelper mSnapHelper1 = new PagerSnapHelper();
        mSnapHelper1.attachToRecyclerView(rv_h2h);
        //getAllStats();
        mAdapter = new head2HeadAdapter(getActivity(), h2h_list);
        rv_h2h.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        rv_h2h.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_h2h.setHasFixedSize(false);
        rv_h2h.setItemAnimator(new DefaultItemAnimator());
        childref = mRootref.child("H2H").child(Key);
        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                h2h_list.clear();
                try{
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        h2h_list.add(snapshot.getValue(H2H.class));
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ItemClickSupport.addTo(rv_h2h)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        mAdapter = (head2HeadAdapter) recyclerView.getAdapter();
                        //AllMatchData product = (AllMatchData) mAdapter.getItem(position);
                    }
                });

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    public void init(View view) {
        rv_h2h = (RecyclerView) view.findViewById(R.id.rv_h2h);
    }


    class head2HeadAdapter extends RecyclerView.Adapter<head2HeadAdapter.MyViewHolder> {
        Context context;
        ArrayList<H2H> stats_list = new ArrayList<H2H>();


        public head2HeadAdapter(Context context, ArrayList<H2H> leavelist) {
            this.context = context;
            this.stats_list = leavelist;
        }

        @Override
        public head2HeadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.h2h_layout_view, parent, false);
            head2HeadAdapter.MyViewHolder myholder = new head2HeadAdapter.MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final head2HeadAdapter.MyViewHolder holder, int position) {


            if(position%2==0){
                holder.h2hLyt.setBackground(context.getResources().getDrawable(R.drawable.series_main_back));
            }else  holder.h2hLyt.setBackground(context.getResources().getDrawable(R.drawable.new_series));

            holder.team1_name.setText(stats_list.get(position).team_name1);
            holder.team2_name.setText(stats_list.get(position).team_name2);
            holder.team1_score.setText("Runs: "+stats_list.get(position).team_score1);
            holder.team2_score.setText("Runs: "+stats_list.get(position).team_score2);


            holder.date.setText(stats_list.get(position).date+": ");
            holder.Result.setText(stats_list.get(position).teamname+" won by "+stats_list.get(position).result);
            if(stats_list.get(position).batwin.equalsIgnoreCase("H")){
                holder.HomeTeam.setVisibility(0);
                holder.AwayTeam.setVisibility(8);
            }else if(stats_list.get(position).batwin.equalsIgnoreCase("A")){
                holder.HomeTeam.setVisibility(8);
                holder.AwayTeam.setVisibility(0);
            }else{
                holder.HomeTeam.setVisibility(0);
                holder.AwayTeam.setVisibility(0);
            }


        }

        @Override
        public int getItemCount() {
            return stats_list == null ? 0 : stats_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView team1_name,team1_score,team1_wicket,team2_name,team2_score,team2_wicket,date,HomeTeam,AwayTeam,Result;
            RelativeLayout h2hLyt;
            public MyViewHolder(View view) {
                super(view);

                date = (TextView) view.findViewById(R.id.date);
                team1_name = (TextView) view.findViewById(com.cricflame.cricflame.R.id.txt_h2h_team1_name);
                team1_score = (TextView) view.findViewById(com.cricflame.cricflame.R.id.txt_h2h_team1_runs);
                team2_name = (TextView) view.findViewById(com.cricflame.cricflame.R.id.txt_h2h_team2_name);
                team2_score = (TextView) view.findViewById(com.cricflame.cricflame.R.id.txt_h2h_team2_runs);
                HomeTeam = view.findViewById(R.id.imv_h2h_home_team);
                AwayTeam = view.findViewById(R.id.imv_h2h_away_team);
                Result = view.findViewById(R.id.h2h_results);
                h2hLyt = view.findViewById(R.id.h2h_lyt);
            }
        }


        public Object getItem(int location) {
            return stats_list.get(location);
        }

    }
}
