package com.cricflame.cricflame.Chat.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.cricflame.cricflame.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.cricflame.cricflame.Chat.BaseActivity;
import com.cricflame.cricflame.Chat.Dependencies;

import com.cricflame.cricflame.Chat.main.presenter.MainPresenter;
import com.cricflame.cricflame.Chat.main.view.MainDisplayer;
import com.cricflame.cricflame.Chat.navigation.AndroidMainNavigator;

/**
 * Created by marco on 14/08/16.
 */

public class MainActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private MainPresenter presenter;
    private AndroidMainNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        MainDisplayer mainDisplayer = (MainDisplayer) findViewById(R.id.mainView);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        navigator = new AndroidMainNavigator(this, googleApiClient);
        presenter = new MainPresenter(
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getUserService(),
                mainDisplayer,
                Dependencies.INSTANCE.getMainService(),
                Dependencies.INSTANCE.getMessagingService(),
                navigator,
                Dependencies.INSTANCE.getFirebaseToken(),
                this
                );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!navigator.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
        /*if (!presenter.onBackPressed())
            if (!navigator.onBackPressed()) {
                super.onBackPressed();
            }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // DO SOMETHING
    }

}

