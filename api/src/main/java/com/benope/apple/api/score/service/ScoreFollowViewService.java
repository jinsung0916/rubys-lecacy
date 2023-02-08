package com.benope.apple.api.score.service;

import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreFollowView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ScoreFollowViewService {

    Page<ScoreFollowView> getList(@NotNull Score score, @NotNull Pageable pageable);

    Page<ScoreFollowView> getFollowerList(@NotNull Score score, @NotNull Pageable pageable);

}
