package com.benope.apple.api.theme.service;

import com.benope.apple.domain.theme.bean.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ThemeService {

    Page<Theme> getList(@NotNull Theme theme, @NotNull Pageable pageable);

    Optional<Theme> getOne(@NotNull Theme theme);

}
