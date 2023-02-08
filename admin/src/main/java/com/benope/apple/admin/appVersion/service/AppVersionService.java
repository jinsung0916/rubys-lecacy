package com.benope.apple.admin.appVersion.service;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface AppVersionService {

    Page<AppVersion> getList(@NotNull AppVersion appVersion, @NotNull Pageable pageable);

    Optional<AppVersion> getOne(@NotNull AppVersion appVersion);

    List<AppVersion> getByIds(@NotNull List<Long> ids);

    AppVersion regist(@NotNull AppVersion appVersion);

    AppVersion update(@NotNull AppVersion appVersion);

    void deleteById(@NotNull Long appVersionNo);

}
