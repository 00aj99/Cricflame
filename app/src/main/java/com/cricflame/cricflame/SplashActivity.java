package com.cricflame.cricflame;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.cricflame.cricflame.DBHelper.DBHelper;
import com.cricflame.cricflame.Util.ConnectionDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.cricflame.cricflame.Help.FirebaseUtils;

import com.cricflame.cricflame.UpdateApp.ForceUpdateChecker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Deepak Sharma on 29-Sept-17.
 */

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ImageView no_internet;
    public static String token;
    CheckProxy cp;
    private DatabaseReference mRootRef;
    private boolean dialogshowing = false;
    ConnectionDetector cd;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_splash);
        cd = new ConnectionDetector(SplashActivity.this);
        dbHelper = new DBHelper(SplashActivity.this);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 500);

    }
}
