package com.benope.apple.domain.score.repository;

import com.benope.apple.config.domain.RowStatCd;
import com.benope.apple.domain.follow.bean.QFollow;
import com.benope.apple.domain.score.bean.QScore;
import com.benope.apple.domain.score.bean.QScoreFollowView;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreFollowView;
import com.benope.apple.utils.SessionUtil;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ScoreFollowViewJpaRepositorySupport extends QuerydslRepositorySupport {

    private static final QScore qScore = QScore.score;
    private static final QFollow qFollow = QFollow.follow;

    public ScoreFollowViewJpaRepositorySupport(EntityManager em) {
        super(Score.class);
        setEntityManager(em);
    }

    public Page<ScoreFollowView> findAll(Predicate predicate, Pageable pageable) {
        JPQLQuery<ScoreFollowView> query = getQuerydsl().createQuery()
                .select(new QScoreFollowView(qScore, qFollow))
                .from(qScore)
                .leftJoin(qFollow).on(ExpressionUtils.allOf(
                        eqSessionMbNo(),
                        qFollow.followMbNo.eq(qScore.mbNo),
                        qFollow.rowStatCd.ne(RowStatCd.D)
                ))
                .where(ExpressionUtils.allOf(
                        predicate,
                        ScoreQuerydslPredicates.foodExists()
                ));

        long totalCount = query.fetchCount();
        List<ScoreFollowView> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    private Predicate eqSessionMbNo() {
        return SessionUtil.isAuthenticated()
                ? qFollow.mbNo.eq(SessionUtil.getSessionMbNo())
                : Expressions.asBoolean(true).isFalse(); // Always false
    }

}
