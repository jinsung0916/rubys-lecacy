package com.benope.apple.api.ranking.serviceImpl;

import com.benope.apple.api.ranking.service.RankingViewService;
import com.benope.apple.domain.ranking.bean.Ranking;
import com.benope.apple.domain.ranking.bean.RankingView;
import com.benope.apple.domain.ranking.repository.RankingQuerydslPredicates;
import com.benope.apple.domain.ranking.repository.RankingViewJpaRepositorySupport;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingViewServiceImpl implements RankingViewService {

    private final RankingViewJpaRepositorySupport repositorySupport;

    @Transactional(readOnly = true)
    @Override
    public Page<RankingView> getList(Ranking ranking, Pageable pageable) {
        Predicate predicate = RankingQuerydslPredicates.eq(ranking);
        return repositorySupport.findAll(predicate, ranking.getRankDate().minusDays(1), pageable);
    }

}
