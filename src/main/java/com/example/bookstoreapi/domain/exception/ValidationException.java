package com.example.bookstoreapi.domain.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final ExceptionType exceptionType;
    private String detail;

    public ValidationException(ExceptionType exceptionType, String detail) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.detail = detail;
    }

    public ValidationException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
