package com.benope.apple.domain.reaction.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.reaction.bean.FeedReaction;

public class FeedReactionRegistEvent extends BenopeEvent<FeedReaction> {
    public FeedReactionRegistEvent(FeedReaction source) {
        super(source);
    }
}
