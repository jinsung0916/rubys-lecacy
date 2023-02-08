package com.benope.apple.api.follow.service;

import com.benope.apple.domain.follow.bean.Follow;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface FollowExistsService {

    boolean isFollowExists(@NotNull Follow follow);

}
