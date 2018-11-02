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

 public class Batting_adapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<ScoreboardData> Items;


    public Batting_adapter(Activity activity, ArrayList<ScoreboardData> Items) {
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.batting_layout_view, null);


        TextView batsman = (TextView) convertView.findViewById(R.id.batsman);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        TextView run = (TextView) convertView.findViewById(R.id.run);
        TextView bowl = (TextView) convertView.findViewById(R.id.bowl);
        TextView four = (TextView) convertView.findViewById(R.id.four);
        TextView six = (TextView) convertView.findViewById(R.id.six);
        TextView strike_rate = (TextView) convertView.findViewById(R.id.strike_rate);


        batsman.setText(Items.get(position).getBatsman());
        run.setText(Items.get(position).getBat_run());
        bowl.setText(Items.get(position).getBat_ball());
        four.setText(Items.get(position).getFour());
        six.setText(Items.get(position).getSix());
        status.setText(Items.get(position).getStatus());
        strike_rate.setText(Items.get(position).getStrike_rate());
        notifyDataSetChanged();
        // Glide.with(activity).load(Global.IMG_URL+Items.get(position).getImage()).into(Image);

        return convertView;

    }

}
