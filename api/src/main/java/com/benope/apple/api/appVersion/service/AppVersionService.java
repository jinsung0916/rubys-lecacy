package com.benope.apple.api.appVersion.service;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface AppVersionService {

    Page<AppVersion> getList(@NotNull AppVersion appVersion, @NotNull Pageable pageable);

    Optional<AppVersion> getOne(@NotNull AppVersion appVersion);

}
