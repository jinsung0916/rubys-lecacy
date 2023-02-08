package com.benope.apple.api.feed.event;

import com.benope.apple.api.feed.service.CountFeedService;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.event.ScrapDeleteEvent;
import com.benope.apple.domain.scrap.event.ScrapRegistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FeedScrapEventListener {

    private final CountFeedService countFeedService;

    @EventListener
    @Transactional
    public void handleEvent(ScrapRegistEvent event) {
        Scrap scrap = event.getSource();
        Long feedNo = scrap.getFeedNo();

        countFeedService.plusScrapCnt(feedNo);
    }

    @EventListener
    @Transactional
    public void handleEvent(ScrapDeleteEvent event) {
        Scrap scrap = event.getSource();
        Long feedNo = scrap.getFeedNo();

        countFeedService.minusScrapCnt(feedNo);
    }

}
