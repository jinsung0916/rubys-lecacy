package com.benope.apple.api.alarm.event;

import com.benope.apple.api.alarm.bean.NotifyCommand;
import com.benope.apple.api.alarm.service.AlarmService;
import com.benope.apple.api.feed.service.FeedService;
import com.benope.apple.domain.alarm.bean.AlarmTargetCd;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.reaction.bean.FeedReaction;
import com.benope.apple.domain.reaction.event.FeedReactionRegistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AlarmReactionEventListener {

    private final FeedService feedService;
    private final AlarmService alarmService;

    @EventListener
    @Transactional
    public void handleEvent(FeedReactionRegistEvent e) {
        FeedReaction reaction = e.getSource();

        if (!reaction.isLike()) {
            return;
        }

        handleFeedReaction(reaction);
    }

    private void handleFeedReaction(FeedReaction reaction) {
        Feed feed = feedService.getById(reaction.getFeedNo(), false).orElseThrow();
        Long from = reaction.getMbNo();
        Long to = feed.getMbNo();

        alarmService.notify(
                NotifyCommand.builder()
                        .alarmTargetCd(AlarmTargetCd.FEED)
                        .objectNo(feed.getFeedNo())
                        .notifiable(reaction)
                        .fromMbNo(from)
                        .toMbNo(to)
                        .build()
        );
    }

}
