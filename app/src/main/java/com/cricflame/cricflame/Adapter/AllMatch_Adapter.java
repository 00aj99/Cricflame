package com.cricflame.cricflame.Adapter;

/**
 * Created by webore on 12/15/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.AllMatchData;

import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Bhoopendra on 06-07-2016.
 */
public class AllMatch_Adapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<AllMatchData> Items;



    public AllMatch_Adapter(Activity activity, ArrayList<AllMatchData> Items) {
        this.activity = activity;
        this.Items = Items;
    }



    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int location) {
        return Items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.all_matches_view,null);

        TextView Id = (TextView) convertView.findViewById(R.id.id);
        TextView match_date = (TextView) convertView.findViewById(R.id.match_date);
        TextView match_detail = (TextView) convertView.findViewById(R.id.match_detail);
        TextView team1_name = (TextView) convertView.findViewById(R.id.team1_name);
        TextView team2_name = (TextView) convertView.findViewById(R.id.team2_name);
        TextView team1_score = (TextView) convertView.findViewById(R.id.team1_score);
        TextView team2_score = (TextView) convertView.findViewById(R.id.team2_score);
        TextView result = (TextView) convertView.findViewById(R.id.result);
        ImageView team1_flag = (ImageView) convertView.findViewById(R.id.team1_flag);
        ImageView team2_flag = (ImageView) convertView.findViewById(R.id.team2_flag);

        Id.setText(Items.get(position).getId());
        match_date.setText(Items.get(position).getMatch_date());
        match_detail.setText(Items.get(position).getMatch_name());
        team1_name.setText(Items.get(position).getTeam1_name());
        team2_name.setText(Items.get(position).getTeam2_name());
        team1_score.setText(Items.get(position).getTeam1_score());
        team2_score.setText(Items.get(position).getTeam2_score());
        result.setText(Items.get(position).getResult());
        notifyDataSetChanged();
        Glide.with(activity).load(Global.PHOTO_URL+Items.get(position).getTeam1_flag()).into(team1_flag);
        Glide.with(activity).load(Global.PHOTO_URL+Items.get(position).getTeam2_flag()).into(team2_flag);

        return convertView;

    }


}
