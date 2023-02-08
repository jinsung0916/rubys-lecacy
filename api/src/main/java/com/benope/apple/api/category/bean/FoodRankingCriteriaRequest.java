package com.benope.apple.api.category.bean;

import com.benope.apple.domain.category.bean.RankingCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRankingCriteriaRequest {

    public RankingCategory toEntity() {
        return RankingCategory.builder()
                .build();
    }

}
