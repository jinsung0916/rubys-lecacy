package com.benope.apple.api.score.service;

import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreSummaryView;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ScoreHelperService {

    ScoreSummaryView getSummary(Score score);

}
