package com.cricflame.cricflame.Chat.login.service;


import com.cricflame.cricflame.Chat.login.data_model.Authentication;

import rx.Observable;

/**
 * Created by marco on 27/07/16.
 */

public interface LoginService {

    Observable<Authentication> getAuthentication();

    void loginWithGoogle(String idToken);

    void loginWithEmailAndPass(String email, String password);

    void sendPasswordResetEmail(String email);

}
