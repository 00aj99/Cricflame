package com.cricflame.cricflame.Adapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class ViewImagePager extends PagerAdapter {

    Context context1;
    LayoutInflater layoutInflater1;

    public  ArrayList<TourData> videoList;

    public ViewImagePager(Context context,ArrayList<TourData> products) {

        this.context1 = context;
        this.videoList = products;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView viewImage;

        layoutInflater1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater1.inflate(R.layout.image_slide_view, container, false);
        // ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        //imageView.setImageResource(images[position]);

        // Locate the TextViews in viewpager_item.xml
        viewImage = (ImageView) view.findViewById(R.id.imageSlide);

       // LinearLayout pager= (LinearLayout) view.findViewById(R.id.pager_view);

//        pager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context1,ScoreboardActivity.class);
//                String id=videoList.get(position).getMatch_date();
//                Toast.makeText(context1,""+id,Toast.LENGTH_SHORT).show();
//                context1.startActivity(intent);
//            }
//        });
     //   pager.setClipToOutline(true);


        Glide.with(context1).load(Global.PHOTO_URL+videoList.get(position).getLeague_name()).into(viewImage);

        ((ViewPager) container).addView(view);

        return view;
    }

    //  view.setOnClickListener(new View.OnClickListener() {
    //   @Override
    //   public void onClick(View v) {

    //       if(position == 0){
    //           Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
    //       } else if(position == 1){
    //           Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
    //       } else {
    //           Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
    //      }

    //  }
    //);

    //  ViewPager vp = (ViewPager) container;
    //  vp.addView(view, 0);
    //  return view;


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }


}
