package com.cricflame.cricflame.Ranking.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Ranking.Model.Ranking;
import com.cricflame.cricflame.Ranking.adapter.TestRankingAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BolwerRankFragment extends Fragment {


    private DatabaseReference mRootref;
    private DatabaseReference childref;

    public ArrayList<Ranking> list = new ArrayList<>();
    private TestRankingAdapter testAdapter;
    private TextView btnTest,btnT20,btnOdi;
    private RecyclerView rv_ranking;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootref = FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();
    }

    public BolwerRankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_rank_batsman, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        rv_ranking = (RecyclerView) view.findViewById(R.id.rv_ranking);
        btnTest = (TextView) view.findViewById(R.id.btnTest);
        btnT20 = (TextView) view.findViewById(R.id.btnT20);
        btnOdi = (TextView) view.findViewById(R.id.btnOdi);


        testAdapter = new TestRankingAdapter(getActivity(), list);
        rv_ranking.setAdapter(testAdapter);
        testAdapter.notifyDataSetChanged();
        rv_ranking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_ranking.setHasFixedSize(false);
        rv_ranking.setItemAnimator(new DefaultItemAnimator());

        btnTest.setTextColor(getResources().getColor(R.color.green));
        btnTest.setBackgroundColor(Color.WHITE);
        btnT20.setTextColor(Color.WHITE);
        btnT20.setBackgroundColor(getResources().getColor(R.color.green));
        btnOdi.setTextColor(Color.WHITE);
        btnOdi.setBackgroundColor(getResources().getColor(R.color.green));

        getTestRanking();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest.setTextColor(getResources().getColor(R.color.green));
                btnTest.setBackgroundColor(Color.WHITE);
                btnT20.setTextColor(Color.WHITE);
                btnT20.setBackgroundColor(getResources().getColor(R.color.green));
                btnOdi.setTextColor(Color.WHITE);
                btnOdi.setBackgroundColor(getResources().getColor(R.color.green));
                getTestRanking();
            }
        });
        btnOdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOdi.setTextColor(getResources().getColor(R.color.green));
                btnOdi.setBackgroundColor(Color.WHITE);
                btnT20.setTextColor(Color.WHITE);
                btnT20.setBackgroundColor(getResources().getColor(R.color.green));
                btnTest.setTextColor(Color.WHITE);
                btnTest.setBackgroundColor(getResources().getColor(R.color.green));
                getOdiRanking();
            }
        });
        btnT20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnT20.setTextColor(getResources().getColor(R.color.green));
                btnT20.setBackgroundColor(Color.WHITE);
                btnOdi.setTextColor(Color.WHITE);
                btnOdi.setBackgroundColor(getResources().getColor(R.color.green));
                btnTest.setTextColor(Color.WHITE);
                btnTest.setBackgroundColor(getResources().getColor(R.color.green));
                getT20Ranking();
            }
        });
    }

    public void getTestRanking(){

        childref = mRootref.child("ICC_Rankings").child("Top Bowlers").child("Bowler Rankings - Test");
        childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(Ranking.class));
                    testAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getOdiRanking(){

        childref = mRootref.child("ICC_Rankings").child("Top Bowlers").child("Bowler Rankings - ODI");
        childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(Ranking.class));
                    testAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getT20Ranking(){

        childref = mRootref.child("ICC_Rankings").child("Top Bowlers").child("Bowler Rankings - T20");
        childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(Ranking.class));
                    testAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
