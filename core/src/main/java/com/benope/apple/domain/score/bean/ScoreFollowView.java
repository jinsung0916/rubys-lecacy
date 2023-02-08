package com.benope.apple.domain.score.bean;

import com.benope.apple.domain.follow.bean.Follow;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ScoreFollowView {

    private final Score score;
    private final Follow follow;

    @QueryProjection
    public ScoreFollowView(Score score, Follow follow) {
        this.score = score;
        this.follow = follow;
    }

}
