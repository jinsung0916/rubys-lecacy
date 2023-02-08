package com.benope.apple.admin.category.service;

import com.benope.apple.domain.category.bean.RankingCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface RankingCategoryService {

    Page<RankingCategory> getList(@NotNull RankingCategory category, @NotNull Pageable pageable);

    Optional<RankingCategory> getOne(@NotNull RankingCategory category);

    List<RankingCategory> getByIds(@NotNull List<Long> ids);

    RankingCategory regist(@NotNull RankingCategory category);

    RankingCategory update(@NotNull RankingCategory category);

    void deleteById(@NotNull Long categoryNo);

}
