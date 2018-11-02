package com.cricflame.cricflame.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.LiveLine1.Commentries.CommentryDataPicker;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cricflame.cricflame.Fragment.All_Matches_Fragment.BMatchId;
import static com.cricflame.cricflame.Global.URL;


/**
 * Created by Deepak Sharma on 07/10/2017.
 */

public class Browse_Commentary_Fragment extends Fragment {


    private ArrayList<CommentryDataPicker> off_com_list = new ArrayList<>();
    private OfflineCommentaryAdapter mAdapter;
    RecyclerView rv_offline_comment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_off_line_commentry, container, false);


        rv_offline_comment = (RecyclerView)view.findViewById(R.id.rv_offline_comment);


        rv_offline_comment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_offline_comment.setHasFixedSize(false);
        rv_offline_comment.setItemAnimator(new DefaultItemAnimator());

        getCommentaryList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class OfflineCommentaryAdapter extends RecyclerView.Adapter<OfflineCommentaryAdapter.MyViewHolder> {
        Context context;
        ArrayList<CommentryDataPicker> com_list = new ArrayList<CommentryDataPicker>();


        public OfflineCommentaryAdapter(Context context, ArrayList<CommentryDataPicker> com_list) {
            this.context = context;
            this.com_list = com_list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comment_view, parent, false);
            MyViewHolder myholder = new MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {


            if(com_list.get(position).event.equals("wicket")){
                holder.text1.setText("W");
                holder.single_layout.setBackgroundColor(Color.CYAN);
            }
            else if(com_list.get(position).event.equals("six")){
                holder.text1.setText("6");
                holder.single_layout.setBackgroundColor(Color.GREEN);
            }
            else if(com_list.get(position).event.equals("four")){
                holder.single_layout.setBackgroundColor(Color.BLUE);
                holder.text1.setText("4");
            }
            else{
                holder.single_layout.setBackgroundColor(Color.GRAY);
                holder.text1.setText(com_list.get(position).event);
            }

            holder.text2.setText(com_list.get(position).comment);
            holder.text3.setText(com_list.get(position).ball);

        }

        @Override
        public int getItemCount() {
            return com_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView text1,text2,text3;
            RelativeLayout single_layout;
            public MyViewHolder(View view) {
                super(view);
                text1 = (TextView) view.findViewById(com.cricflame.cricflame.R.id.text1);
                text2 = (TextView) view.findViewById(com.cricflame.cricflame.R.id.text2);
                text3 = (TextView) view.findViewById(com.cricflame.cricflame.R.id.text3);
                single_layout = (RelativeLayout) view.findViewById(com.cricflame.cricflame.R.id.single_layout);
            }
        }


        public Object getItem(int location) {
            return com_list.get(location);
        }


    }

    public void getCommentaryList(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL+"commentary.php?id="+BMatchId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("data");


                    for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                        CommentryDataPicker picker = new CommentryDataPicker();
                        picker.ball = object.getString("ballno");
                        picker.event = object.getString("event");
                        picker.comment = object.getString("commtxt");
                        off_com_list.add(picker);

                    }
                    mAdapter = new OfflineCommentaryAdapter(getActivity(), off_com_list);
                    rv_offline_comment.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",""+error.toString());

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }





}