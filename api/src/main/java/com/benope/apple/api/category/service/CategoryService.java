package com.benope.apple.api.category.service;

import com.benope.apple.domain.category.bean.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface CategoryService {

    Page<Category> getList(@NotNull Category category, @NotNull Pageable pageable);

}
