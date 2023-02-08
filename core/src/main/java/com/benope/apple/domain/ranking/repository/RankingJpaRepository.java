package com.benope.apple.domain.ranking.repository;

import com.benope.apple.domain.ranking.bean.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface RankingJpaRepository extends JpaRepository<Ranking, Long>, JpaSpecificationExecutor<Ranking> {

    @Modifying
    @Query("DELETE FROM Ranking WHERE rankDate < :rankDate")
    void deleteByRankDateLessThan(LocalDate rankDate);

}
