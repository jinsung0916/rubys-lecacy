package com.benope.apple.domain.feed.event;

import com.benope.apple.config.domain.BenopeEvent;

public class FeedCountUpdateEvent extends BenopeEvent<Long> {
    public FeedCountUpdateEvent(Long feedNo) {
        super(feedNo);
    }
}
