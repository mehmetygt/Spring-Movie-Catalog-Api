package com.example.bookstoreapi.domain.actor;


import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.port.ActorPersistencePort;
import com.example.bookstoreapi.domain.port.MatchingPersistencePort;
import com.example.bookstoreapi.domain.port.MoviePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorPersistencePort actorPersistencePort;
    private final MoviePersistencePort moviePersistencePort;
    private final MatchingPersistencePort matchingPersistencePort;

    public Long create(Actor actor) {
        return actorPersistencePort.create(actor);
    }

    public List<Movie> retrieveMovies(Long actorId) {
        return moviePersistencePort.retrieveByActorId(actorId);
    }

    public List<Actor> retrieveActors(Long movieId) {
        return matchingPersistencePort.retrieveActorsByMovieId(movieId);
    }
}
