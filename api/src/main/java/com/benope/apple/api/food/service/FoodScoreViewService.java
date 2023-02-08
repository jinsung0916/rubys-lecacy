package com.benope.apple.api.food.service;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodScoreView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface FoodScoreViewService {

    Page<FoodScoreView> getRandomList(@NotNull Food food, @NotNull Pageable pageable);

}
