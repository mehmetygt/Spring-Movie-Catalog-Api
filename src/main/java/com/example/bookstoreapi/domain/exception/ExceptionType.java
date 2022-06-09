package com.example.bookstoreapi.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    GENERIC_EXCEPTION(1, "Bilinmeyen bir sorun oluştu"),

    MOVIE_DATA_NOT_FOUND(1001, "Film bulunamadı"),
    ACTOR_DATA_NOT_FOUND(1002, "Oyuncu bulunamadı"),
    RATE_DATA_NOT_FOUND(1003, "Puanlama bulunamadı"),
    MAIL_NOT_FOUND(1004, "Mail adresi bulunamadı"),
    ACCOUNT_NOT_FOUND(1005, "Hesap bulunamadı"),

    COLLECTION_SIZE_EXCEPTION(2001, "Liste boyutları uyuşmuyor"),
    MAIL_EXISTS(2002, "Bu mail adresi kullanılmaktadır");

    private final Integer code;
    private final String message;
}
