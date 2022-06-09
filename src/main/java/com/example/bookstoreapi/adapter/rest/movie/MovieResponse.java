package com.example.bookstoreapi.adapter.rest.movie;

import com.example.bookstoreapi.adapter.rest.actor.ActorResponse;
import com.example.bookstoreapi.domain.actor.Actor;
import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.movie.MovieGenre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Long id;
    private String name;
    private MovieGenre genre;
    private Integer releaseYear;
    private String director;
    private List<ActorResponse> actors;

    public static MovieResponse convertFrom(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .director(movie.getDirector())
                .build();
    }

    public static MovieResponse convertFrom(Movie movie, List<Actor> actors) {
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .director(movie.getDirector())
                .actors(ActorResponse.from(actors))
                .build();
    }
}
