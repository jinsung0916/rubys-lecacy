package com.benope.apple.api.feed.serviceImpl;

import com.benope.apple.api.feed.service.CountFeedService;
import com.benope.apple.domain.feed.event.FeedCountUpdateEvent;
import com.benope.apple.domain.feed.repository.FeedJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CountFeedServiceImpl implements CountFeedService {

    private final FeedJpaRepository repository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void plusViewCnt(Long feedNo) {
        repository.plusViewCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    @Override
    public void plusLikeCnt(Long feedNo) {
        repository.plusLikeCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    @Override
    public void minusLikeCnt(Long feedNo) {
        repository.minusLikeCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    @Override
    public void plusCommentCnt(Long feedNo) {
        repository.plusCommentCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    @Override
    public void minusCommentCnt(Long feedNo) {
        repository.minusCommentCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    @Override
    public void plusScrapCnt(Long feedNo) {
        repository.plusScrapCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    @Override
    public void minusScrapCnt(Long feedNo) {
        repository.minusScrapCnt(feedNo);
        publishFeedUpdateEvent(feedNo);
    }

    private void publishFeedUpdateEvent(Long feedNo) {
        applicationEventPublisher.publishEvent(new FeedCountUpdateEvent(feedNo));
    }

}
