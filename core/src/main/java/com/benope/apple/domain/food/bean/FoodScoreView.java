package com.benope.apple.domain.food.bean;

import com.benope.apple.domain.score.bean.Score;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class FoodScoreView {

    private final Food food;
    private final Score score;

    @QueryProjection
    public FoodScoreView(Food food, Score score) {
        this.food = food;
        this.score = score;
    }

}
