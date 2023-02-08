package com.benope.apple.domain.ranking.repository;

import com.benope.apple.domain.ranking.bean.FoodRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FoodRankingJpaRepository extends JpaRepository<FoodRanking, Long>, JpaSpecificationExecutor<FoodRanking> {

    @Modifying
    @Query("DELETE FROM FoodRanking WHERe rankDate = :rankDate")
    void deleteByRankDate(LocalDate rankDate);

}
