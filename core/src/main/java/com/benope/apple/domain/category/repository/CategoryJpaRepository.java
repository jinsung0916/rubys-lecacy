package com.benope.apple.domain.category.repository;

import com.benope.apple.domain.category.bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryJpaRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
