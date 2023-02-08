package com.benope.apple.admin.category.service;

import com.benope.apple.domain.category.bean.FoodCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface FoodCategoryService {

    Page<FoodCategory> getList(@NotNull FoodCategory category, @NotNull Pageable pageable);

    Optional<FoodCategory> getOne(@NotNull FoodCategory category);

    List<FoodCategory> getByIds(@NotNull List<Long> ids);

    FoodCategory regist(@NotNull FoodCategory category);

    FoodCategory update(@NotNull FoodCategory category);

    void deleteById(@NotNull Long categoryNo);

}
