package com.benope.apple.api.category.serviceImpl;

import com.benope.apple.api.category.service.RankingCriteriaService;
import com.benope.apple.domain.category.bean.RankingCategory;
import com.benope.apple.domain.category.repository.RankingCategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingCriteriaServiceImpl implements RankingCriteriaService {

    private final RankingCategoryJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<RankingCategory> getList(RankingCategory rankingCriteria) {
        Example<RankingCategory> example = Example.of(rankingCriteria, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example);
    }

}
