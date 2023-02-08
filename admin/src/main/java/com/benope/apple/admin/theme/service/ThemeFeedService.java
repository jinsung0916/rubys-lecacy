package com.benope.apple.admin.theme.service;

import com.benope.apple.domain.theme.bean.ThemeFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface ThemeFeedService {

    Page<ThemeFeed> getList(@NotNull ThemeFeed themeFeed, @NotNull Pageable pageable);

    Optional<ThemeFeed> getOne(@NotNull ThemeFeed themeFeed);

    List<ThemeFeed> getByIds(@NotNull List<Long> ids);

    @NotNull ThemeFeed regist(@NotNull ThemeFeed themeFeed);

    @NotNull ThemeFeed update(@NotNull ThemeFeed themeFeed);

    void deleteById(@NotNull Long themeFeedNo);

}
