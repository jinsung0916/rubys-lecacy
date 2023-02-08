package com.benope.apple.api.ranking.bean;

import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRealtimeRankingResponse {

    private Long rankNum;
    private Long foodNo;
    private String foodNm;
    private String brand;
    private String frontImgUrl;

    public static FoodRealtimeRankingResponse fromEntity(FoodRealtimeRanking entity) {
        return FoodRealtimeRankingResponse.builder()
                .rankNum(entity.getRankNum())
                .foodNo(entity.getFoodNo())
                .foodNm(entity.getFoodNm())
                .brand(entity.getBrand())
                .frontImgUrl(entity.getFrontImgUrl())
                .build();
    }

}
