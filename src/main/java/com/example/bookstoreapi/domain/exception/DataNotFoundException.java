package com.example.bookstoreapi.domain.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    private final ExceptionType exceptionType;
    private String detail;

    public DataNotFoundException(ExceptionType exceptionType, String detail) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.detail = detail;
    }

    public DataNotFoundException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
