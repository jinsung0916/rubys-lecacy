package com.benope.apple.domain.feed.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.feed.bean.Feed;

public class FeedViewEvent extends BenopeEvent<Feed> {
    public FeedViewEvent(Feed source) {
        super(source);
    }
}
