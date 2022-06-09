package com.example.bookstoreapi.adapter.jpa.account;

import com.example.bookstoreapi.adapter.jpa.common.BaseEntity;
import com.example.bookstoreapi.domain.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "account")
@Table(name = "account")
public class AccountEntity extends BaseEntity {

    private String mail;
    private String password;
    private LocalDateTime lastActivationDate;

    public static AccountEntity from(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.password = account.getPassword();
        accountEntity.mail = account.getMail();
        accountEntity.lastActivationDate = account.getLastActivationDate();
        return accountEntity;
    }

    public Account toModel() {
        return Account.builder()
                .id(id)
                .mail(mail)
                .password(password)
                .build();
    }

}
