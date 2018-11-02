package com.cricflame.cricflame.Chat.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cricflame.cricflame.Chat.BaseActivity;
import com.cricflame.cricflame.Chat.Dependencies;

import com.cricflame.cricflame.Chat.navigation.AndroidRegistrationNavigator;
import com.cricflame.cricflame.Chat.registration.presenter.RegistrationPresenter;
import com.cricflame.cricflame.Chat.registration.view.RegistrationDisplayer;
import com.cricflame.cricflame.R;

/**
 * Created by marco on 28/07/16.
 */

public class RegistrationActivity extends BaseActivity {

    private RegistrationPresenter presenter;
    private AndroidRegistrationNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        RegistrationDisplayer registrationDisplayer = (RegistrationDisplayer) findViewById(R.id.registrationView);
        navigator = new AndroidRegistrationNavigator(this);
        presenter = new RegistrationPresenter(
                Dependencies.INSTANCE.getRegistrationService(),
                registrationDisplayer,
                navigator);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

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


}
