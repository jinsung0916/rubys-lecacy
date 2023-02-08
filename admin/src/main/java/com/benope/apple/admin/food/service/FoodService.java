package com.benope.apple.admin.food.service;

import com.benope.apple.domain.food.bean.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface FoodService {

    Page<Food> getList(@NotNull Food food, @NotNull Pageable pageable);

    Optional<Food> getOne(@NotNull Food food);

    List<Food> getByIds(@NotNull List<Long> ids);

    long count(Food food);

    @NotNull Food regist(@NotNull Food food);

    @NotNull Food update(@NotNull Food food);

    void deleteById(@NotNull Long foodNo);

}
