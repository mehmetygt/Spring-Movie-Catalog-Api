package com.example.bookstoreapi.domain.movie;


import com.example.bookstoreapi.domain.actor.Actor;
import com.example.bookstoreapi.domain.exception.ExceptionType;
import com.example.bookstoreapi.domain.exception.ValidationException;
import com.example.bookstoreapi.domain.port.ActorPersistencePort;
import com.example.bookstoreapi.domain.port.MatchingPersistencePort;
import com.example.bookstoreapi.domain.port.MovieCachePort;
import com.example.bookstoreapi.domain.port.MoviePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j //todo log levels
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MoviePersistencePort moviePersistencePort;
    private final ActorPersistencePort actorPersistencePort;
    private final MatchingPersistencePort matchingPersistencePort;
    private final MovieCachePort movieCachePort;

    public Long create(Movie movie, List<Actor> actors, List<Long> actorIds) {
        List<Actor> existingActors = retrieveExistingActors(actorIds);
        List<Actor> createdActors = createActors(actors);
        Movie createdMovie = moviePersistencePort.save(movie);

        ArrayList<Actor> newActors = new ArrayList<>();
        newActors.addAll(existingActors);
        newActors.addAll(createdActors);

        matchingPersistencePort.create(createdMovie, newActors);
        return createdMovie.getId();
    }

    private List<Actor> createActors(List<Actor> actors) {
        if (!CollectionUtils.isEmpty(actors)) {
            return actorPersistencePort.create(actors);
        }

        return new ArrayList<>();
    }

    private List<Actor> retrieveExistingActors(List<Long> actorIds) {
        if (!CollectionUtils.isEmpty(actorIds)) {
            List<Actor> retrievedActors = actorPersistencePort.retrieve(actorIds);

            if (retrievedActors.size() < actorIds.size()) {
                String detail = "Verilen actor id db'de bulunamamıştır. Beklenen:" + actorIds + ", bulunan:" + retrievedActors;
                throw new ValidationException(ExceptionType.COLLECTION_SIZE_EXCEPTION, detail);
            }

            return retrievedActors;
        }

        return new ArrayList<>();
    }

    /* film N - N actor

    Cast
    film 1 -> actor1, actor3, actor4
    film2 -> actor1, actor 2,actor 5, actor 4
    film3 -> actor 2, actor3, actor4

    Matching
    film1-id, actor1-id
    film1-id, actor3-id
    film1-id, actor4-id
    film2-id, actor1-id
    ............

     */

    public Movie retrieve(Long id) {
        Optional<Movie> movie = movieCachePort.retrieveMovie(id);
        log.info("Movie is retrieving: {}", id);

        if (movie.isEmpty()) {
            log.info("Movie cache is updating: {}", id);
            Movie retrievedMovie = moviePersistencePort.retrieve(id);
            movieCachePort.createMovie(retrievedMovie);
            return retrievedMovie;
        }

        return movie.get();
    }

    public void delete(Long id) {
        moviePersistencePort.delete(id);
    }
}
