package com.benope.apple.api.food.serviceImpl;

import com.benope.apple.api.food.service.FoodScoreViewService;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodScoreView;
import com.benope.apple.domain.food.repository.FoodQuerydslPredicates;
import com.benope.apple.domain.food.repository.FoodScoreViewJpaRepositorySupport;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodScoreViewServiceImpl implements FoodScoreViewService {

    private final FoodScoreViewJpaRepositorySupport repositorySupport;

    @Transactional(readOnly = true)
    @Override
    public Page<FoodScoreView> getRandomList(Food food, Pageable pageable) {
        Predicate predicate = FoodQuerydslPredicates.eq(food);
        return repositorySupport.getRandomList(predicate, food.getRandNum(), pageable);
    }

}
