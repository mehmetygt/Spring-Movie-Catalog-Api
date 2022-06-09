package com.example.bookstoreapi.domain.port;



import com.example.bookstoreapi.domain.movie.Movie;

import java.util.Optional;

public interface MovieCachePort {

    Optional<Movie> retrieveMovie(Long movieId);

    void createMovie(Movie movie);
}
