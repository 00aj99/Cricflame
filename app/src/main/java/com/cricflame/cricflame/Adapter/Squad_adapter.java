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
import com.cricflame.cricflame.Model.SquadsData;
import com.cricflame.cricflame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Bhoopendra on 06-07-2016.
 */
public class Squad_adapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;
    private ArrayList<SquadsData> Items;



    public Squad_adapter(Activity activity, ArrayList<SquadsData> Items) {
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
            convertView = inflater.inflate(R.layout.browse_player_view,null);

        TextView Id = (TextView) convertView.findViewById(R.id.id);
        TextView Title = (TextView) convertView.findViewById(R.id.name);
        TextView Description = (TextView) convertView.findViewById(R.id.team);
        ImageView Image = (ImageView) convertView.findViewById(R.id.team_info);

        Id.setText(Items.get(position).getId());
        Title.setText(Items.get(position).getPlayer_name());
        Description.setText(Html.fromHtml(Items.get(position).getPlayer_type()));

        Glide.with(activity).load(Global.PLAYER_URL+Items.get(position).getPlayer_image()).into(Image);
        notifyDataSetChanged();
        return convertView;

    }


}
