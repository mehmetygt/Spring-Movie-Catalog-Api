package com.example.bookstoreapi.domain.actor;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
public class Actor implements Serializable {

    private Long id;
    private String name;
    private LocalDateTime birthDate;
}
