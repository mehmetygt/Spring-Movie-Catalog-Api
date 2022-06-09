package com.example.bookstoreapi.adapter.jpa.rate;

import com.example.bookstoreapi.adapter.jpa.common.BaseEntity;
import com.example.bookstoreapi.adapter.jpa.movie.MovieEntity;
import com.example.bookstoreapi.domain.rate.Rate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "rateEntity")
@Table(name = "rate")
public class RateEntity extends BaseEntity {

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Integer point;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MovieEntity movie;

    public Rate toModel() {
        return Rate.builder()
                //todo map other field
                .build();
    }
}

