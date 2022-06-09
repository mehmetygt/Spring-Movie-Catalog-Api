package com.example.bookstoreapi.adapter.api.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentValidationResponse {

    private String mail;
    private PaymentState paymentState;

}
