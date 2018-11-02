package com.cricflame.cricflame.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.SeriesPointsTableAdapter;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.PointsTableData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PointsTable_Fragment extends Fragment {
    ArrayList<PointsTableData> pointsTableDataArrayList = new ArrayList();
    RequestQueue queue;
    RecyclerView recyclerView;
    String Series_id;
    SeriesPointsTableAdapter seriesPointsTableAdapter;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.queue = Volley.newRequestQueue(getActivity());
        this.pointsTableDataArrayList.add(new PointsTableData(true));
        this.seriesPointsTableAdapter = new SeriesPointsTableAdapter(this.pointsTableDataArrayList);
        Series_id = getActivity().getIntent().getExtras().getString("SeriesId");
        this.queue.add(new JsonObjectRequest(Global.CRICBUZZ_POINTS_TABLE+Series_id, null, new C14481(), new C14492()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_points_table,container,false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.points_table_recyclerview);
        this.recyclerView.setAdapter(this.seriesPointsTableAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return  view;
    }

    class C14481 implements Response.Listener<JSONObject> {
        C14481() {
        }

        public void onResponse(JSONObject res) {
            try {
                JSONObject response = res.getJSONObject("group");
                Iterator<String> keys = response.keys();
                PointsTable_Fragment.this.pointsTableDataArrayList.clear();
                while (keys.hasNext()) {
                    String group = (String) keys.next();
                    if (!group.trim().toLowerCase().equals("na")) {
                        PointsTable_Fragment.this.pointsTableDataArrayList.add(new PointsTableData(group));
                    }
                    try {
                        JSONArray array = response.getJSONArray(group);
                        if (array.length() > 0) {
                            PointsTable_Fragment.this.pointsTableDataArrayList.add(new PointsTableData(false));
                        }
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = "";
                            String series_id = "";
                            String group_id = "";
                            String team_name = jsonObject.getString("teamName");
                            String betfair_name = "";
                            String P = jsonObject.getString("played");
                            String W = jsonObject.getString("won");
                            String L = jsonObject.getString("lost");
                            String NR = jsonObject.getString("noresults");
                            String Pts = jsonObject.getString("pointsscored");
                            String NRR = jsonObject.getString("runrate");
                            String cuprate = "";
                            String group_name = group;
                            PointsTable_Fragment.this.pointsTableDataArrayList.add(new PointsTableData(id, series_id, group_id, team_name, betfair_name, P, W, L, NR, Pts, NRR, cuprate, group_name));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                PointsTable_Fragment.this.seriesPointsTableAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class C14492 implements Response.ErrorListener {
        C14492() {
        }

        public void onErrorResponse(VolleyError error) {
        }
    }
}
