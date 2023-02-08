package com.benope.apple.admin.member.service;

import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface LoginHistoryService {

    Page<LoginHistory> getList(@NotNull LoginHistory loginHistory, @NotNull Pageable pageable);

}
