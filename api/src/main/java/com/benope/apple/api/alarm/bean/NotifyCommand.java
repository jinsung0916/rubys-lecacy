package com.benope.apple.api.alarm.bean;

import com.benope.apple.domain.alarm.bean.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Builder
public class NotifyCommand {

    @NotNull
    private AlarmTargetCd alarmTargetCd;
    @NotNull
    private Long objectNo;
    @NotNull
    private @Getter(AccessLevel.PROTECTED) Notifiable notifiable;
    @NotNull
    private Long fromMbNo;
    @NotNull
    private Long toMbNo;

    public boolean isSelfNotified() {
        return Objects.equals(fromMbNo, toMbNo);
    }

    public Alarm toEntity() {
        if (AlarmTargetCd.FEED.equals(alarmTargetCd)) {
            return FeedAlarm.builder()
                    .alarmTypeCd(notifiable.toAlarmTypeCd())
                    .fromMbNo(fromMbNo)
                    .toMbNo(toMbNo)
                    .feedNo(objectNo)
                    .content(notifiable.toMessage())
                    .build();
        } else if (AlarmTargetCd.COMMUNITY.equals(alarmTargetCd)) {
            return CommunityAlarm.builder()
                    .alarmTypeCd(notifiable.toAlarmTypeCd())
                    .fromMbNo(fromMbNo)
                    .toMbNo(toMbNo)
                    .communityNo(objectNo)
                    .content(notifiable.toMessage())
                    .build();
        } else if (AlarmTargetCd.MEMBER.equals(alarmTargetCd)) {
            return MemberAlarm.builder()
                    .alarmTypeCd(notifiable.toAlarmTypeCd())
                    .fromMbNo(fromMbNo)
                    .toMbNo(toMbNo)
                    .mbNo(objectNo)
                    .content(notifiable.toMessage())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
