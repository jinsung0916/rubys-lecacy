package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.QFood;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import java.util.Objects;

public class FoodQuerydslPredicates {

    private static final QFood qFood = QFood.food;

    public static Predicate eq(Food food) {
        return ExpressionUtils.allOf(
            eqFoodCategoryNo(food.getFoodCategoryNo())
        );
    }

    private static Predicate eqFoodCategoryNo(Long foodCategoryNo) {
        return Objects.nonNull(foodCategoryNo) ? qFood.foodCategoryNo.eq(foodCategoryNo) : null;
    }

}
