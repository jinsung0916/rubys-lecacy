package com.benope.apple.api.food.service;

import com.benope.apple.domain.food.bean.FoodTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface TagService {

    Page<FoodTag> getList(@NotNull FoodTag tag, @NotNull Pageable pageable);

    long countDistinct(@NotNull FoodTag tag);

    FoodTag regist(@NotNull FoodTag tag);

}
