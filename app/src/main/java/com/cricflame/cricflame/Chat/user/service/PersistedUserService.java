package com.cricflame.cricflame.Chat.user.service;

import com.cricflame.cricflame.Chat.user.data_model.User;
import com.cricflame.cricflame.Chat.user.data_model.Users;
import com.cricflame.cricflame.Chat.user.database.UserDatabase;

import rx.Observable;

/**
 * Created by marco on 31/07/16.
 */

public class PersistedUserService implements UserService {

    private final UserDatabase userDatabase;

    public PersistedUserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public Observable<Users> syncUsers() {
        return userDatabase.observeUsers();
    }

    @Override
    public Observable<User> getUser(String userId) {
        return userDatabase.observeUser(userId);
    }

    @Override
    public Observable<Users> getUsers() {
        return userDatabase.singleObserveUsers();
    }

    @Override
    public void setName(User user, String name) {
        userDatabase.setUserName(user.getUid(),name);
    }

    @Override
    public void setProfileImage(User user, String image) {
        userDatabase.setUserImage(user.getUid(),image);
    }

}
