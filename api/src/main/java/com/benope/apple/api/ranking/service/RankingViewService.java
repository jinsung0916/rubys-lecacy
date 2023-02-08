package com.benope.apple.api.ranking.service;

import com.benope.apple.domain.ranking.bean.Ranking;
import com.benope.apple.domain.ranking.bean.RankingView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface RankingViewService {

    Page<RankingView> getList(@NotNull Ranking ranking, @NotNull Pageable pageable);

}
