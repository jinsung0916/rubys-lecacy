package com.benope.apple.api.food.bean;

import com.benope.apple.domain.food.bean.Food;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRequest {

    @NotNull
    private Long foodNo;

    public Food toEntity() {
        return Food.builder()
                .foodNo(foodNo)
                .build();
    }

}
