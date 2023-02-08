package com.benope.apple.domain.reaction.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.reaction.bean.CommunityReaction;

public class CommunityReactionRegistEvent extends BenopeEvent<CommunityReaction> {
    public CommunityReactionRegistEvent(CommunityReaction source) {
        super(source);
    }
}
