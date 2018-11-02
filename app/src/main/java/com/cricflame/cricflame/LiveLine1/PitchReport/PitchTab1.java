package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;
import java.util.ArrayList;
public class PitchTab1 extends Fragment {

    private RecyclerView rv_pitch_report;
    public ArrayList<Stats> stats_list = new ArrayList<>();
    private PitchReportAdapter mAdapter;
    private DatabaseReference mRootref;
    private DatabaseReference childref;
    String Key;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Key = getActivity().getIntent().getExtras().getString("key");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pitch_tab_layout, container, false);
        try{
            mRootref= FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();

        init(view);


        SnapHelper mSnapHelper1 = new PagerSnapHelper();
        mSnapHelper1.attachToRecyclerView(rv_pitch_report);
        mAdapter = new PitchReportAdapter(getActivity(), stats_list);
        rv_pitch_report.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        rv_pitch_report.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_pitch_report.setHasFixedSize(false);
        rv_pitch_report.setItemAnimator(new DefaultItemAnimator());
        childref = mRootref.child("stats").child(Key);
        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stats_list.clear();
                try{
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    //if(dataSnapshot.getValue()!=null){
                        stats_list.add(snapshot.getValue(Stats.class));
                        mAdapter.notifyDataSetChanged();
                    }

                  //  }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ItemClickSupport.addTo(rv_pitch_report)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        mAdapter = (PitchReportAdapter) recyclerView.getAdapter();
                        Stats product = (Stats) mAdapter.getItem(position);
                        Intent intent = new Intent(getActivity(), StatsDetailsActivity.class);
                        intent.putExtra("image",product.image_name);
                        intent.putExtra("heading",product.heading);
                        intent.putExtra("description",product.news);
                        intent.putExtra("date",product.date);
                        startActivity(intent);

                    }
                });

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    public void init(View view) {
        rv_pitch_report = (RecyclerView) view.findViewById(R.id.rv_pitch_report);
    }

    class PitchReportAdapter extends RecyclerView.Adapter<PitchReportAdapter.MyViewHolder> {
        Context context;
        ArrayList<Stats> stats_list = new ArrayList<Stats>();


        public PitchReportAdapter(Context context, ArrayList<Stats> leavelist) {
            this.context = context;
            this.stats_list = leavelist;
        }

        @Override
        public PitchReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout_view, parent, false);
            PitchReportAdapter.MyViewHolder myholder = new PitchReportAdapter.MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final PitchReportAdapter.MyViewHolder holder, int position) {

            if(position%2==0){
                holder.rltStats.setBackground(context.getResources().getDrawable(R.drawable.transparent));
            }else  holder.rltStats.setBackgroundColor(context.getResources().getColor(R.color.transparent_green));

            holder.heading.setText(stats_list.get(position).heading);
            holder.date.setText(stats_list.get(position).date);
            holder.description.setText(Html.fromHtml(stats_list.get(position).news));
            Glide.with(context).load(Global.STATS_URL + stats_list.get(position).image_name).into(holder.image_view);
        }

        @Override
        public int getItemCount() {
            return stats_list == null ? 0 : stats_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView heading, date, description;
            RelativeLayout rltStats;
            ImageView image_view;//,country_logo1,country_logo2


            public MyViewHolder(View view) {
                super(view);

                heading = (TextView) view.findViewById(com.cricflame.cricflame.R.id.title);
                date = (TextView) view.findViewById(com.cricflame.cricflame.R.id.duration);
                description = (TextView) view.findViewById(com.cricflame.cricflame.R.id.description);
                image_view = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.news_image);
                rltStats = (RelativeLayout) view.findViewById(R.id.rlt_stats);
            }
        }

        public Object getItem(int location) {
            return stats_list.get(location);
        }
    }
}
