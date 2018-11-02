package com.cricflame.cricflame.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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



/**
 * Created by Deepak Sharma on 10/10/2017.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TourData> products;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, Description,Id;
        public ImageView thumbnail;
        RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            Id = (TextView) view.findViewById(R.id.productId);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int pos=getAdapterPosition();
//                        Product product=products.get(pos);
//                        Toast.makeText(getActivity(),""+pos,Toast.LENGTH_SHORT).show();
//                        productId=product.getId();
//                        Toast.makeText(getActivity(),""+productId,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(),""+productId,Toast.LENGTH_SHORT).show();
//                        FunctionUtils.Replace((AppCompatActivity)getActivity(),R.id.flMainContainer,new ProductDetailFragment());
//                    }
//                });

        }
    }


    public PhotoViewAdapter(Context mContext, ArrayList<TourData> products) {
        this.mContext = mContext;
        this.products = products;
    }

    public Object getItem(int location) {
        return products.get(location);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TourData productItem = products.get(position);


        Glide.with(mContext).load(Global.PHOTO_URL+productItem.getLeague_name()).into(holder.thumbnail);
       // Glide.with(mContext).load("https://drive.google.com/open?id=1kxMMJBOIzr47qlWfHr_8YdlrtwLBhROO").into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}
