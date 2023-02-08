package com.benope.apple.domain.comment.bean;

import com.benope.apple.domain.score.bean.Score;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CommentScoreView {

    private final Comment comment;
    private final Score score;

    @QueryProjection
    public CommentScoreView(Comment comment, Score score) {
        this.comment = comment;
        this.score = score;
    }

}
