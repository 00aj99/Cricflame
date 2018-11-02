package com.cricflame.cricflame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Model.TourData;

import java.util.ArrayList;

/**
 * Created by tirtha on 23-12-2017.
 */

public class VideoListAdapter1 extends RecyclerView.Adapter<VideoListAdapter1.ViewHolder>{
    Context context;
    ArrayList<TourData> video_list = new ArrayList<TourData>();
    TourData obj;

    public VideoListAdapter1(Context context, ArrayList<TourData> video_list) {
        this.context = context;
        this.video_list = video_list;
    }

    @Override
    public VideoListAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view_layout, parent, false);
        VideoListAdapter1.ViewHolder myholder = new VideoListAdapter1.ViewHolder(view);
        return myholder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public Object getItem(int location) {
        return video_list.get(location);
    }

    @Override
    public void onBindViewHolder(VideoListAdapter1.ViewHolder holder, int position) {
        obj = video_list.get(position);
      // MainActivity.MatchId = video_list.get(position).getId();


        holder._heading.setText(obj.getHeading());
        Glide.with(context).load(Global.VIDEODATA_URL + obj.getLeague_name()).into(holder._image);

    }

    @Override
    public int getItemCount() {
        return video_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView _heading;
        ImageView _image;
        public ViewHolder(View itemView) {
            super(itemView);
            _image = (ImageView) itemView.findViewById(R.id.view_image);
            _heading = (TextView) itemView.findViewById(R.id.heading);
        }
    }
}
