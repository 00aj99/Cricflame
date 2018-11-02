package com.cricflame.cricflame;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cricflame.cricflame.Model.TourData;

import java.util.ArrayList;

/**
 * Created by Pavneet on 07-Sep-17.
 */

public class ViewPagerAdapter1 extends PagerAdapter {

    Context context1;
    LayoutInflater layoutInflater1;

    public  ArrayList<TourData> videoList;

    public ViewPagerAdapter1(Context context,ArrayList<TourData> products) {

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

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater1.inflate(com.cricflame.cricflame.R.layout.viewpagervideo, container, false);
        ImageView imageView = (ImageView) view1.findViewById(com.cricflame.cricflame.R.id.videoView);
        TourData productItem = videoList.get(position);
      //  Glide.with(context1).load(Global.VIDEO_URL+productItem.getLeague_name()).into(imageView);


        // Locate the TextViews in viewpager_item.xml


        ((ViewPager) container).addView(view1);

        return view1;
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
        View view1 = (View) object;
        vp.removeView(view1);

    }


}
