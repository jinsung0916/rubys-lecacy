package com.benope.apple.api.banner.service;

import com.benope.apple.domain.banner.bean.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface BannerService {

    Page<Banner> getList(@NotNull Banner banner, @NotNull Pageable pageable);

}
