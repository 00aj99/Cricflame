package com.cricflame.cricflame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.cricflame.cricflame.Model.LoadingDialog;

public class VideoTestActivity extends Activity {
    VideoView videoView;
    MediaController mediaC;
    ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_video_test);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);

        videoView = (VideoView) this.findViewById(com.cricflame.cricflame.R.id.rtspVideo);
      //  DisplayMetrics displaymetrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //int h = displaymetrics.heightPixels;
        //int w = displaymetrics.widthPixels;
        //videoView.setLayoutParams(new LinearLayout.LayoutParams(w,h));
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       /* new BackgroundAsyncTask()
                .execute(LiveVideoActivity.LIVE_VIDEO_URL);*/


    }


    private void RtspStream(String rtspUrl) {
        videoView.setVideoURI(Uri.parse(rtspUrl));

        videoView.requestFocus();
        videoView.start();

    }

    public class BackgroundAsyncTask extends AsyncTask<String, Uri, Void> {
        Integer track = 0;
        LoadingDialog dialog;

        protected void onPreExecute() {
            dialog = new LoadingDialog(VideoTestActivity.this);
            dialog.setMessage("Loading, Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
        }

        protected void onProgressUpdate(final Uri... uri) {

            try {

                mediaC=new MediaController(VideoTestActivity.this);
                videoView.setMediaController(mediaC);
                mediaC.setPrevNextListeners(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // next button clicked

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                    }
                });
                mediaC.show(10000);

                videoView.setVideoURI(uri[0]);
                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer arg0) {
                        videoView.start();
                        dialog.dismiss();
                    }
                });


            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                dialog.dismiss();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                dialog.dismiss();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                dialog.dismiss();
            }


        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Uri uri = Uri.parse(params[0]);

                publishProgress(uri);
            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();

            }

            return null;
        }


    }
}