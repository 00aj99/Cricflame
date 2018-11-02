package com.cricflame.cricflame.Chat.main.service;

import com.cricflame.cricflame.Chat.user.data_model.User;

import rx.Observable;

/**
 * Created by marco on 17/08/16.
 */

public interface CloudMessagingService {

    Observable<String> readToken(User user);

    void setToken(User user);

}
