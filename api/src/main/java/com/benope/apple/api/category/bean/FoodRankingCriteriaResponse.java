package com.benope.apple.api.category.bean;

import com.benope.apple.domain.category.bean.RankingCategory;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRankingCriteriaResponse {

    private Long rankingCriteriaNo;
    private String criteriaNm;
    private String iconImgUrl;

    public static FoodRankingCriteriaResponse fromEntity(RankingCategory entity) {
        return FoodRankingCriteriaResponse.builder()
                .rankingCriteriaNo(entity.getCategoryNo())
                .criteriaNm(entity.getCategoryNm())
                .iconImgUrl(entity.getIconImgUrl())
                .build();
    }

}
