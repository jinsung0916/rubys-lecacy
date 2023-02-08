package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.domain.food.bean.QFood;
import com.benope.apple.domain.food.bean.QFoodTag;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class FoodTagQuerydslPredicates {

    private static final QFoodTag qFoodTag = QFoodTag.foodTag;
    private static final QFood qFood = QFood.food;

    public static Predicate eq(FoodTag foodTag) {
        return ExpressionUtils.allOf(
                eqFoodNo(foodTag.getFoodNo()),
                eqMbNo(foodTag.getMbNo())
        );
    }

    private static Predicate eqFoodNo(Long foodNo) {
        return Objects.nonNull(foodNo) ? qFoodTag.foodNo.eq(foodNo) : null;
    }

    private static Predicate eqMbNo(Long mbNo) {
        return Objects.nonNull(mbNo) ? qFoodTag.mbNo.eq(mbNo) : null;
    }

    public static Predicate createdAtBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = Objects.requireNonNullElse(startDate, LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDateTime = Objects.requireNonNullElse(endDate, LocalDate.MAX).atTime(LocalTime.MAX);

        return qFoodTag.createdAt.between(startDateTime, endDateTime);
    }

    public static Predicate foodExists() {
        return JPAExpressions.select().from(qFood).where(qFood.foodNo.eq(qFoodTag.foodNo)).exists();
    }

}
