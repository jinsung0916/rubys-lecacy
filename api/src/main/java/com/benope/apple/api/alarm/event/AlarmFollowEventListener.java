package com.benope.apple.api.alarm.event;

import com.benope.apple.api.alarm.bean.NotifyCommand;
import com.benope.apple.api.alarm.service.AlarmService;
import com.benope.apple.domain.alarm.bean.AlarmTargetCd;
import com.benope.apple.domain.follow.bean.Follow;
import com.benope.apple.domain.follow.event.FollowRegistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AlarmFollowEventListener {

    private final AlarmService alarmService;

    @EventListener
    @Transactional
    public void handleAsyncEvent(FollowRegistEvent event) {
        Follow follow = event.getSource();

        alarmService.notify(
                NotifyCommand.builder()
                        .alarmTargetCd(AlarmTargetCd.MEMBER)
                        .objectNo(follow.getMbNo())
                        .notifiable(follow)
                        .fromMbNo(follow.getMbNo())
                        .toMbNo(follow.getFollowMbNo())
                        .build()
        );
    }

}
