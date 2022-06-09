package com.example.bookstoreapi.domain.membership;

import com.example.bookstoreapi.domain.account.Account;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service(MembershipType.Name.TRIAL)
public class TrialMembership implements Membership {

    @Override
    public LocalDateTime calculateLastActivationDate(Account account) {
        return LocalDateTime.now().plusMonths(1);
    }
}
