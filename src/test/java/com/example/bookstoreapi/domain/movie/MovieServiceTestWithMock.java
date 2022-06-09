package com.example.bookstoreapi.domain.movie;


import com.example.bookstoreapi.domain.actor.Actor;
import com.example.bookstoreapi.domain.exception.ValidationException;
import com.example.bookstoreapi.domain.port.ActorPersistencePort;
import com.example.bookstoreapi.domain.port.MatchingPersistencePort;
import com.example.bookstoreapi.domain.port.MovieCachePort;
import com.example.bookstoreapi.domain.port.MoviePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTestWithMock {

    MovieService movieService;

    @Mock
    MoviePersistencePort moviePersistencePort;

    @Mock
    ActorPersistencePort actorPersistencePort;

    @Mock
    MatchingPersistencePort matchingPersistencePort;

    @Mock
    MovieCachePort movieCachePort;

    @BeforeEach
    void setUp() {
        movieService = new MovieService(
                moviePersistencePort,
                actorPersistencePort,
                matchingPersistencePort,
                movieCachePort
        );
    }

    @Test
    void should_retrieve_movie_when_cache_is_exist() {
        //mock
        Movie mockMovie = Movie.builder()
                .id(1L)
                .director("test director")
                .releaseYear(2000)
                .build();

        when(movieCachePort.retrieveMovie(1L)).thenReturn(Optional.ofNullable(mockMovie));

        //when
        Movie movie = movieService.retrieve(1L);

        //then
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(1L);
        assertThat(movie.getDirector()).isEqualTo("test director");
        assertThat(movie.getReleaseYear()).isEqualTo(2000);

        verifyNoInteractions(moviePersistencePort);
        verifyNoMoreInteractions(movieCachePort);
    }

    @Test
    void should_retrieve_movie_when_cache_empty() {
        //mock
        when(movieCachePort.retrieveMovie(any())).thenReturn(Optional.empty());

        Movie mockMovie = Movie.builder()
                .id(1L)
                .director("test director")
                .releaseYear(2000)
                .build();

        when(moviePersistencePort.retrieve(1L)).thenReturn(mockMovie);
        doNothing().when(movieCachePort).createMovie(mockMovie);

        //when
        Movie movie = movieService.retrieve(1L);

        //then
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(1L);
        assertThat(movie.getDirector()).isEqualTo("test director");
        assertThat(movie.getReleaseYear()).isEqualTo(2000);
    }

    @Test
    void should_create() {
        //given
        List<Actor> actors = new ArrayList<>();
        List<Long> actorIds = new ArrayList<>();

        Movie movie = Movie.builder().build();

        //mock
        Movie createdMovie = Movie.builder().id(3L).build();
        when(moviePersistencePort.save(any())).thenReturn(createdMovie);

        //when
        Long createdMovieId = movieService.create(movie, actors, actorIds);

        //then
        assertThat(createdMovieId).isEqualTo(3);

        verify(matchingPersistencePort, times(1)).create(any(), any());
    }

    @Test
    void should_NOT_create_when_given_actors_not_exist_in_db() {
        //given
        List<Actor> actors = new ArrayList<>();
        List<Long> actorIds = List.of(1L, 2L);

        Movie movie = Movie.builder().build();

        //mock
        when(actorPersistencePort.retrieve(any())).thenReturn(List.of(Actor.builder().build()));

        //when
        Throwable throwable = catchThrowable(() -> movieService.create(movie, actors, actorIds));

        //then
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ValidationException.class)
                .hasMessage("Liste boyutları uyuşmuyor");
    }
}