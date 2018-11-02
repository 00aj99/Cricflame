package com.cricflame.cricflame.Chat.global.service;

import com.cricflame.cricflame.Chat.database.DatabaseResult;
import com.cricflame.cricflame.Chat.global.data_model.Chat;
import com.cricflame.cricflame.Chat.global.data_model.Message;

import rx.Observable;

/**
 * Created by marco on 08/08/16.
 */

public interface GlobalService {

    Observable<Chat> getOldMessages(String key);

    Observable<Message> getNewMessages(String key);

    Observable<DatabaseResult<Chat>> getChat();

    void sendMessage(Message message);

}
