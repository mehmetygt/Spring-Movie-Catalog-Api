package com.example.bookstoreapi.adapter.rest.rate;

import com.example.bookstoreapi.domain.rate.Rate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class RateResponse {

    private LocalDateTime createdDate;
    private Long memberId;
    private Long movieId;
    private Integer point;

    public static List<RateResponse> convertFromRate(List<Rate> rateList) {
        return rateList.stream()
                .map(RateResponse::convertFromRate)
                .collect(Collectors.toList());
    }

    private static RateResponse convertFromRate(Rate rate) {
        return RateResponse.builder()
                .createdDate(rate.getCreatedDate())
                .memberId(rate.getMemberId())
                .movieId(rate.getMovieId())
                .point(rate.getPoint())
                .build();
    }
}
