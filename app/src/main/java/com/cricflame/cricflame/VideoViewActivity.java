
package com.cricflame.cricflame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;


public class VideoViewActivity extends AppCompatActivity {

    //public static String VIDEO_HEADING = "";
    ImageView back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_videi_view);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  VideoViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
            onBackPressed();
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onStop() {
        super.onStop();

    }


}
