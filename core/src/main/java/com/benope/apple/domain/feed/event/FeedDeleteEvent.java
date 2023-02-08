package com.benope.apple.domain.feed.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.feed.bean.Feed;

public class FeedDeleteEvent extends BenopeEvent<Feed> {
    public FeedDeleteEvent(Feed source) {
        super(source);
    }
}
