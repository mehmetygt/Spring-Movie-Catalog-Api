package com.example.bookstoreapi.domain.port;


import com.example.bookstoreapi.domain.account.Account;

public interface AccountPort {

    Account retrieveByMail(String mail);

    Account create(Account account);

    Account retrieve(Long id);

    Boolean isMailExists(String mail);
}
