package com.benope.apple.api.ranking.bean;

import com.benope.apple.domain.ranking.bean.FoodRanking;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRankingRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    @NotNull
    private Long rankingCriteriaNo;
    @NotNull
    private LocalDate rankDate;

    public FoodRanking toEntity() {
        return FoodRanking.builder()
                .categoryNo(rankingCriteriaNo)
                .rankDate(rankDate)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.asc("rankNum")));
    }

}
