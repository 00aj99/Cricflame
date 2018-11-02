package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Adapter.SeriesWise_Adapter;
import com.cricflame.cricflame.Adapter.TeamWiseAdapter;
import com.cricflame.cricflame.Adapter.TourAdapter_New;
import com.cricflame.cricflame.Adapter.Tour_Adapter;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.Model.TeamWiseModel;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.News.EndlessRecyclerOnScrollListener;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.ScheduleDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import static com.cricflame.cricflame.Global.SCHEDULE_ID;

public class ODI_Schedule_Fragment extends Fragment {
    public ArrayList<TourDataNew> tourData=new ArrayList<TourDataNew>();
    public ArrayList<TeamWiseModel>  teamNames = new ArrayList<TeamWiseModel>();
    TeamWiseAdapter teamWiseAdapter;
    RecyclerView tour_list;
    TourAdapter_New tourAdapter_new;
    ImageView no_data;
    boolean visibleToUser;
    DatabaseReference mRootref;
    DatabaseReference childref;
    LoadingDialog loadingDialog;
    int currentItem = 0;
    private int mPostsPerPage = 10;
    int checkScrolling;
    public ArrayList<SeriesWise_Model> SeriesData=new ArrayList<SeriesWise_Model>();
    SeriesWise_Adapter seriesWise_adapter;
    Button DateWise,SeriesWise,TeamWise;
    String ClickType = "DateWise";
    String setDate ="0";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            mRootref = FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();
            loadingDialog = new LoadingDialog(getActivity());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.international_match_layout,container,false);
        tour_list= (RecyclerView) view.findViewById(R.id.tour_list);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        DateWise = view.findViewById(R.id.date_wise_data);
        SeriesWise = view.findViewById(R.id.series_wise_data);
        TeamWise = view.findViewById(R.id.team_wise_data);
      /*  if(new CheckProxy().isProxyDisabled()){
            getAllInternationalTours();
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            getActivity().finish();
        }*/


        tourAdapter_new = new TourAdapter_New(getActivity(), tourData);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tour_list.setLayoutManager(mLayoutManager);

        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        tour_list.setItemAnimator(new DefaultItemAnimator());

        tour_list.setAdapter(tourAdapter_new);
        tourAdapter_new.notifyDataSetChanged();

        tour_list.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if(checkScrolling>=10){
                    loadMoreData();
                }

            }
        });
        ItemClickSupport.addTo(tour_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                       /* tourAdapter_new= (TourAdapter_New) recyclerView.getAdapter();
                        TourDataNew tourData= (TourDataNew) tourAdapter_new.getItem(position);
                        SCHEDULE_ID=String.valueOf(tourData.id);
                        startActivity(new Intent(getActivity(), ScheduleDetail.class));*/

                    }
                });


        DateWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkScrolling = tourData.size();
                currentItem = tourData.size();
                ClickType = "DateWise";
                tourAdapter_new = new TourAdapter_New(getActivity(), tourData);
                tour_list.setHasFixedSize(true);
                final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                tour_list.setLayoutManager(mLayoutManager);
                tour_list.setAdapter(tourAdapter_new);
                tourAdapter_new.notifyDataSetChanged();
                tour_list.setNestedScrollingEnabled(false);
                tour_list.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        if(checkScrolling>=10){
                            loadMoreData();
                        }

                    }
                });

                DateWise.setBackgroundResource(R.drawable.round_button_green);
                DateWise.setTextColor(getActivity().getResources().getColor(R.color.white));
                SeriesWise.setBackgroundResource(R.drawable.round_unselected_green);
                SeriesWise.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                TeamWise.setBackgroundResource(R.drawable.round_unselected_green);
                TeamWise.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                if(tourData.size()==0){
                    tourData.clear();
                    currentItem = 0;
                    setDate = "0";
                   getAllODITours();
                }



            }
        });

        SeriesWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkScrolling = SeriesData.size();
                currentItem = SeriesData.size();
                ClickType = "SeriesWise";
                seriesWise_adapter = new SeriesWise_Adapter(getActivity(), SeriesData,"ODI");
                tour_list.setHasFixedSize(true);
                final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                //tour_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                tour_list.setLayoutManager(mLayoutManager);
                tour_list.setAdapter(seriesWise_adapter);
                seriesWise_adapter.notifyDataSetChanged();
                tour_list.setNestedScrollingEnabled(false);
                tour_list.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        if(checkScrolling>=10){
                            loadMoreData();
                        }

                    }
                });
                SeriesWise.setBackgroundResource(R.drawable.round_button_green);
                SeriesWise.setTextColor(getActivity().getResources().getColor(R.color.white));
                DateWise.setBackgroundResource(R.drawable.round_unselected_green);
                DateWise.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                TeamWise.setBackgroundResource(R.drawable.round_unselected_green);
                TeamWise.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                if(SeriesData.size()==0){
                    SeriesData.clear();
                    currentItem = 0;
                    setDate = "0";
                    getAllSereisWiseMatch();

                }
            }
        });

        TeamWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkScrolling = teamNames.size();
                ClickType="TeamWise";
                teamWiseAdapter = new TeamWiseAdapter(getActivity(),teamNames,"ODI");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                tour_list.setLayoutManager(linearLayoutManager);
                tour_list.setAdapter(teamWiseAdapter);
                teamWiseAdapter.notifyDataSetChanged();
                tour_list.setNestedScrollingEnabled(false);

                TeamWise.setBackgroundResource(R.drawable.round_button_green);
                TeamWise.setTextColor(getActivity().getResources().getColor(R.color.white));
                DateWise.setBackgroundResource(R.drawable.round_unselected_green);
                DateWise.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));
                SeriesWise.setBackgroundResource(R.drawable.round_unselected_green);
                SeriesWise.setTextColor(getActivity().getResources().getColor(R.color.parrot_base));

                if(teamNames.size()==0){
                    teamNames.clear();
                    currentItem = 0;
                    new GetAllODITeams().execute();
                }


            }
        });

        return view;
    }

    public void getAllODITours(){
       // tourData.clear();
        try{
            loadingDialog.show();
            checkScrolling =0;
            childref = mRootref.child("Fixtures").child("ODI").child("DATEWISE");
            childref.orderByKey()
                    .startAt(String.valueOf(currentItem))
                    .limitToFirst(mPostsPerPage)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    TourDataNew list = new TourDataNew();
                                    String[] splited =  snapshot.child("date_time").getValue().toString().split("\\s+");
                                    String date=splited[0];
                                    String time=splited[1];
                                    if(setDate.equalsIgnoreCase(date)){
                                        list.date_time = time;
                                        setDate = date;
                                    }else{
                                        list.date_time = snapshot.child("date_time").getValue().toString();
                                        setDate = date;
                                    }
                                    list.series_name = snapshot.child("series_name").getValue().toString();
                                    list.match_number = snapshot.child("match_number").getValue().toString();
                                    list.team1 = snapshot.child("team1").getValue().toString();
                                    list.team1_flag = snapshot.child("team1_flag").getValue().toString();
                                    list.team2 = snapshot.child("team2").getValue().toString();
                                    list.team2_flag = snapshot.child("team2_flag").getValue().toString();
                                    list.venue = snapshot.child("venue").getValue().toString();
                                    list.f_key = snapshot.child("f_key").getValue().toString();
                                    list.result = snapshot.child("result").getValue().toString();
                                    list.t1_short_name = snapshot.child("t1_short_name").getValue().toString();
                                    list.t2_short_name = snapshot.child("t2_short_name").getValue().toString();
                                    tourData.add(list);
                                    if(currentItem == 0){
                                        tourAdapter_new.notifyDataSetChanged();
                                    }else{
                                        tourAdapter_new.notifyItemInserted(tourData.size() - 1);
                                    }
                                    checkScrolling++;
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

        }catch (Exception e){
            e.printStackTrace();
            getActivity().finish();
        }

    }

    public void getAllSereisWiseMatch(){
        //tourData.clear();
        loadingDialog.show();
        checkScrolling =0;
        childref = mRootref.child("Fixtures").child("ODI").child("SERIEWISE");
        childref.orderByKey()
                .startAt(String.valueOf(currentItem))
                .limitToFirst(mPostsPerPage)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                SeriesWise_Model list = new SeriesWise_Model();
                                String[] splited =  snapshot.child("date_time").getValue().toString().split("\\s+");
                                String date=returnDateone(splited[0]);
                                String time=splited[1];
                                if(setDate.equalsIgnoreCase(date)){
                                    list.date_time = time;
                                    setDate = date;
                                }else{
                                    list.date_time = snapshot.child("date_time").getValue().toString();
                                    setDate = date;
                                }
                                list.tourname = snapshot.child("tourname").getValue().toString();
                                list.timeperiod = snapshot.child("timeperiod").getValue().toString();
                                list.seriesid = snapshot.child("seriesid").getValue().toString();
                                SeriesData.add(list);
                                if(currentItem == 0){
                                    seriesWise_adapter.notifyDataSetChanged();
                                }else{
                                    seriesWise_adapter.notifyItemInserted(SeriesData.size() - 1);
                                }
                                checkScrolling++;
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

    public class GetAllODITeams extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler sh = new ServiceHandler();
            HashMap<String, String> args1 = new HashMap<>();
            args1.put("type", "odi");
            String resultServer = sh.makePostCall(Global.Comman_Url+"teamName", args1);
            try{
                JSONArray TeamArray = new JSONArray(resultServer);

                for(int i = 0; i <TeamArray.length(); i++){
                    TeamWiseModel list = new TeamWiseModel(TeamArray.getJSONObject(i).getString("team1"),TeamArray.getJSONObject(i).getString("team1_flag"));
                    teamNames.add(list);
                }


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(loadingDialog.isShowing())
                loadingDialog.dismiss();
            try{
                teamWiseAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
        if(new CheckProxy().isProxyDisabled()){
            if(tourData==null || tourData.size()==0){
                getAllODITours();
            }
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            getActivity().finish();
        }
    }

    private void loadMoreData() {
        currentItem = currentItem + 10;
        if(ClickType.equalsIgnoreCase("DateWise")){
            getAllODITours();
        }else if(ClickType.equalsIgnoreCase("SeriesWise")){
            getAllSereisWiseMatch();
        }
    }


    public String returnDateone(String input){
        String goal = null;
        try{
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("MMMM yyyy");
            goal = outFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return goal;
    }
}
