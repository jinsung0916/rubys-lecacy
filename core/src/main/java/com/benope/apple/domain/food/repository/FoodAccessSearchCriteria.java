package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodAccessDocument;
import org.springframework.data.elasticsearch.core.query.Criteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class FoodAccessSearchCriteria {

    public static Criteria eq(FoodAccessDocument entity) {
        Criteria criteria = new Criteria();

        if(Objects.nonNull(entity.getFoodNo())) {
            criteria = criteria.and("foodNo").is(entity.getFoodNo());
        }

        return criteria;
    }

    public static Criteria createdAtBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = Objects.requireNonNullElse(startDate, LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDateTime = Objects.requireNonNullElse(endDate, LocalDate.MAX).atTime(LocalTime.MAX);

        return new Criteria("createdAt").between(startDateTime, endDateTime);
    }

}
