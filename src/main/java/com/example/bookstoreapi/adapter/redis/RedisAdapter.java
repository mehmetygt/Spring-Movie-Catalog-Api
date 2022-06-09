package com.example.bookstoreapi.adapter.redis;

import com.example.bookstoreapi.domain.movie.Movie;
import com.example.bookstoreapi.domain.port.MovieCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisAdapter implements MovieCachePort {

    private final RedisTemplate<String, MovieCache> movieRedisTemplate;

    @Override
    public Optional<Movie> retrieveMovie(Long movieId) {//todo optional
        MovieCache movieCache = movieRedisTemplate.opsForValue().get("patika:movie:" + movieId);

        return Optional.ofNullable(movieCache)
                .map(MovieCache::toModel);
    }

    @Override
    public void createMovie(Movie movie) {
        MovieCache movieCache = MovieCache.from(movie);
        movieRedisTemplate.opsForValue().set("patika:movie:" + movie.getId(), movieCache, Duration.ofSeconds(30));
    }
}
