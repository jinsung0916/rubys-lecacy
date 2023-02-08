package com.benope.apple.api.food.serviceImpl;

import com.benope.apple.api.food.service.TagService;
import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.domain.food.repository.FoodTagJpaRepository;
import com.benope.apple.domain.food.repository.FoodTagJpaRepositorySupport;
import com.benope.apple.domain.food.repository.FoodTagQuerydslPredicates;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final FoodTagJpaRepository repository;
    private final FoodTagJpaRepositorySupport repositorySupport;

    @Transactional(readOnly = true)
    @Override
    public Page<FoodTag> getList(FoodTag tag, Pageable pageable) {
        Predicate predicate = ExpressionUtils.allOf(
                FoodTagQuerydslPredicates.eq(tag),
                FoodTagQuerydslPredicates.foodExists()
        );

        return repository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public long countDistinct(FoodTag tag) {
        Predicate predicate = FoodTagQuerydslPredicates.eq(tag);
        return repositorySupport.countDistinct(predicate);
    }

    @Override
    public FoodTag regist(FoodTag tag) {
        return repository.save(tag);
    }

}
