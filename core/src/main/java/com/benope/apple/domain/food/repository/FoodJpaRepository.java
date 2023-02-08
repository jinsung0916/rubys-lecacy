package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.stream.Stream;

public interface FoodJpaRepository extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food>, QuerydslPredicateExecutor<Food> {

    @Query("SELECT f FROM Food  f")
    Stream<Food> streamAll();

}
