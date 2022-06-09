package com.example.bookstoreapi;
import com.example.bookstoreapi.adapter.jpa.actor.ActorEntity;
import com.example.bookstoreapi.adapter.jpa.actor.ActorJpaRepository;
import com.example.bookstoreapi.adapter.rest.actor.ActorCreateRequest;
import com.example.bookstoreapi.adapter.rest.actor.ActorCreateResponse;
import com.example.bookstoreapi.adapter.rest.movie.MovieResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ActorControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ActorJpaRepository actorJpaRepository;

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_create_actor() {
        //given
        ActorCreateRequest request = new ActorCreateRequest();
        request.setName("test actor");
        request.setBirthDate(LocalDateTime.of(1990, 1, 12, 13, 0, 0)); //yükselenini hesaplamayacaksak saate gerek yok

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER_TOKEN);

        //when
        ResponseEntity<ActorCreateResponse> response = testRestTemplate.exchange("/actors", HttpMethod.POST, new HttpEntity<>(request, httpHeaders), ActorCreateResponse.class);

        //when
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

        Optional<ActorEntity> actorEntity = actorJpaRepository.findById(response.getBody().getId());
        assertThat(actorEntity).isPresent();
        assertThat(actorEntity.get()).extracting("name", "birthDate")
                .containsExactly("test actor", LocalDateTime.of(1990, 1, 12, 13, 0, 0));
    }

    @Test
    @Sql(scripts = "/movie-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_actor_movies() {
        //when
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER_TOKEN);

        ResponseEntity<MovieResponse[]> response = testRestTemplate.exchange("/actors/2001/movies", HttpMethod.GET, new HttpEntity<>(httpHeaders), MovieResponse[].class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(Arrays.stream(response.getBody()).collect(Collectors.toList()))
                .hasSize(2)
                .extracting("id")
                .containsExactly(1001L, 1002L);
    }
}
