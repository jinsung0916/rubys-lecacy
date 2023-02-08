package com.benope.apple.domain.banner.repository;

import com.benope.apple.domain.banner.bean.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BannerJpaRepository extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner>, QuerydslPredicateExecutor<Banner> {
}
