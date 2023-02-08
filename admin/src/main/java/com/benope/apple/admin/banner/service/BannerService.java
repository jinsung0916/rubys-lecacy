package com.benope.apple.admin.banner.service;

import com.benope.apple.domain.banner.bean.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface BannerService {

    Page<Banner> getList(@NotNull Banner banner, @NotNull Pageable pageable);

    Optional<Banner> getOne(@NotNull Banner banner);

    List<Banner> getByIds(@NotNull List<Long> ids);

    Banner regist(@NotNull Banner banner);

    Banner update(@NotNull Banner banner);

    void deleteById(@NotNull Long bannerNo);

}
