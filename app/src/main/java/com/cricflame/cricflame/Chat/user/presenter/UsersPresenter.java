package com.cricflame.cricflame.Chat.user.presenter;

import android.os.Bundle;

import com.cricflame.cricflame.Chat.login.data_model.Authentication;
import com.cricflame.cricflame.Chat.login.service.LoginService;
import com.cricflame.cricflame.Chat.navigation.AndroidConversationsNavigator;
import com.cricflame.cricflame.Chat.user.data_model.User;
import com.cricflame.cricflame.Chat.user.data_model.Users;
import com.cricflame.cricflame.Chat.user.service.UserService;
import com.cricflame.cricflame.Chat.user.view.UsersDisplayer;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by marco on 19/08/16.
 */

public class UsersPresenter {

    private static final String SENDER = "sender";
    private static final String DESTINATION = "destination";

    private UsersDisplayer usersDisplayer;
    private AndroidConversationsNavigator navigator;
    private LoginService loginService;
    private UserService userService;

    private Subscription loginSubscription;

    private User self;

    public UsersPresenter(
            UsersDisplayer conversationListDisplayer,
            AndroidConversationsNavigator navigator,
            LoginService loginService,
            UserService userService) {
        this.usersDisplayer = conversationListDisplayer;
        this.navigator = navigator;
        this.loginService = loginService;
        this.userService = userService;
    }

    public void startPresenting() {
        usersDisplayer.attach(conversationInteractionListener);

        loginSubscription = loginService.getAuthentication()
                .filter(successfullyAuthenticated())
                .doOnNext(new Action1<Authentication>() {
                    @Override
                    public void call(Authentication authentication) {
                        self = authentication.getUser();
                    }
                })
                .flatMap(new Func1<Authentication, Observable<Users>>() {
                    @Override
                    public Observable<Users> call(Authentication authentication) {
                        return userService.syncUsers();
                    }
                })
                .subscribe(new Subscriber<Users>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Users users) {
                        usersDisplayer.display(users);
                    }
                });
    }

    public void stopPresenting() {
        usersDisplayer.detach(conversationInteractionListener);
        loginSubscription.unsubscribe();
    }

    private Func1<Authentication, Boolean> successfullyAuthenticated() {
        return new Func1<Authentication, Boolean>() {
            @Override
            public Boolean call(Authentication authentication) {
                return authentication.isSuccess();
            }
        };
    }

    private final UsersDisplayer.UserInteractionListener conversationInteractionListener = new UsersDisplayer.UserInteractionListener() {

        @Override
        public void onUserSelected(User user) {
            Bundle bundle = new Bundle();
            bundle.putString(SENDER, self.getUid());
            bundle.putString(DESTINATION,user.getUid());
            navigator.toSelectedConversation(bundle);
        }
    };

    public void filterUsers(String text) {
        usersDisplayer.filter(text);
    }

}
