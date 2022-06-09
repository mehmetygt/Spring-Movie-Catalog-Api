package com.example.bookstoreapi.adapter.jpa.account;


import com.example.bookstoreapi.domain.account.Account;
import com.example.bookstoreapi.domain.exception.ExceptionType;
import com.example.bookstoreapi.domain.exception.DataNotFoundException;
import com.example.bookstoreapi.domain.port.AccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountJpaAdapter implements AccountPort {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account retrieveByMail(String mail) {
        return accountJpaRepository.findByMail(mail)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.MAIL_NOT_FOUND))
                .toModel();
    }

    @Override
    public Account create(Account account) {
        AccountEntity accountEntity = AccountEntity.from(account);
        return accountJpaRepository.save(accountEntity).toModel();
    }

    @Override
    public Account retrieve(Long id) {
        return retrieveUserEntity(id)
                .toModel();
    }

    @Override
    public Boolean isMailExists(String mail) {
        return accountJpaRepository.existsByMail(mail);
    }

    private AccountEntity retrieveUserEntity(Long id) {
        return accountJpaRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.ACCOUNT_NOT_FOUND));
    }
}
