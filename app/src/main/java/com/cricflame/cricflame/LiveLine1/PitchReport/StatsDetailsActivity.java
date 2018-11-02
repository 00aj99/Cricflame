package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.R;

public class StatsDetailsActivity extends AppCompatActivity {

    private TextView heading,date,description;
    private ImageView image_stats;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_details);
        init();
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        heading.setText(getIntent().getStringExtra("heading"));
        description.setText(Html.fromHtml(getIntent().getStringExtra("description")));
        date.setText(getIntent().getStringExtra("date"));
        Glide.with(StatsDetailsActivity.this).load(Global.STATS_URL + getIntent().getStringExtra("image")).into(image_stats);
        //Glide.with(getApplicationContext()).load(Global.STATS_URL+getIntent().getStringExtra("image"));
    }

    private void init() {
        image_stats = (ImageView)findViewById(R.id.stats_image);
        heading = (TextView)findViewById(R.id.heading);
        date = (TextView)findViewById(R.id.date);
        description = (TextView)findViewById(R.id.description);
    }
}
