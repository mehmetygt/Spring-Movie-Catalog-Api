package com.example.bookstoreapi.domain.movie;

import com.example.bookstoreapi.domain.rate.Rate;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Movie {

    private Long id;
    private String name;
    private MovieGenre genre;
    private Integer releaseYear;
    private String director;
    private List<Rate> rateList;

    public Double calculateAvgRate() {
        Integer sum = rateList.stream()
                .map(Rate::getPoint)
                .reduce(0, Integer::sum);

        return sum / (double) rateList.size();
    }

    public Integer getValidRateCount() {
        return (int) rateList.stream()
                .filter(Rate::isStillValid)
                .count();
    }
}
