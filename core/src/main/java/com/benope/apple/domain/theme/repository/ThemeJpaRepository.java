package com.benope.apple.domain.theme.repository;

import com.benope.apple.domain.theme.bean.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long>, JpaSpecificationExecutor<Theme>, QuerydslPredicateExecutor<Theme> {
}
