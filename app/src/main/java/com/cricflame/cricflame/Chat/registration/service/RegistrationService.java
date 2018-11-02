package com.cricflame.cricflame.Chat.registration.service;

import rx.Observable;

/**
 * Created by marco on 28/07/16.
 */

public interface RegistrationService {

    Observable<Boolean> getRegistration();

    void registerWithEmailAndPass(String email, String password);

}
