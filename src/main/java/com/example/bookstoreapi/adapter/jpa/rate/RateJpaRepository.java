package com.example.bookstoreapi.adapter.jpa.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RateJpaRepository extends JpaRepository<RateEntity, Long> {

    List<RateEntity> findAllByMovieId(Long movieId);

    //@Query(value = "SELECT * FROM rate WHERE movie_id = :movieId", nativeQuery = true)
    @Query(value = "Select r FROM rateEntity as r WHERE r.movie.id = :movieId")
    List<RateEntity> retrieve(@Param("movieId") Long movieId);
}
