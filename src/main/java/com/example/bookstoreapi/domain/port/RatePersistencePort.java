package com.example.bookstoreapi.domain.port;


import com.example.bookstoreapi.domain.rate.Rate;

import java.util.List;

public interface RatePersistencePort {

    void rateToMovie(Rate entity);

    List<Rate> retrieveByMovieId(Long movieId);
}
