package com.cricflame.cricflame.Fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Custom_view.CustomGridView;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.SeriesWise_Model;
import com.cricflame.cricflame.Model.TourDataNew;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SquadFragment extends Fragment {
    ExpandableListView expandableListView;
    String Series_id;
    RequestQueue queue;
    ArrayList<ArrayList<String>> squads = new ArrayList();
    SquadsExpandableListAdapter squadsExpandableListAdapter;
    LoadingDialog loadingDialog;
    ProgressBar pb;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> india = new ArrayList();
        loadingDialog = new LoadingDialog(getActivity());
        this.queue = Volley.newRequestQueue(getActivity());
        Series_id = getActivity().getIntent().getExtras().getString("SeriesId");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_squads,container,false);
        this.expandableListView = (ExpandableListView) view.findViewById(R.id.squad_expandable_list_view);
        pb = view.findViewById(R.id.progressBar1);
        this.squadsExpandableListAdapter = new SquadsExpandableListAdapter(getContext());
        this.expandableListView.setAdapter(this.squadsExpandableListAdapter);
        this.expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        loadTeam();
        return  view;
    }


    public class SquadsExpandableListAdapter extends BaseExpandableListAdapter {
        Context context;

        public SquadsExpandableListAdapter(Context context) {
            this.context = context;
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            super.registerDataSetObserver(observer);
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            super.unregisterDataSetObserver(observer);
        }

        public int getGroupCount() {
            return SquadFragment.this.squads.size();
        }

        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        public Object getGroup(int groupPosition) {
            return null;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        public long getGroupId(int groupPosition) {
            return 0;
        }

        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        public boolean hasStableIds() {
            return false;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blue_with_down_arrow, null);
            }
            ((TextView) convertView.findViewById(R.id.blue_with_down_arrow)).setText((CharSequence) ((ArrayList) SquadFragment.this.squads.get(groupPosition)).get(0));
            return convertView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.team_squad_grid, null);
            }
            CustomGridView gridView = (CustomGridView) convertView.findViewById(R.id.grid_view);
            gridView.setNumColumns(2);
            SquadGridAdapter adapter = new SquadGridAdapter(groupPosition, this.context);
            gridView.setAdapter(adapter);
            int totalHeight = 0;
            for (int size = 0; size < adapter.getCount(); size += 2) {
                TextView textView = (TextView) adapter.getView(size, null, gridView);
                textView.measure(0, 0);
                totalHeight += textView.getMeasuredHeight();
            }
            gridView.SetHeight(totalHeight);
            return convertView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public boolean isEmpty() {
            return false;
        }

        public void onGroupExpanded(int groupPosition) {
        }

        public void onGroupCollapsed(int groupPosition) {
        }

        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }

    private class SquadGridAdapter extends BaseAdapter {
        private Context context;
        private int pos;

        public SquadGridAdapter(int pos, Context context) {
            this.pos = pos;
            this.context = context;
        }

        public int getCount() {
            return ((ArrayList) SquadFragment.this.squads.get(this.pos)).size() - 1;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.textview, null);
            }
            TextView textView = (TextView) convertView;
            textView.setText((CharSequence) ((ArrayList) SquadFragment.this.squads.get(this.pos)).get(position + 1));
            textView.setTextColor(-1);
            if (position % 4 == 0 || position % 4 == 3) {
                textView.setBackgroundColor(Color.argb(0, 255, 255, 255));
            } else {
                textView.setBackgroundColor(Color.argb(20, 255, 255, 255));
            }
            return convertView;
        }
    }

    private void loadTeam() {
       // loadingDialog.show();
        pb.setVisibility(View.VISIBLE);
        StringRequest getTeamSquads = new StringRequest(Request.Method.POST, Global.Comman_Url+"seriesSquad",new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                try {
                    JSONObject response = new JSONObject(res);
                    Iterator<String> keys = response.keys();
                    while (keys.hasNext()) {
                        String team_name = (String) keys.next();
                        ArrayList<String> t1 = new ArrayList();
                        t1.add(team_name);
                        try {
                            for (int i = 1; i <=response.getJSONObject(team_name).length(); i++) {
                                t1.add(response.getJSONObject(team_name).getString("p"+i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pb.setVisibility(View.GONE);
                        }
                        SquadFragment.this.squads.add(t1);
                        SquadFragment.this.squadsExpandableListAdapter.notifyDataSetChanged();
                       /* if(loadingDialog.isShowing())
                            loadingDialog.dismiss();*/
                        pb.setVisibility(View.GONE);
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                    pb.setVisibility(View.GONE);
                   /* if(loadingDialog.isShowing())
                        loadingDialog.dismiss();*/
                    }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                /*if(loadingDialog.isShowing())
                    loadingDialog.dismiss();*/
                pb.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();
                params.put("seriesid", Series_id);
                //params.put("seriesid", "2716");
                return params;
            }
        };
        getTeamSquads.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(getTeamSquads);
    }

}
