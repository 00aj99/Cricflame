package com.cricflame.cricflame.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cricflame.cricflame.Model.ScoreboardData;
import com.cricflame.cricflame.R;

import java.util.ArrayList;

/**
 * Created by Deepak Sharma on 10/10/2017.
 */

public class Bowling_adapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<ScoreboardData> Items;


    public Bowling_adapter(Activity activity, ArrayList<ScoreboardData> Items) {
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
            convertView = inflater.inflate(R.layout.bowling_layout, null);


        TextView bowler = (TextView) convertView.findViewById(R.id.bowler);
        TextView over = (TextView) convertView.findViewById(R.id.over);
        TextView maiden = (TextView) convertView.findViewById(R.id.maiden);
        TextView run = (TextView) convertView.findViewById(R.id.run);
        TextView wicket = (TextView) convertView.findViewById(R.id.wicket);
        TextView economy_rate = (TextView) convertView.findViewById(R.id.economy_rate);


        bowler.setText(Items.get(position).getBowler());
        run.setText(Items.get(position).getBowl_run());
        over.setText(Items.get(position).getOver());
        maiden.setText(Items.get(position).getMaiden());
        wicket.setText(Items.get(position).getWicket());
        economy_rate.setText(Items.get(position).getEconomy());
        // Glide.with(activity).load(Global.IMG_URL+Items.get(position).getImage()).into(Image);
        notifyDataSetChanged();
        return convertView;

    }

}

