package com.benope.apple.api.follow.service;

import com.benope.apple.domain.follow.bean.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface FollowService {

    Page<Follow> getList(@NotNull Follow follow, @NotNull Pageable pageable);

    Optional<Follow> getOne(@NotNull Follow follow);

    @NotNull Follow regist(@NotNull Follow follow);

    @PostAuthorize("returnObject.name == authentication.name")
    @NotNull Follow deleteById(@NotNull Long followNo);

}
