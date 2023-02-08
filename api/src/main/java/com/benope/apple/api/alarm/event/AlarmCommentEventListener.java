package com.benope.apple.api.alarm.event;

import com.benope.apple.api.alarm.bean.NotifyCommand;
import com.benope.apple.api.alarm.service.AlarmService;
import com.benope.apple.api.comment.service.CommentService;
import com.benope.apple.api.feed.service.FeedService;
import com.benope.apple.domain.alarm.bean.AlarmTargetCd;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.FeedComment;
import com.benope.apple.domain.comment.event.FeedCommentRegistEvent;
import com.benope.apple.domain.feed.bean.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AlarmCommentEventListener {

    private final FeedService feedService;
    private final CommentService commentService;
    private final AlarmService alarmService;

    @EventListener
    @Transactional
    public void handleEvent(FeedCommentRegistEvent e) {
        FeedComment comment = e.getSource();

        handleFeedComment(comment);
    }

    private void handleFeedComment(FeedComment comment) {
        Long from = comment.getMbNo();

        if (comment.isSubComment()) {
            Feed feed = feedService.getById(comment.getFeedNo(), false).orElseThrow();
            Comment parent = commentService.getById(comment.getParentCommentNo()).orElseThrow();
            Long to = parent.getMbNo();

            alarmService.notify(
                    NotifyCommand.builder()
                            .alarmTargetCd(AlarmTargetCd.FEED)
                            .objectNo(feed.getFeedNo())
                            .notifiable(comment)
                            .fromMbNo(from)
                            .toMbNo(to)
                            .build()
            );
        } else {
            Feed feed = feedService.getById(comment.getFeedNo(), false).orElseThrow();
            Long to = feed.getMbNo();

            alarmService.notify(
                    NotifyCommand.builder()
                            .alarmTargetCd(AlarmTargetCd.FEED)
                            .objectNo(feed.getFeedNo())
                            .notifiable(comment)
                            .fromMbNo(from)
                            .toMbNo(to)
                            .build()
            );
        }
    }

}
