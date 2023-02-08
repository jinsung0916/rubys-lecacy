package com.benope.apple.domain.comment.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.comment.bean.CommunityComment;

public class CommunityCommentDeleteEvent extends BenopeEvent<CommunityComment> {
    public CommunityCommentDeleteEvent(CommunityComment source) {
        super(source);
    }
}
