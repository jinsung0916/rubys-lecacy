package com.benope.apple.admin.score.bean;

import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.score.bean.Score;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreRequest implements IdRequest {

    private List<Long> id;

    private Long mbNo;
    private Long foodNo;
    private String brand;

    public Score toEntity() {
        return Score.builder()
                .mbNo(mbNo)
                .foodNo(foodNo)
                .food(food())
                .build();
    }

    private Food food() {
        if (Objects.isNull(brand)) {
            return null;
        }

        return Food.builder()
                .brand(brand)
                .build();
    }

}
