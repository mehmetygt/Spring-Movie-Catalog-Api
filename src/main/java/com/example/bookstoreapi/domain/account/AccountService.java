package com.example.bookstoreapi.domain.account;

import com.example.bookstoreapi.domain.exception.ExceptionType;
import com.example.bookstoreapi.domain.exception.ValidationException;
import com.example.bookstoreapi.domain.membership.MembershipStrategy;
import com.example.bookstoreapi.domain.port.AccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPort accountPort;

    private final MembershipStrategy membershipStrategy;

    public Account createAccount(Account account) {
        if (Boolean.TRUE.equals(accountPort.isMailExists(account.getMail())))
            throw new ValidationException(ExceptionType.MAIL_EXISTS);
        updateLastActivationDate(account);
        return accountPort.create(account);
    }

    private void updateLastActivationDate(Account account) {
        LocalDateTime dateTime = membershipStrategy.calculateLastActivationDate(account);
        LocalDateTime calculate = account.getMembership().calculate();
        account.setLastActivationDate(dateTime);
    }

    public Account retrieve(Long id) {
        return accountPort.retrieve(id);
    }
}
