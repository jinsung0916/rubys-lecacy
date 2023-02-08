package com.benope.apple.domain.ranking.repository;

import com.benope.apple.domain.ranking.bean.QRanking;
import com.benope.apple.domain.ranking.bean.Ranking;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

public class RankingQuerydslPredicates {

    private static final QRanking qRanking = QRanking.ranking;

    public static Predicate eq(Ranking ranking) {
        return ExpressionUtils.allOf(
                qRanking.categoryNo.eq(ranking.getCategoryNo()),
                qRanking.rankingTypeCd.eq(ranking.getRankingTypeCd()),
                qRanking.rankDate.eq(ranking.getRankDate())
        );
    }

}
