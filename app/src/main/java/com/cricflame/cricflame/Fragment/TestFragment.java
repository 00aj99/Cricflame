package com.cricflame.cricflame.Fragment;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.News.EndlessRecyclerOnScrollListener;
import com.cricflame.cricflame.News.NewsActivity;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Ranking.Model.Ranking;
import com.cricflame.cricflame.Ranking.adapter.TeamsRankingAdapter;
import com.cricflame.cricflame.Ranking.adapter.TestRankingAdapter;
import com.google.android.exoplayer2.Player;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class TestFragment extends Fragment {

    private DatabaseReference mRootref;
    private DatabaseReference childref;
    RelativeLayout Select_Bat_All_lay;
    public ArrayList<Ranking> list = new ArrayList<>();
    private TeamsRankingAdapter testAdapter;
    TestRankingAdapter testRankingAdapter;
    private TextView btnbat, btnbowl, btnall;
    private RecyclerView rv_ranking;
    String Gender,Type,ConvertGender,Format,PlayerType;
    LoadingDialog loadingDialog;
    int currentItem = 0;
    String ClickType;
    private int mPostsPerPage = 20;
    Boolean isViewShown = false;
    View view = null;
    boolean visibleToUser;
    RequestQueue requestQueue;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            mRootref = FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();
            loadingDialog = new LoadingDialog(getActivity());
            Intent intent= getActivity().getIntent();
            Bundle bundle=intent.getExtras();
            Gender = bundle.getString("gender");
            Type = bundle.getString("type");
            Format = bundle.getString("format");
            PlayerType = bundle.getString("playertype");
            ConvertGender = firstLetterCaps(Gender);
            requestQueue = Volley.newRequestQueue(getActivity());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try{
            if(Type.equalsIgnoreCase("teams")){
                view =inflater.inflate(R.layout.fragment_test, container, false);
            }else if(Type.equalsIgnoreCase("Players")){
                view =inflater.inflate(R.layout.fragment_player_ranking, container, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //init(view);
        return view;
    }

    public void init(View view) {
        try{

            rv_ranking = (RecyclerView) view.findViewById(R.id.rv_ranking);
            btnbat = (TextView) view.findViewById(R.id.btnbat);
            btnbowl = (TextView) view.findViewById(R.id.btnbowl);
            btnall = (TextView) view.findViewById(R.id.btnAll);

            if (Type.equalsIgnoreCase("teams")) {

                testAdapter = new TeamsRankingAdapter(getActivity(), list);
                rv_ranking.setAdapter(testAdapter);
                testAdapter.notifyDataSetChanged();
                final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                rv_ranking.setLayoutManager(mLayoutManager);
                rv_ranking.setHasFixedSize(false);
                rv_ranking.setItemAnimator(new DefaultItemAnimator());
                getTeamTestRanking();
            } else if (Type.equalsIgnoreCase("Players")) {

                if (PlayerType.equalsIgnoreCase("Batsman")) {
                    currentItem = 0;
                    ClickType = "Batsman";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager);
                    //rv_ranking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_ranking.setHasFixedSize(false);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });

                    btnbat.setBackgroundResource(R.drawable.round_button_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnall.setBackgroundResource(R.drawable.round_unselected_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnbowl.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerTestRanking();
                } else if (PlayerType.equalsIgnoreCase("Bowler")) {
                    currentItem = 0;
                    ClickType = "Bowler";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager1);
                    //rv_ranking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_ranking.setHasFixedSize(false);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager1) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });
                    btnbowl.setBackgroundResource(R.drawable.round_button_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnbat.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnall.setBackgroundResource(R.drawable.round_unselected_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerBowlerTestRanking();
                } else if (PlayerType.equalsIgnoreCase("ALLROUNDER")) {
                    currentItem = 0;
                    ClickType = "Allrounder";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager);
                    //rv_ranking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_ranking.setHasFixedSize(false);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });
                    btnall.setBackgroundResource(R.drawable.round_button_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnbat.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnbowl.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerAllRounderTestRanking();
                } else {
                    currentItem = 0;
                    ClickType = "Batsman";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager);
                    //rv_ranking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_ranking.setHasFixedSize(false);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });


                    btnbat.setBackgroundResource(R.drawable.round_button_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnbowl.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnall.setBackgroundResource(R.drawable.round_unselected_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerTestRanking();
                }
            }


            btnbat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.clear();
                    currentItem = 0;
                    ClickType = "Batsman";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager);
                    //rv_ranking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_ranking.setHasFixedSize(false);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });
                    btnbat.setBackgroundResource(R.drawable.round_button_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnbowl.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnall.setBackgroundResource(R.drawable.round_unselected_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerTestRanking();
                }
            });

            btnbowl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.clear();
                    currentItem = 0;
                    ClickType = "Bowler";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager1);
                    rv_ranking.setHasFixedSize(false);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager1) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });
                    btnbowl.setBackgroundResource(R.drawable.round_button_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnbat.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnall.setBackgroundResource(R.drawable.round_unselected_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerBowlerTestRanking();
                }
            });

            btnall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.clear();
                    currentItem = 0;
                    ClickType = "Allrounder";
                    testRankingAdapter = new TestRankingAdapter(getActivity(), list);
                    rv_ranking.setAdapter(testRankingAdapter);
                    testRankingAdapter.notifyDataSetChanged();
                    final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_ranking.setLayoutManager(mLayoutManager1);
                    rv_ranking.setItemAnimator(new DefaultItemAnimator());
                    rv_ranking.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager1) {
                        @Override
                        public void onLoadMore(int current_page) {
                            loadMoreData();
                        }
                    });
                    btnall.setBackgroundResource(R.drawable.round_button_green);
                    btnall.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btnbat.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbat.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    btnbowl.setBackgroundResource(R.drawable.round_unselected_green);
                    btnbowl.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                    getPlayerAllRounderTestRanking();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void getTeamTestRanking(){
        loadingDialog.show();
        childref = mRootref.child("IccRanking").child(ConvertGender).child("Team").child("Secondoryscreen").child("TEST");
        childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    list.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        list.add(snapshot.getValue(Ranking.class));
                        testAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getPlayerTestRanking(){
        loadingDialog.show();
        childref =  mRootref.child("IccRanking").child(ConvertGender).child("Players").child("Secondoryscreen").child("TEST").child("BATSMAN");
        childref.orderByKey()
                .startAt(String.valueOf(currentItem))
                .limitToFirst(mPostsPerPage)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //list.clear();
                        try{
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                list.add(snapshot.getValue(Ranking.class));
                                testRankingAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if(loadingDialog.isShowing())
                            loadingDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    public void getPlayerBowlerTestRanking(){
        loadingDialog.show();
        childref = mRootref.child("IccRanking").child(ConvertGender).child("Players").child("Secondoryscreen").child("TEST").child("BOWLER");
        childref.orderByKey()
                .startAt(String.valueOf(currentItem))
                .limitToFirst(mPostsPerPage)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // list.clear();
                        try{
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                list.add(snapshot.getValue(Ranking.class));
                                testRankingAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if(loadingDialog.isShowing())
                            loadingDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getPlayerAllRounderTestRanking(){
        loadingDialog.show();
        childref = mRootref.child("IccRanking").child(ConvertGender).child("Players").child("Secondoryscreen").child("TEST").child("ALLROUNDER");
        childref.orderByKey()
                .startAt(String.valueOf(currentItem))
                .limitToFirst(mPostsPerPage)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //list.clear();
                        try{
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                list.add(snapshot.getValue(Ranking.class));
                                testRankingAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if(loadingDialog.isShowing())
                            loadingDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    static public String firstLetterCaps ( String data )
    {
        String firstLetter = data.substring(0,1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }


    public void onResume() {
        super.onResume();
    }

    public void onStart() {
        super.onStart();
        if (this.visibleToUser) {
            onVisible();
        }
    }

    public void onStop() {
        super.onStop();
        if (this.visibleToUser) {
            onInvisible();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.visibleToUser = isVisibleToUser;
        if (!isResumed()) {
            return;
        }
        if (this.visibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    private void onInvisible() {
    }

    public void onVisible() {
        if(list==null || list.size()==0){
            init(view);
        }

    }

    private void loadMoreData() {
        currentItem = currentItem+20;
        if(ClickType.equalsIgnoreCase("Batsman")){
            getPlayerTestRanking();
        }else if(ClickType.equalsIgnoreCase("Bowler")){
            getPlayerBowlerTestRanking();
        }else{
            getPlayerAllRounderTestRanking();
        }

    }


}