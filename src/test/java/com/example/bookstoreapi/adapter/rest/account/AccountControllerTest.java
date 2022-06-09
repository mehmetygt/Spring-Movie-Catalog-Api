package com.example.bookstoreapi.adapter.rest.account;


import com.example.bookstoreapi.BaseIntegrationTest;
import com.example.bookstoreapi.adapter.jpa.account.AccountEntity;
import com.example.bookstoreapi.adapter.jpa.account.AccountJpaRepository;
import com.example.bookstoreapi.domain.membership.MembershipType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AccountControllerTest extends BaseIntegrationTest {

    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_create_premium_account() {
        //given
        SignUpRequest request = new SignUpRequest();
        request.setMail("mail");
        request.setPassword("1234");
        request.setMembership(MembershipType.PREMIUM);

        //when
        ResponseEntity<AccountResponse> response = testRestTemplate.postForEntity("/accounts", request, AccountResponse.class);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Optional<AccountEntity> mail = accountJpaRepository.findByMail("mail");
        assertThat(mail).isPresent();
        assertThat(mail.get().getLastActivationDate()).isAfter(LocalDateTime.now().plusYears(1).minusMinutes(1));
    }

}