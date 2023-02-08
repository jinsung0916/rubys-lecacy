package com.benope.apple.domain.comment.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.comment.bean.FeedComment;

public class FeedCommentDeleteEvent extends BenopeEvent<FeedComment> {
    public FeedCommentDeleteEvent(FeedComment source) {
        super(source);
    }
}
