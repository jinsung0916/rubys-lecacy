package com.benope.apple.api.ranking.service;

import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface FoodRealtimeRankingService {

    Page<FoodRealtimeRanking> getList(@NotNull FoodRealtimeRanking foodRealtimeRanking, @NotNull Pageable pageable);

}
