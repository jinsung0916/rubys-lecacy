package com.benope.apple.api.food.service;

import com.benope.apple.domain.food.bean.Food;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface FoodService {

    Optional<Food> getById(@NotNull Long foodNo, boolean isViewCounted);

    Food update(@NotNull Food food);

}
