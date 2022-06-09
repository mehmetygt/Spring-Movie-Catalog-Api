package com.example.bookstoreapi.adapter.api.mail;

import com.example.bookstoreapi.domain.port.MailApiPort;
import org.springframework.stereotype.Service;

@Service
public class MailApiAdapter implements MailApiPort {

    @Override
    public void sendActivationMail(String mail, String message) {

    }
}
