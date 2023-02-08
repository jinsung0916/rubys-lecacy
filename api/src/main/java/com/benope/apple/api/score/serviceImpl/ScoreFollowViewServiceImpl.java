package com.benope.apple.api.score.serviceImpl;

import com.benope.apple.api.score.service.ScoreFollowViewService;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreFollowView;
import com.benope.apple.domain.score.repository.ScoreFollowViewJpaRepositorySupport;
import com.benope.apple.domain.score.repository.ScoreQuerydslPredicates;
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
public class ScoreFollowViewServiceImpl implements ScoreFollowViewService {

    private final ScoreFollowViewJpaRepositorySupport repositorySupport;

    @Transactional(readOnly = true)
    @Override
    public Page<ScoreFollowView> getList(Score score, Pageable pageable) {
        Predicate predicate = ScoreQuerydslPredicates.eq(score);
        return repositorySupport.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ScoreFollowView> getFollowerList(Score score, Pageable pageable) {
        Predicate predicate = ExpressionUtils.allOf(
                ScoreQuerydslPredicates.eq(score),
                ScoreQuerydslPredicates.followerExists()
        );

        return repositorySupport.findAll(predicate, pageable);
    }

}
