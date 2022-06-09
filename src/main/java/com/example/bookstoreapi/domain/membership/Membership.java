package com.example.bookstoreapi.domain.membership;


import com.example.bookstoreapi.domain.account.Account;

import java.time.LocalDateTime;

public interface Membership {

    LocalDateTime calculateLastActivationDate(Account account);
}
