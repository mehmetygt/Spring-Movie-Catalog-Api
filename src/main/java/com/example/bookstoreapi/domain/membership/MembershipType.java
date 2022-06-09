package com.example.bookstoreapi.domain.membership;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public enum MembershipType {
    TRIAL(Name.TRIAL) {
        @Override
        public LocalDateTime calculate() {
            return LocalDateTime.now().plusMonths(1);
        }
    },
    STUDENT(Name.STUDENT) {
        @Override
        public LocalDateTime calculate() {
            return LocalDateTime.now().plusMonths(3);
        }
    },
    PREMIUM(Name.PREMUIM) {
        @Override
        public LocalDateTime calculate() {
            return LocalDateTime.now().plusYears(1);
        }
    };

    private final String typeName;

    public abstract LocalDateTime calculate();

    public interface Name {
        String TRIAL = "trial";
        String STUDENT = "student";
        String PREMUIM = "premium";
    }
}
