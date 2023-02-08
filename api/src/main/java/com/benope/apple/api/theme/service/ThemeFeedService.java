package com.benope.apple.api.theme.service;

import com.benope.apple.domain.theme.bean.ThemeFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ThemeFeedService {

    Page<ThemeFeed> getList(@NotNull ThemeFeed themeFeed, @NotNull Pageable pageable);

}
