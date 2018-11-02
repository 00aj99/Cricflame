package com.cricflame.cricflame.Standing.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Standing.Model.Standing;

import java.util.ArrayList;

public class StandingAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<Standing> Items;
    public StandingAdapter(Context activity, ArrayList<Standing> Items) {
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


        View single_list_view = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            single_list_view = new View(activity);
            single_list_view = inflater.inflate(R.layout.single_textview_item, parent,false);
            TextView  tvTitle = (TextView) single_list_view.findViewById(R.id.seriesname);
            tvTitle.setText(Items.get(position).getSeriesName());

        }else{
            single_list_view = (View) convertView;
        }

        return single_list_view;

    }


}



