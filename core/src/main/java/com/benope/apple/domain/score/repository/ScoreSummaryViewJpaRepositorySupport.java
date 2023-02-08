package com.benope.apple.domain.score.repository;

import com.benope.apple.domain.score.bean.QScore;
import com.benope.apple.domain.score.bean.QScoreSummaryView;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreSummaryView;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@Repository
public class ScoreSummaryViewJpaRepositorySupport extends QuerydslRepositorySupport {

    private static final QScore qScore = QScore.score;

    public ScoreSummaryViewJpaRepositorySupport(EntityManager em) {
        super(Score.class);
        setEntityManager(em);
    }

    public ScoreSummaryView findOne(Predicate predicate) {
        return getQuerydsl().createQuery()
                .select(new QScoreSummaryView(sumOfScoreValue(), countAll()))
                .from(qScore)
                .where(predicate)
                .fetchOne();
    }

    private Expression<? extends BigDecimal> sumOfScoreValue() {
        return qScore.scoreValue.sum().nullif(BigDecimal.ZERO);
    }

    private Expression<Long> countAll() {
        return qScore.count().nullif(0L);
    }

}
