package com.benope.apple.domain.score.bean;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ScoreSummaryView {

    private final BigDecimal totalScore;
    private final Long scoreCount;

    @QueryProjection
    public ScoreSummaryView(BigDecimal totalScore, Long scoreCount) {
        this.totalScore = totalScore;
        this.scoreCount = scoreCount;
    }

}
