package com.benope.apple.api.score.service;

import com.benope.apple.domain.score.bean.Score;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ScoreService {

    Optional<Score> getOne(@NotNull Score score);

    Score score(@NotNull Score score);

    Score unScore(@NotNull Score score);

}
