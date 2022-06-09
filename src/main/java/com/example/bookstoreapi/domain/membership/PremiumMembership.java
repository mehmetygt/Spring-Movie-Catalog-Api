package com.example.bookstoreapi.domain.membership;

import com.example.bookstoreapi.domain.account.Account;
import com.example.bookstoreapi.domain.port.PaymentApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service(MembershipType.Name.PREMUIM)
@RequiredArgsConstructor
public class PremiumMembership implements Membership {

    private final PaymentApiPort paymentApiPort;

    @Override
    public LocalDateTime calculateLastActivationDate(Account account) {
        boolean paymentResult = paymentApiPort.validatePayment(account);

        if(paymentResult)
            return LocalDateTime.now().plusYears(1);
        else
            return LocalDateTime.now().plusMonths(1);
    }
}
