package com.example.bookstoreapi.adapter.jpa.movie;

import com.example.bookstoreapi.adapter.jpa.common.BaseEntity;

import com.example.bookstoreapi.adapter.jpa.common.Status;
import com.example.bookstoreapi.adapter.jpa.rate.RateEntity;
import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.movie.MovieGenre;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "movie")
@Table(name = "movie")
@Where(clause = "status <> 'DELETED'")
public class MovieEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    private Integer releaseYear;

    @Column(nullable = false)
    private String director;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<RateEntity> rates;

    public static MovieEntity from(Movie movie) {
        MovieEntity entity = new MovieEntity();
        entity.id = movie.getId();
        entity.status = Status.ACTIVE;
        entity.name = movie.getName();
        entity.genre = movie.getGenre();
        entity.releaseYear = movie.getReleaseYear();
        entity.director = movie.getDirector();
        return entity;
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
