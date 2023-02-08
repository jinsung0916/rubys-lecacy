package com.benope.apple.api.score.serviceImpl;

import com.benope.apple.api.score.service.ScoreHelperService;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreSummaryView;
import com.benope.apple.domain.score.repository.ScoreQuerydslPredicates;
import com.benope.apple.domain.score.repository.ScoreSummaryViewJpaRepositorySupport;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreHelperServiceImpl implements ScoreHelperService {

    private final ScoreSummaryViewJpaRepositorySupport scoreSummaryViewJpaRepositorySupport;

    @Transactional(readOnly = true)
    @Override
    public ScoreSummaryView getSummary(Score score) {
        Score cond = Score.builder()
                .foodNo(score.getFoodNo())
                .build();

        Predicate predicate = ScoreQuerydslPredicates.eq(cond);
        return scoreSummaryViewJpaRepositorySupport.findOne(predicate);
    }

}
