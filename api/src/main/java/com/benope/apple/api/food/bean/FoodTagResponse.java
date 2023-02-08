package com.benope.apple.api.food.bean;

import com.benope.apple.domain.food.bean.FoodTag;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodTagResponse {

    private String brand;
    private Long foodNo;
    private String foodNm;
    private String frontImgUrl;

    public static FoodTagResponse fromEntity(FoodTag entity) {
        return FoodTagResponse.builder()
                .foodNo(entity.getFoodNo())
                .brand(entity.getBrand())
                .foodNm(entity.getFoodNm())
                .frontImgUrl(entity.getFrontImgUrl())
                .build();
    }

}
