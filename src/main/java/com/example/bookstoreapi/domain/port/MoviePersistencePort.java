package com.example.bookstoreapi.domain.port;

import com.example.bookstoreapi.domain.movie.Movie;

import java.util.List;

public interface MoviePersistencePort {

    Movie save(Movie movie);

    Movie retrieve(Long id);

    List<Movie> retrieveByActorId(Long actorId);

    void delete(Long id);
}
