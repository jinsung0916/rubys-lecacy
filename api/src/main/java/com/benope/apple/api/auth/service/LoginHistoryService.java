package com.benope.apple.api.auth.service;

import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface LoginHistoryService {

    Page<LoginHistory> getList(@NotNull LoginHistory loginHistory, @NotNull Pageable pageable);

    Optional<LoginHistory> getOne(@NotNull LoginHistory loginHistory);

    @NotNull LoginHistory regist(@NotNull LoginHistory loginHistory);

    @NotNull LoginHistory update(@NotNull LoginHistory loginHistory);

    void deleteByRefreshToken(@NotBlank String sessionId);

    void deleteByMbNo(@NotNull Long mbNo);

}