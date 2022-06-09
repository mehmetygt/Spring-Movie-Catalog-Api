package com.example.bookstoreapi.adapter.jpa.rate;

import com.example.bookstoreapi.domain.port.RatePersistencePort;
import com.example.bookstoreapi.domain.rate.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateJpaAdapter implements RatePersistencePort {

    private final RateJpaRepository rateJpaRepository;

    @Override
    public void rateToMovie(Rate rate) {
        RateEntity rateEntity = new RateEntity();
        rateJpaRepository.save(rateEntity);
    }

    @Override
    public List<Rate> retrieveByMovieId(Long movieId) {
        return rateJpaRepository.retrieve(movieId)
                .stream()
                .map(RateEntity::toModel)
                .collect(Collectors.toList());
    }
}
