package com.cricflame.cricflame.Records;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yashlam on 1/31/2018.
 */

@SuppressLint("ValidFragment")
public class BowlingFragment extends Fragment {
    private RecyclerView recycle_list;

    private ArrayList<String> items = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.record_fragment_layout,container,false);
        recycle_list= (RecyclerView) view.findViewById(R.id.recycler_view);

        getBattngHeading();


        return view;
    }
    private void getBattngHeading(){
        //String Url = "matalanghana.com/cricklineapi/fetch_betting_list.php";
        String Url = "http://matalanghana.com/cricklineapi/fetch_bowling_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("betting_record");
                            items.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                items.add(object.getString("name"));
                            }
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                            recycle_list.setLayoutManager(mLayoutManager);
                            RecordAdapter adapter = new RecordAdapter(getActivity(),items);
                            recycle_list.setItemAnimator(new DefaultItemAnimator());
                            recycle_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

        private final ArrayList<String> items;
        private final Context mContext;

        public RecordAdapter(Context context, ArrayList<String> list) {
            this.mContext = context;
            this.items = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.betfare_allmatch_layout_view, parent, false);
            return new RecordAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.title.setText(items.get(position));
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext,DetailsRecordActivity.class);
                    intent.putExtra("name",items.get(position));
                    intent.putExtra("type","bowling");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, Description,Id;
            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.name);


            }
        }
    }



}
