package com.benope.apple.api.score.service;

import com.benope.apple.domain.score.bean.Score;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ScoreExistsService {

    boolean isExists(@NotNull Score score);

}
