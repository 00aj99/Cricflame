package com.cricflame.cricflame.Chat.user.service;

import com.cricflame.cricflame.Chat.user.data_model.User;
import com.cricflame.cricflame.Chat.user.data_model.Users;

import rx.Observable;

/**
 * Created by marco on 31/07/16.
 */

public interface UserService {

    Observable<Users> syncUsers();

    Observable<User> getUser(String userId);

    Observable<Users> getUsers();

    void setName(User user, String name);

    void setProfileImage(User user, String image);

}