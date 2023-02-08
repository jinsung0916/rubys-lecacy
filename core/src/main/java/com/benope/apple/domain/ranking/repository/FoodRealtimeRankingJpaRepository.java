package com.benope.apple.domain.ranking.repository;

import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FoodRealtimeRankingJpaRepository extends JpaRepository<FoodRealtimeRanking, Long>, JpaSpecificationExecutor<FoodRealtimeRanking> {

    @Modifying
    @Query("DELETE FROM FoodRealtimeRanking WHERe rankDate = :rankDate")
    void deleteByRankDate(LocalDate rankDate);

}
