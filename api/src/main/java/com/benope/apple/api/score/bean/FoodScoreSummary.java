package com.benope.apple.api.score.bean;

import com.benope.apple.domain.food.bean.Food;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodScoreSummary {

    private Long foodNo;
    private BigDecimal score;
    private Long scoreCount;
    private BigDecimal myScore;

    @Builder
    public FoodScoreSummary(Food food, BigDecimal myScore) {
        this.foodNo = food.getFoodNo();
        this.score = food.getAverageScore();
        this.scoreCount = food.getScoreCount();
        this.myScore = myScore;
    }

}
