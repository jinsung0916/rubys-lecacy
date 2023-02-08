package com.benope.apple.api.ranking.bean;

import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRealtimeRankingRequest {

    private static final int DEFAULT_PAGE_SIZE = 6;

    @NotNull
    private LocalDate rankDate;

    public FoodRealtimeRanking toEntity() {
        return FoodRealtimeRanking.builder()
                .rankDate(rankDate)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.ofSize(DEFAULT_PAGE_SIZE).withSort(Sort.by(Sort.Order.asc("rankNum")));
    }

}
