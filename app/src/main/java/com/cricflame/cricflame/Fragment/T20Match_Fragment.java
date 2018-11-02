package com.cricflame.cricflame.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Adapter.Tour_Adapter;
import com.cricflame.cricflame.CheckProxy;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.TourDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Global.Tour_Id;
import static com.cricflame.cricflame.Global.Tour_name;
import static com.cricflame.cricflame.Global.URL;

/**
 * Created by Deepak Sharma on 06/10/2017.
 */

public class T20Match_Fragment extends Fragment {
    public  ArrayList<TourData> tourData=new ArrayList<TourData>();
    RecyclerView tour_list;
    Tour_Adapter tour_adapter;
    ImageView no_data;


    private DatabaseReference mRootRef;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.international_match_layout,container,false);
        tour_list= (RecyclerView) view.findViewById(R.id.tour_list);
        no_data= (ImageView) view.findViewById(R.id.no_data);
        if(new CheckProxy().isProxyDisabled()){
            getAllInternationalTours();
            //FirebaseTask();
        }else {
            Toast.makeText(getActivity(), "Something went wrong. Is proxy enabled?", Toast.LENGTH_SHORT).show();

        }

        ItemClickSupport.addTo(tour_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        tour_adapter= (Tour_Adapter) recyclerView.getAdapter();
                        TourData tourData= (TourData) tour_adapter.getItem(position);
                        Tour_Id=tourData.getId();
                        Tour_name = tourData.getLeague_name();
                        startActivity(new Intent(getActivity(), TourDetailsActivity.class));

                        // do it
                    }
                });

        return view;
    }

/*
    public void FirebaseTask() {
        mRootRef = FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();


        tour_adapter = new Tour_Adapter(getActivity(), tourData);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        tour_list.setLayoutManager(mLayoutManager);

        tour_list.setItemAnimator(new DefaultItemAnimator());

        tour_list.setAdapter(tour_adapter);


        DatabaseReference dr_create_league = mRootRef.child("create_league");
        dr_create_league.orderByChild("start_date")

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tourData.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String str = (String) snapshot.child("league_type").getValue();
                            if(str.equals("T20")) {
                                tourData.add(snapshot.getValue(TourData.class));
                                tour_adapter.notifyDataSetChanged();
                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }
    public class Tour_Adapter extends RecyclerView.Adapter<Tour_Adapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<TourData> products;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView Name, Description, Id, month, TourDate;


            public MyViewHolder(View view) {
                super(view);
                Id = (TextView) view.findViewById(R.id.id);
                Name = (TextView) view.findViewById(R.id.name);
                month = (TextView) view.findViewById(R.id.month);
                TourDate = (TextView) view.findViewById(R.id.tour_date);


            }
        }

        public Tour_Adapter(Context mContext, ArrayList<TourData> products) {
            this.mContext = mContext;
            this.products = products;
        }

        public Object getItem(int location) {
            return products.get(location);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.international_match_view, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            String[] separated = products.get(position).getStart_date().split("-");
            String month = Utility.returnMonth(separated[1]);
            holder.month.setText(month+" "+separated[0]);




            holder.Id.setText(products.get(position).getId());

            holder.Name.setText(products.get(position).getLeague_name());

            holder.TourDate.setText(products.get(position).getStart_date() + " - " + products.get(position).getEnd_date());


        }


        @Override
        public int getItemCount() {
            return products.size();
        }
    }
*/
    public void getAllInternationalTours(){
        tourData.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL+"t20_league.php?status=series", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject data=response.getJSONObject("data");
                    JSONArray months=response.getJSONArray("data");
                    if (months.length()<=0) {
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setImageResource(R.drawable.no_data);
                    }
                    else {
                        for (int i = 0; i < months.length(); i++) {
                            JSONObject jsonObject = months.getJSONObject(i);
                            JSONArray jsonArray = jsonObject.getJSONArray("month");
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                TourData tourDatas = new TourData();
                                tourDatas.setId(jsonObject1.getString("id"));
                                tourDatas.setLeague_name(jsonObject1.getString("league_name"));
                                tourDatas.setMonth(jsonObject1.getString("month_name"));
                                tourDatas.setMonthId(String.valueOf(j));
                                tourDatas.setStrat_date(jsonObject1.getString("start_date"));
                                tourDatas.setEnd_date(jsonObject1.getString("end_date"));
                                tourData.add(tourDatas);
                            }

                        }
                        tour_adapter = new Tour_Adapter(getActivity(), tourData);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                        tour_list.setLayoutManager(mLayoutManager);

                        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                        tour_list.setItemAnimator(new DefaultItemAnimator());

                        tour_list.setAdapter(tour_adapter);

                        tour_adapter.notifyDataSetChanged();
                        tour_list.setAdapter(tour_adapter);
                    }  // months.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }
}
