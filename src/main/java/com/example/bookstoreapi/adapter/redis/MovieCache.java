package com.example.bookstoreapi.adapter.redis;

import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.movie.MovieGenre;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCache {

    private Long id;
    private String name;
    private MovieGenre genre;
    private Integer releaseYear;
    private String director;

    public static MovieCache from(Movie movie) {
        return MovieCache.builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .director(movie.getDirector())
                .build();
    }

    public Movie toModel() {
        return Movie.builder()
                .id(id)
                .name(name)
                .genre(genre)
                .releaseYear(releaseYear)
                .director(director)
                .build();
    }
}
