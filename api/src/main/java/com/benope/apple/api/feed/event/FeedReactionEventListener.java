package com.benope.apple.api.feed.event;

import com.benope.apple.api.feed.service.CountFeedService;
import com.benope.apple.domain.reaction.bean.FeedReaction;
import com.benope.apple.domain.reaction.event.FeedReactionDeleteEvent;
import com.benope.apple.domain.reaction.event.FeedReactionRegistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FeedReactionEventListener {

    private final CountFeedService countFeedService;

    @EventListener
    @Transactional
    public void handleEvent(FeedReactionRegistEvent e) {
        FeedReaction reaction = e.getSource();

        if (reaction.isLike()) {
            countFeedService.plusLikeCnt(reaction.getFeedNo());
        }
    }

    @EventListener
    @Transactional
    public void handleEvent(FeedReactionDeleteEvent e) {
        FeedReaction reaction = e.getSource();

        if (reaction.isLike()) {
            countFeedService.minusLikeCnt(reaction.getFeedNo());
        }
    }

}
