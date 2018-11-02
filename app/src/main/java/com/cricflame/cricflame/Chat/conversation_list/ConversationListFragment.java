package com.cricflame.cricflame.Chat.conversation_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricflame.cricflame.Chat.Dependencies;

import com.cricflame.cricflame.Chat.conversation_list.presenter.ConversationListPresenter;
import com.cricflame.cricflame.Chat.conversation_list.view.ConversationListDisplayer;
import com.cricflame.cricflame.Chat.navigation.AndroidConversationsNavigator;
import com.cricflame.cricflame.Chat.navigation.AndroidNavigator;
import com.cricflame.cricflame.R;

/**
 * Created by marco on 29/07/16.
 */

public class ConversationListFragment extends Fragment {

    private ConversationListPresenter presenter;
    private AndroidConversationsNavigator navigator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
        getActivity().setTitle(R.string.conversations_toolbar_title);

        navigator = new AndroidConversationsNavigator((AppCompatActivity)getActivity(),new AndroidNavigator(getActivity()));
        presenter = new ConversationListPresenter(
                (ConversationListDisplayer) rootView.findViewById(R.id.conversationsView),
                Dependencies.INSTANCE.getConversationListService(),
                navigator,
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getUserService()
                );

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
