package com.cricflame.cricflame.Model;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cricflame.cricflame.R;

/**
 * Created by Deepak Sharma on 09/11/2017.
 */

public class LoadingDialog extends Dialog {

    private Animation loading_animation;
    private ImageView loading_img;
    private TextView mtvTitle;
    private TextView mtvmessage;
    ProgressBar pb;

    public LoadingDialog(Context context, String title, String message) {
        super(context);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_loading);
        pb = findViewById(R.id.universal_progress);
        loading_img = (ImageView) findViewById(R.id.loading_img);
        mtvTitle = (TextView) findViewById(R.id.tvDialogTitle);
        mtvmessage = (TextView) findViewById(R.id.tvMessage);
        mtvTitle.setText(title);
        mtvmessage.setText(message);
    }

    @Override
    public void onDetachedFromWindow() {
        if (isShowing())
            super.dismiss();
        super.onDetachedFromWindow();
    }

    public LoadingDialog(Context context) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_loading);
        pb = findViewById(R.id.universal_progress);
        loading_img = (ImageView) findViewById(R.id.loading_img);
        mtvTitle = (TextView) findViewById(R.id.tvDialogTitle);
        mtvmessage = (TextView) findViewById(R.id.tvMessage);
    }

    public void setMessage(String message) {
        mtvmessage.setText(message);
    }

    public void setTitle(String title) {

        mtvTitle.setText(title);
    }

    @Override
    public void show() {
       /* loading_animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.loading);
        loading_img.startAnimation(loading_animation);*/
       pb.setVisibility(0);
        super.show();
    }

    @Override
    public void dismiss() {
        try {

        /*    loading_img.clearAnimation();
            loading_animation = null;*/
            pb.setVisibility(8);
        } catch (Exception e) {
        }
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
    }

}

