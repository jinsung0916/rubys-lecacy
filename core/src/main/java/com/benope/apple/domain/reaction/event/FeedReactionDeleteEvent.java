package com.benope.apple.domain.reaction.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.reaction.bean.FeedReaction;

public class FeedReactionDeleteEvent extends BenopeEvent<FeedReaction> {
    public FeedReactionDeleteEvent(FeedReaction source) {
        super(source);
    }
}
