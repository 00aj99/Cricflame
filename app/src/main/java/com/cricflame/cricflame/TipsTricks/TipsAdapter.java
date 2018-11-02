package com.cricflame.cricflame.TipsTricks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TourData> products;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, Description,Id;
        public ImageView img_view,shareit;
        public RelativeLayout layout;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(com.cricflame.cricflame.R.id.name);
            // Description = (TextView) view.findViewById(com.cricflame.cricket.R.id.desp);
            Id = (TextView) view.findViewById(com.cricflame.cricflame.R.id.id);
            img_view = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.view_image);
            shareit = (ImageView) view.findViewById(com.cricflame.cricflame.R.id.shareit);
            layout = (RelativeLayout) view.findViewById(com.cricflame.cricflame.R.id.layout_tips_heading);
            layout.getBackground().setAlpha(128);





        }
    }


    public TipsAdapter(Context mContext, ArrayList<TourData> products) {
        this.mContext = mContext;
        this.products = products;
    }

    public Object getItem(int location) {
        return products.get(location);
    }
    @Override
    public TipsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tips_trick_view, parent, false);


        return new TipsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TipsAdapter.MyViewHolder holder, int position) {
        TourData productItem = products.get(position);
        holder.title.setText(productItem.getLeague_name());
        //holder.Description.setText(productItem.getDescription());
        holder.Id.setText(productItem.getId());
        if(productItem.getImage().equals("") || productItem.getImage()== null){
            Glide.with(mContext).load(R.drawable.app_logo);
        }else{
            Glide.with(mContext).load(Global.TIPS_IMAGE_URL+productItem.getImage()).into(holder.img_view);
        }

        final String shareUrl = productItem.getId();
        final String subject = productItem.getLeague_name();
        holder.shareit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    String sAux = shareUrl;

                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    mContext.startActivity(Intent.createChooser(i, "Share Via"));
                } catch (Exception e) {

                }
            }
        });



    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}