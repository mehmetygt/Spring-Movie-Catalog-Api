package com.example.bookstoreapi.adapter.rest.account;

import com.example.bookstoreapi.domain.account.Account;
import com.example.bookstoreapi.domain.membership.MembershipType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignUpRequest {

    @NotEmpty(message = "mail is not null")
    private String mail;

    @NotEmpty(message = "password is not null")
    private String password;

    @NotNull(message = "membership is not null")
    private MembershipType membership;

    public Account toModel() {
        return Account.builder()
                .mail(mail)
                .password(password)
                .membership(membership)
                .build();
    }
}
