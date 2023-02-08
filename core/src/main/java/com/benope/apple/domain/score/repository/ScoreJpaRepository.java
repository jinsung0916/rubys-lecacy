package com.benope.apple.domain.score.repository;

import com.benope.apple.domain.score.bean.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ScoreJpaRepository extends JpaRepository<Score, Long>, JpaSpecificationExecutor<Score>, QuerydslPredicateExecutor<Score> {
}
