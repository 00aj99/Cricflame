package com.cricflame.cricflame.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Deepak Sharma on 16/10/2017.
 */

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TourData> products;
    Activity activity;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, Description,Id;
        ImageView imageView;



        public MyViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.view_image);
            title= (TextView) view.findViewById(R.id.heading);



        }
    }


    public VideoViewAdapter(Context mContext, ArrayList<TourData> products) {
        this.mContext = mContext;
        this.products = products;
    }

    public Object getItem(int location) {
        return products.get(location);
    }
    @Override
    public VideoViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_view_layout, parent, false);


        return new VideoViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoViewAdapter.MyViewHolder holder, int position) {
        TourData productItem = products.get(position);
        holder.title.setText(productItem.getHeading());


          Glide.with(mContext).load(Global.VIDEODATA_URL+productItem.getLeague_name()).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}
