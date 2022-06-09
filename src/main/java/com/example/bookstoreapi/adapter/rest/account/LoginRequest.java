package com.example.bookstoreapi.adapter.rest.account;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty(message = "mail is not null")
    private String mail;

    @NotEmpty(message = "password is not null")
    private String password;
}
