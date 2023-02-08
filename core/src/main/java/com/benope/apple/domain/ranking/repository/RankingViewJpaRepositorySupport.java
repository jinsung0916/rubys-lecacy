package com.benope.apple.domain.ranking.repository;

import com.benope.apple.config.domain.RowStatCd;
import com.benope.apple.domain.ranking.bean.QRanking;
import com.benope.apple.domain.ranking.bean.QRankingView;
import com.benope.apple.domain.ranking.bean.Ranking;
import com.benope.apple.domain.ranking.bean.RankingView;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RankingViewJpaRepositorySupport extends QuerydslRepositorySupport {

    private static final QRanking now = QRanking.ranking;
    private static final QRanking prev = new QRanking("prev");

    public RankingViewJpaRepositorySupport(EntityManager em) {
        super(Ranking.class);
        setEntityManager(em);
    }

    public Page<RankingView> findAll(Predicate predicate, LocalDate yesterday, Pageable pageable) {
        JPQLQuery<RankingView> query = getQuerydsl().createQuery()
                .select(new QRankingView(now, prev))
                .from(now)
                .leftJoin(prev).on(ExpressionUtils.allOf(
                        prev.categoryNo.eq(now.categoryNo),
                        prev.rankingTypeCd.eq(now.rankingTypeCd),
                        prev.objectNo.eq(now.objectNo),
                        prev.rankDate.eq(yesterday),
                        prev.rowStatCd.ne(RowStatCd.D)
                ))
                .where(predicate);

        long totalCount = query.fetchCount();
        List<RankingView> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

}
