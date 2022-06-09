package com.example.bookstoreapi.controller.movie;



import com.example.bookstoreapi.BaseIntegrationTest;
import com.example.bookstoreapi.adapter.jpa.actor.ActorEntity;
import com.example.bookstoreapi.adapter.jpa.actor.ActorJpaRepository;
import com.example.bookstoreapi.adapter.jpa.matching.MatchingEntity;
import com.example.bookstoreapi.adapter.jpa.matching.MatchingJpaRepository;
import com.example.bookstoreapi.adapter.jpa.movie.MovieEntity;
import com.example.bookstoreapi.adapter.jpa.movie.MovieJpaRepository;
import com.example.bookstoreapi.adapter.redis.MovieCache;
import com.example.bookstoreapi.adapter.rest.actor.ActorCreateRequest;
import com.example.bookstoreapi.adapter.rest.common.ExceptionResponse;
import com.example.bookstoreapi.adapter.rest.movie.MovieCreateResponse;
import com.example.bookstoreapi.adapter.rest.movie.MovieRequest;
import com.example.bookstoreapi.adapter.rest.movie.MovieResponse;
import com.example.bookstoreapi.domain.movie.MovieGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MovieControllerTest extends BaseIntegrationTest {

    @Autowired
    MovieJpaRepository movieJpaRepository;

    @Autowired
    ActorJpaRepository actorJpaRepository;

    @Autowired
    MatchingJpaRepository matchingJpaRepository;

    @Autowired
    RedisTemplate<String, MovieCache> movieRedisTemplate;

    @Test
    @Sql(scripts = "/actor-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_create_movie_with_actors_in_db_and_new_actors() {
        //given
        MovieRequest request = new MovieRequest();
        request.setName("test movie name");
        request.setGenre(MovieGenre.ACTION);
        request.setReleaseYear(2008);
        request.setDirector("test director");

        ActorCreateRequest actorCreateRequest1 = new ActorCreateRequest();
        actorCreateRequest1.setName("actor name 1");
        actorCreateRequest1.setBirthDate(LocalDateTime.of(2000, 1, 12, 14, 0));

        ActorCreateRequest actorCreateRequest2 = new ActorCreateRequest();
        actorCreateRequest2.setName("actor name 2");
        actorCreateRequest2.setBirthDate(LocalDateTime.of(1990, 1, 12, 14, 0));

        request.setActors(List.of(actorCreateRequest1, actorCreateRequest2));
        request.setActorIds(List.of(1001L, 1002L, 1003L));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER_TOKEN);

        //when
        ResponseEntity<MovieCreateResponse> response = testRestTemplate.exchange("/movies", HttpMethod.POST, new HttpEntity<>(request, httpHeaders), MovieCreateResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

        //validate movie
        Optional<MovieEntity> movie = movieJpaRepository.findById(response.getBody().getId());
        assertThat(movie).isPresent();
        assertThat(movie.get().getName()).isEqualTo("test movie name");
        assertThat(movie.get().getCreatedDate()).isNotNull();

        //todo validate other movie fields

        //validate actor
        List<ActorEntity> actors = actorJpaRepository.findAll();
        assertThat(actors)
                .hasSize(5)
                .extracting("name", "birthDate")
                .contains(
                        tuple("actor name 1", LocalDateTime.of(2000, 1, 12, 14, 0)),
                        tuple("actor name 2", LocalDateTime.of(1990, 1, 12, 14, 0))
                );

        //validate matching
        List<MatchingEntity> matchings = matchingJpaRepository.findAll();
        assertThat(matchings).hasSize(5);
    }

    @Test
    @Sql(scripts = "/movie-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_movie() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER_TOKEN);

        //when
        ResponseEntity<MovieResponse> response = testRestTemplate.exchange("/movies/1001", HttpMethod.GET, new HttpEntity<>(httpHeaders), MovieResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody())
                .extracting("name", "genre", "releaseYear", "director")
                .containsExactly("test film 1001", MovieGenre.COMEDY, 2001, "test yönetmen 1001");

        assertThat(response.getBody().getActors())
                .hasSize(2)
                .extracting("id", "name", "birthDate")
                .containsExactly(
                        tuple(2001L, "test actor 2001", LocalDateTime.of(2001, 1, 12, 11, 0, 0)),
                        tuple(2003L, "test actor 2003", LocalDateTime.of(2003, 1, 12, 13, 0, 0))
                );

        //validate-cache
        MovieCache movie = movieRedisTemplate.opsForValue().get("patika:movie:" + 1001);
        assertThat(movie).isNotNull();
        assertThat(movie.getName()).isEqualTo("test film 1001");
        //todo validate other fields
    }

    @Test
    void should_NOT_retrieve_movie() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER_TOKEN);

        //when
        ResponseEntity<ExceptionResponse> response = testRestTemplate.exchange("/movies/99", HttpMethod.GET, new HttpEntity<>(httpHeaders), ExceptionResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody())
                .extracting("code", "message", "detail")
                .containsExactly(1001, "Film bulunamadı", "Movie id:99");
    }

    @Test
    @Sql(scripts = "/movie-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_delete_movie() {
        //given
        Optional<MovieEntity> optionalMovie = movieJpaRepository.findById(1001L);
        assertThat(optionalMovie).isPresent();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER_TOKEN);

        //when
        testRestTemplate.exchange("/movies/1001", HttpMethod.DELETE, new HttpEntity<>(httpHeaders), Void.class);

        //then
        optionalMovie = movieJpaRepository.findById(1001L);
        assertThat(optionalMovie).isEmpty();
    }
}