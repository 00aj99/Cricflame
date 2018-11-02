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
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Bhoopendra on 06-07-2016.
 */
public class VideoListAdapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<TourData> Items;



    public VideoListAdapter(Activity activity, ArrayList<TourData> Items) {
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
            convertView = inflater.inflate(R.layout.video_view_layout,null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.view_image);
        TextView title= (TextView) convertView.findViewById(R.id.heading);

        title.setText(Items.get(position).getHeading());
        Glide.with(activity).load(Global.VIDEODATA_URL+Items.get(position).getLeague_name()).into(imageView);
        notifyDataSetChanged();
        return convertView;

    }


}
