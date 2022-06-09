package com.example.bookstoreapi.domain.port;

import com.example.bookstoreapi.domain.account.Account;

public interface PaymentApiPort {
    boolean validatePayment(Account account);
}
