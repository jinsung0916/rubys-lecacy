package com.benope.apple.domain.follow.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.follow.bean.Follow;

public class FollowRegistEvent extends BenopeEvent<Follow> {
    public FollowRegistEvent(Follow source) {
        super(source);
    }
}
