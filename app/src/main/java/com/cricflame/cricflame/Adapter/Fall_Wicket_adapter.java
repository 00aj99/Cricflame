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

public class Fall_Wicket_adapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<ScoreboardData> Items;


    public Fall_Wicket_adapter(Activity activity, ArrayList<ScoreboardData> Items) {
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
            convertView = inflater.inflate(R.layout.fall_wicket_layout, null);


        TextView player = (TextView) convertView.findViewById(R.id.player);
        TextView over = (TextView) convertView.findViewById(R.id.over);
        TextView score = (TextView) convertView.findViewById(R.id.score);



        player.setText(Items.get(position).getName());
        score.setText(Items.get(position).getScore());
        over.setText(Items.get(position).getOver_out());
        // Glide.with(activity).load(Global.IMG_URL+Items.get(position).getImage()).into(Image);
        notifyDataSetChanged();
        return convertView;

    }

}
