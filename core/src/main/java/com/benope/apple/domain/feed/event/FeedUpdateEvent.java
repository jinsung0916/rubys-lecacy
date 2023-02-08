package com.benope.apple.domain.feed.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.feed.bean.Feed;

public class FeedUpdateEvent extends BenopeEvent<Feed> {
    public FeedUpdateEvent(Feed source) {
        super(source);
    }
}
