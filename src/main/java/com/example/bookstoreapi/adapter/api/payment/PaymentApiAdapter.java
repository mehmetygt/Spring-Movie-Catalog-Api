package com.example.bookstoreapi.adapter.api.payment;

import com.example.bookstoreapi.domain.account.Account;
import com.example.bookstoreapi.domain.port.PaymentApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentApiAdapter implements PaymentApiPort {

    private final RestTemplate restTemplate;

    @Override
    public boolean validatePayment(Account account) {
        //todo properties
        PaymentValidationResponse response = restTemplate.getForObject("http://payment-api:8070/payment-validations?mail=" + account.getMail(), PaymentValidationResponse.class);
        //PaymentValidationResponse response = restTemplate.getForObject("http://localhost:8090/payment-validations?mail=" + account.getMail(), PaymentValidationResponse.class);
        return Optional.ofNullable(response).map(r -> response.getPaymentState().isResult()).orElse(false);
    }
}
