package com.example.bookstoreapi.domain.port;

public interface MailApiPort {

    void sendActivationMail(String mail, String message);
}
