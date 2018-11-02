package com.cricflame.cricflame.Adapter;

/**
 * Created by webore on 12/15/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.NewsData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class News_Adapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<NewsData> Items;



    public News_Adapter(Activity activity, ArrayList<NewsData> Items) {
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
            convertView = inflater.inflate(R.layout.news_home_layout_view,null);

       TextView subheading = (TextView) convertView.findViewById(R.id.news_heading);
        TextView Id = (TextView) convertView.findViewById(R.id.id);
        TextView Title = (TextView) convertView.findViewById(R.id.title);
        TextView Duration = (TextView) convertView.findViewById(R.id.duration);
        TextView Description = (TextView) convertView.findViewById(R.id.description);
        ImageView Image = (ImageView) convertView.findViewById(R.id.news_image);

        Id.setText(Items.get(position).getId());
        Title.setText(Items.get(position).getTitle());
       subheading.setText(Items.get(position).getSubheading());
        Duration.setText(Items.get(position).getDuration());
        Description.setText(Html.fromHtml(Items.get(position).getDescription()));

        Glide.with(activity).load(Global.IMG_URL+Items.get(position).getImage()).into(Image);

      /*//  Id.setText(Items.get(position).Source);
        Title.setText(Items.get(position).Headline);
        subheading.setText(Items.get(position).Intro);
        Duration.setText(Items.get(position).Date);
        Description.setText(Items.get(position).para);
        Glide.with(activity).load(Items.get(position).Image).into(Image);*/


        notifyDataSetChanged();
        return convertView;

    }


}
