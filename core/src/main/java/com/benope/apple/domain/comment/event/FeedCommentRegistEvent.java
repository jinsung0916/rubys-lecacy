package com.benope.apple.domain.comment.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.comment.bean.FeedComment;

public class FeedCommentRegistEvent extends BenopeEvent<FeedComment> {
    public FeedCommentRegistEvent(FeedComment source) {
        super(source);
    }
}
