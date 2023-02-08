package com.benope.apple.api.feed.event;

import com.benope.apple.api.feed.service.CountFeedService;
import com.benope.apple.domain.comment.bean.FeedComment;
import com.benope.apple.domain.comment.event.FeedCommentDeleteEvent;
import com.benope.apple.domain.comment.event.FeedCommentRegistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FeedCommentEventListener {

    private final CountFeedService countFeedService;

    @EventListener
    @Transactional
    public void handleEvent(FeedCommentRegistEvent e) {
        FeedComment comment = e.getSource();

        countFeedService.plusCommentCnt(comment.getObjectNo());
    }

    @EventListener
    @Transactional
    public void handleEvent(FeedCommentDeleteEvent e) {
        FeedComment comment = e.getSource();

        countFeedService.minusCommentCnt(comment.getObjectNo());
    }

}
