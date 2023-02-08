package com.benope.apple.domain.category.repository;

import com.benope.apple.domain.category.bean.RankingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RankingCategoryJpaRepository extends JpaRepository<RankingCategory, Long>, JpaSpecificationExecutor<RankingCategory> {
}
