package com.example.bookstoreapi.adapter.jpa.movie;

import com.example.bookstoreapi.adapter.jpa.actor.ActorEntity;
import com.example.bookstoreapi.adapter.jpa.actor.ActorJpaRepository;
import com.example.bookstoreapi.adapter.jpa.common.Status;
import com.example.bookstoreapi.adapter.jpa.matching.MatchingEntity;
import com.example.bookstoreapi.domain.exception.DataNotFoundException;
import com.example.bookstoreapi.domain.exception.ExceptionType;
import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.port.MoviePersistencePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieJpaAdapter implements MoviePersistencePort {

    private final MovieJpaRepository movieJpaRepository;
    private final ActorJpaRepository actorJpaRepository;

    @Override
    public Movie save(Movie movie) {
        MovieEntity movieEntity = MovieEntity.from(movie);
        return movieJpaRepository.save(movieEntity).toModel();
    }

    @Override
    public Movie retrieve(Long id) {
        return movieJpaRepository.findById(id)
                .map(MovieEntity::toModel)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.MOVIE_DATA_NOT_FOUND, "Movie id:" + id));
    }

    @Override
    public List<Movie> retrieveByActorId(Long actorId) {
        return actorJpaRepository.findById(actorId)
                .map(ActorEntity::getMatchings)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.ACTOR_DATA_NOT_FOUND))
                .stream()
                .map(MatchingEntity::getMovie)
                .map(MovieEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        movieJpaRepository.findById(id)
                .ifPresent(movie -> {
                    movie.setStatus(Status.DELETED);
                    movieJpaRepository.save(movie);
                });
    }
}
