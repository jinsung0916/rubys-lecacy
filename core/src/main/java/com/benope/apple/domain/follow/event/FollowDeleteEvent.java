package com.benope.apple.domain.follow.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.follow.bean.Follow;

public class FollowDeleteEvent extends BenopeEvent<Follow> {
    public FollowDeleteEvent(Follow source) {
        super(source);
    }
}
