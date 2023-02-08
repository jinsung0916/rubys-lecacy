package com.benope.apple.api.feed.event;

import com.benope.apple.api.feed.service.CountFeedService;
import com.benope.apple.api.feed.service.FeedService;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.event.*;
import com.benope.apple.domain.feed.repository.FeedSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FeedEventListener {

    private final FeedService feedService;
    private final CountFeedService countFeedService;

    private final FeedSolrRepository feedSolrRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FeedViewEvent e) {
        Feed feed = e.getSource();
        countFeedService.plusViewCnt(feed.getFeedNo());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FeedRegistEvent e) {
        Feed feed = e.getSource();
        Feed refreshed = feedService.getById(feed.getFeedNo(), false).orElseThrow();

        FeedSolrEntity entity = FeedSolrEntity.fromEntity(refreshed);
        feedSolrRepository.save(entity);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FeedUpdateEvent e) {
        Feed feed = e.getSource();
        Feed refreshed = feedService.getById(feed.getFeedNo(), false).orElseThrow();

        FeedSolrEntity entity = FeedSolrEntity.fromEntity(refreshed);
        feedSolrRepository.save(entity);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FeedCountUpdateEvent e) {
        Long feedNo = e.getSource();
        Feed feed = feedService.getById(feedNo, false).orElseThrow();

        FeedSolrEntity entity = FeedSolrEntity.fromEntity(feed);
        feedSolrRepository.save(entity);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FeedDeleteEvent e) {
        Feed feed = e.getSource();
        String id = String.valueOf(feed.getFeedNo());
        feedSolrRepository.deleteById(id);
    }

}
