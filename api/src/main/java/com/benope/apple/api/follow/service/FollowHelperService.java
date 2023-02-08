package com.benope.apple.api.follow.service;

import com.benope.apple.domain.follow.bean.Follow;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface FollowHelperService {

    boolean checkFollow(@NotNull Long mbNo, @NotNull Long followMbNo);

    @NotNull
    Follow unFollow(@NotNull Long mbNo, @NotNull Long followMbNo);

}
