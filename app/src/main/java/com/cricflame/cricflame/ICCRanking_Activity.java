package com.cricflame.cricflame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.cricflame.cricflame.Adapter.Rank_Adapter;
import com.cricflame.cricflame.Adapter.Rank_Sub_Adapter;
import com.cricflame.cricflame.DBHelper.DBHelper;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.Rank_Main_Model;
import com.cricflame.cricflame.Model.RankingModel;
import com.cricflame.cricflame.Ranking.Model.Ranking;
import com.cricflame.cricflame.Util.ConnectionDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ICCRanking_Activity extends AppCompatActivity {
    ImageView men_dropdown_btn;
    RelativeLayout men_layout,Men_top_Recycler;
    LinearLayout mens_ranking;
    ImageView women_dropdown_btn;
    RelativeLayout women_layout;
    LinearLayout womens_ranking;
    Rank_Adapter rank_adapter;
    Rank_Adapter rank_adapter_women;
    RecyclerView MainList_Recycler,Women_Recycler;
    List<Rank_Main_Model> Main_list;
    List<RankingModel> Sub_list;
    List<Rank_Main_Model> Women_Main_List;
    List<RankingModel> Women_Sub_list;
    LoadingDialog loadingDialog;
    ImageView back;
    private DatabaseReference mRootref;
    private DatabaseReference childref;
    int canChangeValue = 0;
    Gson gson;
    String json1;
    DBHelper dbHelper;
    ConnectionDetector cd;
    int flag = 0;
    int flag1 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iccranking_new);
        gson = new Gson();
        dbHelper = new DBHelper(ICCRanking_Activity.this);
        cd = new ConnectionDetector(ICCRanking_Activity.this);
        Men_top_Recycler = findViewById(R.id.recycler_main);
        men_dropdown_btn = (ImageView) findViewById(R.id.mens_dropdown_button);
        women_dropdown_btn = (ImageView) findViewById(R.id.womens_dropdown_button);
        mens_ranking = (LinearLayout) findViewById(R.id.mens_rankings);
        womens_ranking = (LinearLayout) findViewById(R.id.womens_rankings);
        men_layout = (RelativeLayout) findViewById(R.id.animate_men);
        women_layout = (RelativeLayout) findViewById(R.id.animate_women);
        women_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_down);
        womens_ranking.animate().alpha(0.0f).translationY((float) (-this.womens_ranking.getHeight()));
        mens_ranking.animate().alpha(0.0f).translationY((float) (-this.mens_ranking.getHeight()));
        women_layout.setVisibility(8);
        flag1 = 1;
        men_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_down);
        men_layout.setVisibility(8);
        flag = 1;
        MainList_Recycler = findViewById(R.id.mens_ranking_recycler);
        Women_Recycler = findViewById(R.id.womens_ranking_recycler);
        loadingDialog = new LoadingDialog(ICCRanking_Activity.this);
        back = (ImageView) findViewById(R.id.back);
        canChangeValue = 0;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRootref = FirebaseUtils.getSecondaryDatabase(ICCRanking_Activity.this).getReference();

        GetRanking();

    }


 public void GetRanking(){
     try{

         loadingDialog.show();
         Main_list = new ArrayList<Rank_Main_Model>();
         Women_Main_List = new ArrayList<Rank_Main_Model>();
         Women_Sub_list = new ArrayList<RankingModel>();
         //final Rank_Main_Model list = new Rank_Main_Model();
        // final Rank_Main_Model list_women = new Rank_Main_Model();

         if(dbHelper.getFieldValueUser("TeamMen")==null){
           LoadTeamsFirebase(1);
         }else{
             LoadTeamsLocal();
         }

            if(dbHelper.getFieldValueUser("PlayersMen")==null){
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     LoadPlayersFirebase(1);
                 }
             },3000);

            }else{
                LoadPlayersLocal();
            }


         if(dbHelper.getFieldValueUser("TeamWomen")==null){
             LoadWomensTeamsFirebase(1);
         }else{
             LoadWomensTeamsLocal();
         }

         if(dbHelper.getFieldValueUser("PlayerWomen")==null){
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                    LoadWomensPlayersFirebase(1);
                 }
             },3000);
         }else{
             LoadWomensPlayerssLocal();
         }


         MainList_Recycler.setHasFixedSize(true);
         rank_adapter = new Rank_Adapter(ICCRanking_Activity.this,Main_list);
         MainList_Recycler.setLayoutManager(new LinearLayoutManager(ICCRanking_Activity.this, LinearLayoutManager.VERTICAL, false));
         rank_adapter.notifyDataSetChanged();
         MainList_Recycler.setAdapter(rank_adapter);
         MainList_Recycler.setNestedScrollingEnabled(false);









         Women_Recycler.setHasFixedSize(true);
         rank_adapter_women = new Rank_Adapter(ICCRanking_Activity.this,Women_Main_List);
         Women_Recycler.setLayoutManager(new LinearLayoutManager(ICCRanking_Activity.this, LinearLayoutManager.VERTICAL, false));
         rank_adapter_women.notifyDataSetChanged();
         Women_Recycler.setAdapter(rank_adapter_women);
         Women_Recycler.setNestedScrollingEnabled(false);

     }catch (Exception e){
         e.printStackTrace();
     }
 }


 public void LoadTeamsFirebase(final int type){
     final Rank_Main_Model list = new Rank_Main_Model();
     Sub_list = new ArrayList<RankingModel>();
     mRootref.child("IccRanking").child("Men").child("Team").child("Primarysceen").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             json1 = gson.toJson(dataSnapshot.getValue());
             //System.out.print(json1);
             dbHelper.insertFielduser("TeamMen",json1);
             if(type==1){
                 try{
                     JSONObject jObj = new JSONObject(json1);
                     list.setHeader("Teams | Rankings");
                     list.setGender(jObj.getJSONObject("TEST").getString("gender"));
                     list.setType("teams");
                     list.setFormat(jObj.getJSONObject("TEST").getString("type"));
                     for(int i =0; i<3;i++){
                         RankingModel list1 = new RankingModel();
                         list1.setType("teams");
                         if(i==0){
                             list1.setType("teams");
                             list1.setGender(jObj.getJSONObject("TEST").getString("gender"));
                             list1.setFlag(jObj.getJSONObject("TEST").getString("imageprimary"));
                             list1.setTeamName(jObj.getJSONObject("TEST").getString("name"));
                             list1.setRating(jObj.getJSONObject("TEST").getString("ratings"));
                             list1.setFormatType(jObj.getJSONObject("TEST").getString("type"));
                             list1.setPlayerType(jObj.getJSONObject("TEST").getString("type"));
                             Sub_list.add(list1);
                             list.setRanking_List(Sub_list);
                         } if(i==1){
                             list1.setType("teams");
                             list1.setGender(jObj.getJSONObject("ODI").getString("gender"));
                             list1.setFlag(jObj.getJSONObject("ODI").getString("imageprimary"));
                             list1.setTeamName(jObj.getJSONObject("ODI").getString("name"));
                             list1.setRating(jObj.getJSONObject("ODI").getString("ratings"));
                             list1.setFormatType(jObj.getJSONObject("ODI").getString("type"));
                             list1.setPlayerType(jObj.getJSONObject("ODI").getString("type"));
                             Sub_list.add(list1);
                             list.setRanking_List(Sub_list);
                         } if(i==2){
                             list1.setType("teams");
                             list1.setGender(jObj.getJSONObject("T20").getString("gender"));
                             list1.setFlag(jObj.getJSONObject("T20").getString("imageprimary"));
                             list1.setTeamName(jObj.getJSONObject("T20").getString("name"));
                             list1.setRating(jObj.getJSONObject("T20").getString("ratings"));
                             list1.setFormatType(jObj.getJSONObject("T20").getString("type"));
                             list1.setPlayerType(jObj.getJSONObject("T20").getString("type"));
                             Sub_list.add(list1);
                             list.setRanking_List(Sub_list);
                         }


                     }

                     Main_list.add(list);

                 }catch (Exception e){
                     e.printStackTrace();
                 }

             }

         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
 }

 public void LoadTeamsLocal(){
     final Rank_Main_Model list = new Rank_Main_Model();
     Sub_list = new ArrayList<RankingModel>();
     try{
         JSONObject jObj = new JSONObject(dbHelper.getFieldValueUser("TeamMen"));
         list.setHeader("Teams | Rankings");
         list.setGender(jObj.getJSONObject("TEST").getString("gender"));
         list.setType("teams");
         list.setFormat(jObj.getJSONObject("TEST").getString("type"));
         for(int i =0; i<3;i++){
             RankingModel list1 = new RankingModel();
             list1.setType("teams");
             if(i==0){
                 list1.setGender(jObj.getJSONObject("TEST").getString("gender"));
                 list1.setFlag(jObj.getJSONObject("TEST").getString("imageprimary"));
                 list1.setTeamName(jObj.getJSONObject("TEST").getString("name"));
                 list1.setRating(jObj.getJSONObject("TEST").getString("ratings"));
                 list1.setFormatType(jObj.getJSONObject("TEST").getString("type"));
                 list1.setPlayerType(jObj.getJSONObject("TEST").getString("type"));
                 Sub_list.add(list1);
                 list.setRanking_List(Sub_list);
             } if(i==1){
                 list1.setGender(jObj.getJSONObject("ODI").getString("gender"));
                 list1.setFlag(jObj.getJSONObject("ODI").getString("imageprimary"));
                 list1.setTeamName(jObj.getJSONObject("ODI").getString("name"));
                 list1.setRating(jObj.getJSONObject("ODI").getString("ratings"));
                 list1.setFormatType(jObj.getJSONObject("ODI").getString("type"));
                 list1.setPlayerType(jObj.getJSONObject("ODI").getString("type"));
                 Sub_list.add(list1);
                 list.setRanking_List(Sub_list);
             } if(i==2){
                 list1.setGender(jObj.getJSONObject("T20").getString("gender"));
                 list1.setFlag(jObj.getJSONObject("T20").getString("imageprimary"));
                 list1.setTeamName(jObj.getJSONObject("T20").getString("name"));
                 list1.setRating(jObj.getJSONObject("T20").getString("ratings"));
                 list1.setFormatType(jObj.getJSONObject("T20").getString("type"));
                 list1.setPlayerType(jObj.getJSONObject("T20").getString("type"));
                 Sub_list.add(list1);
                 list.setRanking_List(Sub_list);
             }


         }

         Main_list.add(list);

     }catch (Exception e){
         e.printStackTrace();
     }
     if(cd.isConnectingToInternet()){
         LoadTeamsFirebase(0);
     }

 }


 public void LoadPlayersFirebase(final int type){
     mRootref.child("IccRanking").child("Men").child("Players").child("Primarysceen").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             String PlayerJson = gson.toJson(dataSnapshot.getValue());
             dbHelper.insertFielduser("PlayersMen",PlayerJson);
             if(type == 1){
                 try{
                     String Type = "TEST";
                     JSONObject jObj = new JSONObject(PlayerJson);
                     for(int j=0; j<3;j++){
                         final Rank_Main_Model list = new Rank_Main_Model();
                         Sub_list = new ArrayList<RankingModel>();
                         if(j==0){
                             Type = "TEST";
                             list.setHeader("Players | Test");
                             list.setFormat("TEST");
                         }if(j==1){
                             Type = "ODI";
                             list.setHeader("Players | ODI");
                             list.setFormat("ODI");
                         }if(j==2){
                             Type = "T20";
                             list.setHeader("Players | T20");
                             list.setFormat("T20");
                         }

                         list.setGender("Men");
                         list.setType("Players");

                         for(int i =0; i<3;i++){
                             RankingModel list1 = new RankingModel();
                             if(i==0){
                                 list1.setType("Players");
                                 list1.setGender("Men");
                                 list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playerimage"));
                                 list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("name"));
                                 list1.setRating(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("team"));
                                 list1.setFormatType(Type);
                                 list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playertype"));
                                 Sub_list.add(list1);
                                 list.setRanking_List(Sub_list);

                             }
                             if(i==1){
                                 list1.setType("Players");
                                 list1.setGender("Men");
                                 list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playerimage"));
                                 list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("name"));
                                 list1.setRating(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("team"));
                                 list1.setFormatType(Type);
                                 list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playertype"));
                                 Sub_list.add(list1);
                                 list.setRanking_List(Sub_list);

                             }
                             if(i==2){
                                 list1.setType("Players");
                                 list1.setGender("Men");
                                 list1.setFlag(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playerimage"));
                                 list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("name"));
                                 list1.setRating(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("team"));
                                 list1.setFormatType(Type);
                                 list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playertype"));
                                 Sub_list.add(list1);
                                 list.setRanking_List(Sub_list);

                             }
                         }
                         Main_list.add(list);
                     }

                 }catch (Exception e){
                     e.printStackTrace();
                 }


             }

         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
 }

 public void LoadPlayersLocal(){
     try{
         String Type = "TEST";
         JSONObject jObj = new JSONObject(dbHelper.getFieldValueUser("PlayersMen"));
         for(int j=0; j<3;j++){
             final Rank_Main_Model list = new Rank_Main_Model();
             Sub_list = new ArrayList<RankingModel>();

             if(j==0){
                 Type = "TEST";
                 list.setHeader("Players | Test");
                 list.setFormat("TEST");
             }if(j==1){
                 Type = "ODI";
                 list.setHeader("Players | ODI");
                 list.setFormat("ODI");
             }if(j==2){
                 Type = "T20";
                 list.setHeader("Players | T20");
                 list.setFormat("T20");
             }

             list.setGender("Men");
             list.setType("Players");

             for(int i =0; i<3;i++){
                 RankingModel list1 = new RankingModel();
                 if(i==0){
                     list1.setType("Players");
                     list1.setGender("Men");
                     list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playerimage"));
                     list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("name"));
                     list1.setRating(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("team"));
                     list1.setFormatType(Type);
                     list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playertype"));
                     Sub_list.add(list1);
                     list.setRanking_List(Sub_list);

                 }
                 if(i==1){
                     list1.setType("Players");
                     list1.setGender("Men");
                     list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playerimage"));
                     list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("name"));
                     list1.setRating(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("team"));
                     list1.setFormatType(Type);
                     list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playertype"));
                     Sub_list.add(list1);
                     list.setRanking_List(Sub_list);

                 }
                 if(i==2){
                     list1.setType("Players");
                     list1.setGender("Men");
                     list1.setFlag(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playerimage"));
                     list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("name"));
                     list1.setRating(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("team"));
                     list1.setFormatType(Type);
                     list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playertype"));
                     Sub_list.add(list1);
                     list.setRanking_List(Sub_list);

                 }

             }
             Main_list.add(list);
         }

     }catch (Exception e){
         e.printStackTrace();
     }

     if(cd.isConnectingToInternet()){
         LoadPlayersFirebase(0);
     }
 }



    public void LoadWomensTeamsFirebase(final int type){
        final Rank_Main_Model list_women = new Rank_Main_Model();
        Women_Sub_list = new ArrayList<RankingModel>();
        mRootref.child("IccRanking").child("Women").child("Team").child("Primarysceen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                json1 = gson.toJson(dataSnapshot.getValue());
                //System.out.print(json1);
                dbHelper.insertFielduser("TeamWomen",json1);
                if(type==1) {
                    try {
                        JSONObject jObj = new JSONObject(json1);
                        list_women.setHeader("Teams | Rankings");
                        list_women.setGender(jObj.getJSONObject("ODI").getString("gender"));
                        list_women.setType("teams");
                        list_women.setFormat(jObj.getJSONObject("ODI").getString("type"));
                        for (int i = 0; i < 2; i++) {
                            RankingModel list1 = new RankingModel();
                            if (i == 0) {
                                list1.setType("teams");
                                list1.setGender(jObj.getJSONObject("ODI").getString("gender"));
                                list1.setFlag(jObj.getJSONObject("ODI").getString("imageprimary"));
                                list1.setTeamName(jObj.getJSONObject("ODI").getString("name"));
                                list1.setRating(jObj.getJSONObject("ODI").getString("ratings"));
                                list1.setFormatType(jObj.getJSONObject("ODI").getString("type"));
                                list1.setPlayerType(jObj.getJSONObject("ODI").getString("type"));
                                Women_Sub_list.add(list1);
                                list_women.setRanking_List(Women_Sub_list);
                            }
                            if (i == 1) {
                                list1.setType("teams");
                                list1.setGender(jObj.getJSONObject("T20").getString("gender"));
                                list1.setFlag(jObj.getJSONObject("T20").getString("imageprimary"));
                                list1.setTeamName(jObj.getJSONObject("T20").getString("name"));
                                list1.setRating(jObj.getJSONObject("T20").getString("ratings"));
                                list1.setFormatType(jObj.getJSONObject("T20").getString("type"));
                                list1.setPlayerType(jObj.getJSONObject("T20").getString("type"));
                                Women_Sub_list.add(list1);
                                list_women.setRanking_List(Women_Sub_list);
                            }

                        }
                        Women_Main_List.add(list_women);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void LoadWomensTeamsLocal(){
        final Rank_Main_Model list_women = new Rank_Main_Model();
        Women_Sub_list = new ArrayList<RankingModel>();
        try{
            JSONObject jObj = new JSONObject(dbHelper.getFieldValueUser("TeamWomen"));
            list_women.setHeader("Teams | Rankings");
            list_women.setGender(jObj.getJSONObject("ODI").getString("gender"));
            list_women.setType("teams");
            list_women.setFormat(jObj.getJSONObject("ODI").getString("type"));
            for(int i =0; i<2; i++){
                RankingModel list1 = new RankingModel();
                if(i==0){
                    list1.setType("teams");
                    list1.setGender(jObj.getJSONObject("ODI").getString("gender"));
                    list1.setFlag(jObj.getJSONObject("ODI").getString("imageprimary"));
                    list1.setTeamName(jObj.getJSONObject("ODI").getString("name"));
                    list1.setRating(jObj.getJSONObject("ODI").getString("ratings"));
                    list1.setFormatType(jObj.getJSONObject("ODI").getString("type"));
                    list1.setPlayerType(jObj.getJSONObject("ODI").getString("type"));
                    Women_Sub_list.add(list1);
                    list_women.setRanking_List(Women_Sub_list);
                }
                if(i==1){
                    list1.setType("teams");
                    list1.setGender(jObj.getJSONObject("T20").getString("gender"));
                    list1.setFlag(jObj.getJSONObject("T20").getString("imageprimary"));
                    list1.setTeamName(jObj.getJSONObject("T20").getString("name"));
                    list1.setRating(jObj.getJSONObject("T20").getString("ratings"));
                    list1.setFormatType(jObj.getJSONObject("T20").getString("type"));
                    list1.setPlayerType(jObj.getJSONObject("T20").getString("type"));
                    Women_Sub_list.add(list1);
                    list_women.setRanking_List(Women_Sub_list);
                }

            }
            Women_Main_List.add(list_women);



        }catch (Exception e){
            e.printStackTrace();
        }

        if(cd.isConnectingToInternet()){
            LoadWomensTeamsFirebase(0);
        }

    }



    public void LoadWomensPlayersFirebase(final int type){
        mRootref.child("IccRanking").child("Women").child("Players").child("Primarysceen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String PlayerJson = gson.toJson(dataSnapshot.getValue());
                //System.out.print(json1);
                dbHelper.insertFielduser("PlayerWomen", PlayerJson);
                if (type == 1) {
                    try {
                        String Type = "TEST";
                        JSONObject jObj = new JSONObject(PlayerJson);
                        for (int j = 0; j < 2; j++) {
                            final Rank_Main_Model list_women = new Rank_Main_Model();
                            Women_Sub_list = new ArrayList<RankingModel>();
                            if (j == 0) {
                                Type = "ODI";
                                list_women.setHeader("Players | ODI");
                                list_women.setFormat("ODI");
                            }
                            if (j == 1) {
                                Type = "T20";
                                list_women.setHeader("Players | T20");
                                list_women.setFormat("T20");
                            }

                            list_women.setGender("Women");
                            list_women.setType("Players");

                            for (int i = 0; i < 3; i++) {
                                RankingModel list1 = new RankingModel();
                                if (i == 0) {
                                    list1.setType("Players");
                                    list1.setGender("Women");
                                    list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playerimage"));
                                    list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("name"));
                                    list1.setRating(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("team"));
                                    list1.setFormatType(Type);
                                    list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playertype"));
                                    Women_Sub_list.add(list1);
                                    list_women.setRanking_List(Women_Sub_list);

                                }
                                if (i == 1) {
                                    list1.setType("Players");
                                    list1.setGender("Women");
                                    list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playerimage"));
                                    list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("name"));
                                    list1.setRating(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("team"));
                                    list1.setFormatType(Type);
                                    list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playertype"));
                                    Women_Sub_list.add(list1);
                                    list_women.setRanking_List(Women_Sub_list);

                                }
                                if (i == 2) {
                                    list1.setType("Players");
                                    list1.setGender("Women");
                                    list1.setFlag(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playerimage"));
                                    list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("name"));
                                    list1.setRating(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("team"));
                                    list1.setFormatType(Type);
                                    list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playertype"));
                                    Women_Sub_list.add(list1);
                                    list_women.setRanking_List(Women_Sub_list);

                                }
                            }
                            Women_Main_List.add(list_women);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(type == 1){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                men_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_up);
                                men_layout.setVisibility(0);
                                mens_ranking.animate().setDuration(650).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).translationY(0.0f);
                                flag = 0;
                            }
                        },200);
                    }




                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void LoadWomensPlayerssLocal(){

        try {
            String Type = "TEST";
            JSONObject jObj = new JSONObject(dbHelper.getFieldValueUser("PlayerWomen"));
            for (int j = 0; j < 2; j++) {
                final Rank_Main_Model list_women = new Rank_Main_Model();

                Women_Sub_list = new ArrayList<RankingModel>();
                if (j == 0) {
                    Type = "ODI";
                    list_women.setHeader("Players | ODI");
                    list_women.setFormat("ODI");
                }
                if (j == 1) {
                    Type = "T20";
                    list_women.setHeader("Players | T20");
                    list_women.setFormat("T20");
                }

                list_women.setGender("Women");
                list_women.setType("Players");

                for (int i = 0; i < 3; i++) {
                    RankingModel list1 = new RankingModel();
                    if (i == 0) {
                        list1.setType("Players");
                        list1.setGender("Women");
                        list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playerimage"));
                        list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("name"));
                        list1.setRating(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("team"));
                        list1.setFormatType(Type);
                        list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BATSMAN").getString("playertype"));
                        Women_Sub_list.add(list1);
                        list_women.setRanking_List(Women_Sub_list);

                    }
                    if (i == 1) {
                        list1.setType("Players");
                        list1.setGender("Women");
                        list1.setFlag(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playerimage"));
                        list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("name"));
                        list1.setRating(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("team"));
                        list1.setFormatType(Type);
                        list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("BOWLER").getString("playertype"));
                        Women_Sub_list.add(list1);
                        list_women.setRanking_List(Women_Sub_list);

                    }
                    if (i == 2) {
                        list1.setType("Players");
                        list1.setGender("Women");
                        list1.setFlag(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playerimage"));
                        list1.setTeamName(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("name"));
                        list1.setRating(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("team"));
                        list1.setFormatType(Type);
                        list1.setPlayerType(jObj.getJSONObject(Type).getJSONObject("ALLROUNDER").getString("playertype"));
                        Women_Sub_list.add(list1);
                        list_women.setRanking_List(Women_Sub_list);

                    }
                }
                Women_Main_List.add(list_women);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                men_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_up);
                men_layout.setVisibility(0);
                mens_ranking.animate().setDuration(650).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).translationY(0.0f);
                flag = 0;
            }
        },200);


        if(loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(cd.isConnectingToInternet()){
            LoadWomensTeamsFirebase(0);
        }

    }


    public void mens_dropdown_btn(View view) {
        if (this.flag == 0) {
            this.men_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_down);
            this.mens_ranking.animate().setDuration(650).alpha(0.0f).translationY((float) (-this.mens_ranking.getHeight()));
            //this.men_layout.setVisibility(8);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    men_layout.setVisibility(8);
                }
            },650);
            this.flag = 1;
            return;
        }
        this.men_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_up);
        this.men_layout.setVisibility(0);
        this.mens_ranking.animate().setDuration(650).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).translationY(0.0f);
        this.flag = 0;
    }

    public void womens_dropdown_btn(View view) {
        if (this.flag1 == 0) {
            this.women_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_down);
            this.womens_ranking.animate().setDuration(600).alpha(0.0f).translationY((float) (-this.womens_ranking.getHeight()));
           // this.women_layout.setVisibility(8);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    women_layout.setVisibility(8);
                }
            },600);
            this.flag1 = 1;
            return;
        }
        this.women_dropdown_btn.setImageResource(R.mipmap.ic_action_arrow_drop_up);
        this.women_layout.setVisibility(0);
        this.womens_ranking.animate().setDuration(600).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).translationY(0.0f);
        this.flag1 = 0;
    }
}
