package com.benope.apple.domain.follow.repository;

import com.benope.apple.domain.follow.bean.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FollowJpaRepository extends JpaRepository<Follow, Long>, JpaSpecificationExecutor<Follow>, QuerydslPredicateExecutor<Follow> {
}
