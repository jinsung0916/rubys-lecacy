package com.benope.apple.api.ranking.serviceImpl;

import com.benope.apple.api.ranking.service.FoodRealtimeRankingService;
import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import com.benope.apple.domain.ranking.repository.FoodRealtimeRankingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodRealtimeRankingServiceImpl implements FoodRealtimeRankingService {

    private final FoodRealtimeRankingJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<FoodRealtimeRanking> getList(FoodRealtimeRanking foodRealtimeRanking, Pageable pageable) {
        Example<FoodRealtimeRanking> example = Example.of(foodRealtimeRanking, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

}
