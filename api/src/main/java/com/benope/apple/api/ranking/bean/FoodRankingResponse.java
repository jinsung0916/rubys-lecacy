package com.benope.apple.api.ranking.bean;

import com.benope.apple.domain.ranking.bean.FoodRanking;
import com.benope.apple.domain.ranking.bean.RankingView;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRankingResponse {

    private Long rankNum;
    private Long foodNo;
    private String foodNm;
    private String brand;
    private String frontImgUrl;
    private BigDecimal averageScore;
    private Long scoreCount;

    private Integer status;

    public static FoodRankingResponse fromEntity(FoodRanking entity) {
        return FoodRankingResponse.builder()
                .rankNum(entity.getRankNum())
                .foodNo(entity.getFoodNo())
                .foodNm(entity.getFoodNm())
                .brand(entity.getBrand())
                .frontImgUrl(entity.getFrontImgUrl())
                .averageScore(entity.getFoodRankingData().toAverageScore())
                .scoreCount(entity.getFoodRankingData().getScoreCount())
                .build();
    }

    public static FoodRankingResponse fromEntity(RankingView view) {
        FoodRankingResponse response = fromEntity((FoodRanking) view.getNow());
        response.status = view.getStatus();
        return response;
    }

}
