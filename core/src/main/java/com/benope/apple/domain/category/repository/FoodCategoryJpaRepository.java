package com.benope.apple.domain.category.repository;

import com.benope.apple.domain.category.bean.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoodCategoryJpaRepository extends JpaRepository<FoodCategory, Long>, JpaSpecificationExecutor<FoodCategory> {
}
