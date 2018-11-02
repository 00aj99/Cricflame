package com.cricflame.cricflame.Chat.conversation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cricflame.cricflame.Chat.BaseActivity;
import com.cricflame.cricflame.Chat.Dependencies;
import com.cricflame.cricflame.Chat.conversation.presenter.ConversationPresenter;
import com.cricflame.cricflame.Chat.conversation.view.ConversationDisplayer;
import com.cricflame.cricflame.Chat.conversation.view.ConversationView;
import com.cricflame.cricflame.Chat.navigation.AndroidNavigator;
import com.cricflame.cricflame.R;


/**
 * Created by marco on 29/07/16.
 */

public class ConversationActivity extends BaseActivity {

    private ConversationPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ConversationDisplayer conversationDisplayer = (ConversationView) findViewById(R.id.conversationView);
        presenter = new ConversationPresenter(
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getConversationService(),
                conversationDisplayer,
                Dependencies.INSTANCE.getUserService(),
                getIntent().getStringExtra("sender"),
                getIntent().getStringExtra("destination"),
                new AndroidNavigator(this)
        );
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
    protected void onDestroy() {
        super.onDestroy();
    }

}
