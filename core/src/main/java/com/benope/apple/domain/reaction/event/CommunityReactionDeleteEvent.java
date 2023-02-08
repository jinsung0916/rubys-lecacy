package com.benope.apple.domain.reaction.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.reaction.bean.CommunityReaction;

public class CommunityReactionDeleteEvent extends BenopeEvent<CommunityReaction> {
    public CommunityReactionDeleteEvent(CommunityReaction source) {
        super(source);
    }
}
