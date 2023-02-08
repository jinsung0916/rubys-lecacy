package com.benope.apple.domain.comment.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.comment.bean.CommunityComment;

public class CommunityCommentRegistEvent extends BenopeEvent<CommunityComment> {
    public CommunityCommentRegistEvent(CommunityComment source) {
        super(source);
    }
}
