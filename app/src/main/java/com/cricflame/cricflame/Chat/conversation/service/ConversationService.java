package com.cricflame.cricflame.Chat.conversation.service;

import com.cricflame.cricflame.Chat.conversation.data_model.Chat;
import com.cricflame.cricflame.Chat.conversation.data_model.Message;
import com.cricflame.cricflame.Chat.database.DatabaseResult;

import rx.Observable;

/**
 * Created by marco on 29/07/16.
 */

public interface ConversationService {

    Observable<Chat> getOldMessages(String self, String destination, String key);

    Observable<Message> getNewMessages(String self, String destination, String key);

    Observable<DatabaseResult<Chat>> getChat(String self, String destination);

    void sendMessage(String user, Message message);

    Observable<Boolean> getTyping(String self, String destination);

    void setTyping(String self, String destination, Boolean value);

}
