package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.domain.food.bean.FoodTagTargetCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FoodTagJpaRepository extends JpaRepository<FoodTag, Long>, JpaSpecificationExecutor<FoodTag>, QuerydslPredicateExecutor<FoodTag> {

    void deleteByMbNoAndFoodTagTargetCdAndObjectNo(Long mbNo, FoodTagTargetCd foodTagTargetCd, Long feedNo);

}
