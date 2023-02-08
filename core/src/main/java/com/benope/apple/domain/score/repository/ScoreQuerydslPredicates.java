package com.benope.apple.domain.score.repository;

import com.benope.apple.domain.follow.bean.QFollow;
import com.benope.apple.domain.food.bean.QFood;
import com.benope.apple.domain.score.bean.QScore;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.utils.SessionUtil;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ScoreQuerydslPredicates {

    private static final QScore qScore = QScore.score;
    private static final QFood qFood = QFood.food;
    private static final QFollow qFollow = QFollow.follow;

    public static Predicate eq(Score score) {
        return ExpressionUtils.allOf(
                eqMbNo(score.getMbNo()),
                eqFoodNo(score.getFoodNo())
        );
    }

    private static Predicate eqMbNo(Long mbNo) {
        return Objects.nonNull(mbNo) ? qScore.mbNo.eq(mbNo) : null;
    }

    private static Predicate eqFoodNo(Long foodNo) {
        return Objects.nonNull(foodNo) ? qScore.foodNo.eq(foodNo) : null;
    }

    public static Predicate foodExists() {
        return JPAExpressions
                .select()
                .from(qFood)
                .where(qFood.foodNo.eq(qScore.foodNo))
                .exists();
    }

    public static Predicate followerExists() {
        return JPAExpressions
                .selectFrom(qFollow)
                .where(ExpressionUtils.allOf(
                        qFollow.mbNo.eq(SessionUtil.getSessionMbNo()),
                        qFollow.followMbNo.eq(qScore.mbNo)
                ))
                .exists();
    }

    public static Predicate createdAtOrUpdatedAtBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = Objects.requireNonNullElse(startDate, LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDateTime = Objects.requireNonNullElse(endDate, LocalDate.MAX).atTime(LocalTime.MAX);


        return ExpressionUtils.or(
                qScore.createdAt.between(startDateTime, endDateTime),
                qScore.updatedAt.between(startDateTime, endDateTime)
        );
    }

}
