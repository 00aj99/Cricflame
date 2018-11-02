package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import java.util.ArrayList;

/**
 * Created by Deepak Sharma on 24/10/2017.
 */

public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

    public ArrayList<TourData> alName;

    Context context;

    public HLVAdapter(Context context, ArrayList<TourData> alName) {
        super();
        this.context = context;
        this.alName = alName;

    }
    public Object getItem(int location) {
        return alName.get(location);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_video_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TourData productItem = alName.get(i);
      //  Glide.with(context).load(Global.VIDEO_URL+productItem.getLeague_name()).into(viewHolder.imgThumbnail);



    }

    @Override
    public int getItemCount() {
        return alName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgThumbnail;
        public TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }


    }

}