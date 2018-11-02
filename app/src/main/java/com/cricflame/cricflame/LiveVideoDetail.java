
package com.cricflame.cricflame;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;




public class LiveVideoDetail extends AppCompatActivity {

   // private  String file_url = Global.VIDEODATA_URL+"vid-0-201710011133415049.MP4";
    private  String file_url = "rtsp://178.32.67.42:1935/myStr1/myStream";
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    TextView videoHeading;

    ImageView back;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_live_video_detail);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        videoHeading= (TextView) findViewById(com.cricflame.cricflame.R.id.video_heading);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  VideoViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
                onBackPressed();
            }
        });
        exoPlayerView= (SimpleExoPlayerView) findViewById(com.cricflame.cricflame.R.id.exoplayer_view);



        BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
        TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer= ExoPlayerFactory.newSimpleInstance(this,trackSelector);
       // file_url=LIVE_VIDEO_URL;

        Uri videoUri=Uri.parse(file_url);

        DefaultHttpDataSourceFactory dataSourceFactory=new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(videoUri,dataSourceFactory,extractorsFactory,null,null);
        PlaybackControlView playbackControlView=new PlaybackControlView(LiveVideoDetail.this);
        playbackControlView.show();
        exoPlayer.setPlaybackParameters(PlaybackParameters.DEFAULT);
        exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);





    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.setPlayWhenReady(true);
    }


    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.setPlayWhenReady(false);
    }


}

