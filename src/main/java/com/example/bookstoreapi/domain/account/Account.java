package com.example.bookstoreapi.domain.account;

import com.example.bookstoreapi.domain.membership.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Account {

    private Long id;
    private String mail;
    private String password;
    private MembershipType membership;
    private LocalDateTime lastActivationDate;

    public void encodePassword(String password) {
        this.password = password;
    }
}
