package com.cricflame.cricflame.Chat.main.database;

import com.cricflame.cricflame.Chat.user.data_model.User;

import rx.Observable;

/**
 * Created by marco on 17/08/16.
 */

public interface CloudMessagingDatabase {

    Observable<String> readToken(User user);

    void setToken(User user);

    void enableToken(String userId);

    void disableToken(String userId);

}
