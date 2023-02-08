package com.benope.apple.admin.score.service;

import com.benope.apple.domain.score.bean.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ScoreService {

    Page<Score> getList(@NotNull Score score, @NotNull Pageable pageable);

    List<Score> getByIds(@NotNull List<Long> ids);

}
