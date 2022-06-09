package com.example.bookstoreapi.domain.rate;


import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.port.MoviePersistencePort;
import com.example.bookstoreapi.domain.port.RatePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RatePersistencePort ratePersistencePort;
    private final MoviePersistencePort moviePersistencePort;


    public Double findAvgRateByAnemicModel(Long movieId) {
        List<Rate> rates = ratePersistencePort.retrieveByMovieId(movieId);

        Integer sum = rates.stream()
                .map(Rate::getPoint)
                .reduce(0, Integer::sum);

        return sum / (double) rates.size();
    }

    public Integer validRateCountV1(Long movieId) {
        List<Rate> rates = ratePersistencePort.retrieveByMovieId(movieId)
                .stream()
                .filter(r -> r.getCreatedDate().isAfter(LocalDateTime.of(2022,1,1,0,0,0)))
                .collect(Collectors.toList());

        return rates.size();
    }

    public Integer validRateCountV2(Long movieId) {
        Movie movie = moviePersistencePort.retrieve(movieId);
        return movie.getValidRateCount();
    }

    public Double findAvgRateByRichModel(Long movieId) {
        Movie movie = moviePersistencePort.retrieve(movieId);
        return movie.calculateAvgRate();
    }

    public void rateToMovie(Rate rate) {
        ratePersistencePort.rateToMovie(rate);
    }

    public List<Rate> retrieveByMovieIdV2(Long movieId) {
        //todo refactor via hexagonal
        return null;
        /*
        return moviePersistencePort.retrieve(movieId)
                .getRates()
                .stream()
                .map(Rate::convertFromRateEntity)
                .collect(Collectors.toList());

         */
    }

    public List<Rate> retrieveByMovieId(Long movieId) {
        //todo refactor via hexagonal
        return null;
        /*
        return ratePersistencePort.retrieveByMovieId(movieId)
                .stream()
                .map(Rate::convertFromRateEntity)
                .collect(Collectors.toList());

         */
    }
}
