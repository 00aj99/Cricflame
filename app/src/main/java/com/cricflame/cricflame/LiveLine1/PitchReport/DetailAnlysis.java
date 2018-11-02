package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.R;

public class DetailAnlysis extends AppCompatActivity {
    private TextView heading,date,description;
    private ImageView image_analysis;
    private ImageView back;
    private ImageView gifid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anlysis);

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
        image_analysis.setVisibility(View.GONE);
        Glide.with(DetailAnlysis.this).load(Global.ANALYSIS_IMAGE_URL + getIntent().getStringExtra("image")).into(image_analysis);

      /*  Glide.with(this).load(R.raw.babaji).into(new GlideDrawableImageViewTarget(gifid));*/
    }

    private void init() {
        image_analysis = (ImageView)findViewById(R.id.stats_image);
        heading = (TextView)findViewById(R.id.heading);
        date = (TextView)findViewById(R.id.date);
        description = (TextView)findViewById(R.id.description);
        //gifid=(ImageView) findViewById(R.id.gifid);
    }
}
