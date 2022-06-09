package com.example.bookstoreapi.domain.port;



import com.example.bookstoreapi.domain.actor.Actor;
import com.example.bookstoreapi.domain.movie.Movie;

import java.util.List;

public interface MatchingPersistencePort {

    void create(Movie movie, List<Actor> actors);

    List<Actor> retrieveActorsByMovieId(Long movieId);
}
