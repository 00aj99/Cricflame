package com.cricflame.cricflame.Adapter;

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



public class BrowseTeamAdapter extends RecyclerView.Adapter<BrowseTeamAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TourData> products;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Description,Id,month,TourDate;
        ImageView imageView;



        public MyViewHolder(View view) {
            super(view);
             Id = (TextView) view.findViewById(R.id.id);
             Name = (TextView) view.findViewById(R.id.name);
             imageView = (ImageView) view.findViewById(R.id.team_info);



        }
    }
    public BrowseTeamAdapter(Context mContext, ArrayList<TourData> products) {
        this.mContext = mContext;
        this.products = products;
    }

    public Object getItem(int location) {
        return products.get(location);
    }
    @Override
    public BrowseTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_team_view, parent, false);


        return new BrowseTeamAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BrowseTeamAdapter.MyViewHolder holder, int position) {
        TourData productItem = products.get(position);
        holder.Id.setText(products.get(position).getId());
        holder.Name.setText(products.get(position).getLeague_name());
        Glide.with(mContext).load(Global.PHOTO_URL+products.get(position).getImage()).into(holder.imageView);





    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}

