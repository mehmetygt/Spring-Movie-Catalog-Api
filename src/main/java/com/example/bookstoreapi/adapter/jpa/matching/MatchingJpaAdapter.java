package com.example.bookstoreapi.adapter.jpa.matching;


import com.example.bookstoreapi.adapter.jpa.actor.ActorEntity;
import com.example.bookstoreapi.adapter.jpa.movie.MovieEntity;
import com.example.bookstoreapi.domain.actor.Actor;
import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.port.MatchingPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingJpaAdapter implements MatchingPersistencePort {

    private final MatchingJpaRepository matchingJpaRepository;

    @Override
    public void create(Movie movie, List<Actor> actors) {
        MovieEntity movieEntity = MovieEntity.from(movie);

        List<MatchingEntity> matchingEntities = actors.stream()
                .map(actor -> {
                    ActorEntity actorEntity = ActorEntity.from(actor);

                    MatchingEntity matchingEntity = new MatchingEntity();
                    matchingEntity.setMovie(movieEntity);
                    matchingEntity.setActor(actorEntity);
                    return matchingEntity;
                }).collect(Collectors.toList());

        matchingJpaRepository.saveAll(matchingEntities);
    }

    @Override
    public List<Actor> retrieveActorsByMovieId(Long movieId) {
        return matchingJpaRepository.findAllByMovie_Id(movieId)
                .stream()
                .map(MatchingEntity::getActor)
                .map(ActorEntity::toModel)
                .collect(Collectors.toList());
    }
}
