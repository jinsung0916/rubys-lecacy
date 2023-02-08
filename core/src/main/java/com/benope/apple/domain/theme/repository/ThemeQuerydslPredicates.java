package com.benope.apple.domain.theme.repository;

import com.benope.apple.domain.theme.bean.QTheme;
import com.benope.apple.domain.theme.bean.QThemeFeed;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

public class ThemeQuerydslPredicates {

    private static final QTheme qTheme = QTheme.theme;
    private static final QThemeFeed qThemeFeed = QThemeFeed.themeFeed;

    public static Predicate feedExists(Long feedNo) {
        return JPAExpressions
                .select()
                .from(qThemeFeed)
                .where(ExpressionUtils.allOf(
                        qThemeFeed.themeNo.eq(qTheme.themeNo),
                        qThemeFeed.feedNo.eq(feedNo)
                )).exists();
    }

}
