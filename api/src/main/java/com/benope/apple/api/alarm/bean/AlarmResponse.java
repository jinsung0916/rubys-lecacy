package com.benope.apple.api.alarm.bean;

import com.benope.apple.domain.alarm.bean.Alarm;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmResponse {
    private Long alarmNo;
    private Long mbNo;
    private String alarmTargetCd;
    private Long objectNo;
    private String content;
    private String thumbnailImgUrl;
    private boolean isRead;

    public static AlarmResponse fromEntity(Alarm alarm) {
        return AlarmResponse.builder()
                .alarmNo(alarm.getAlarmNo())
                .mbNo(alarm.getFromMbNo())
                .alarmTargetCd(alarm.getAlarmTargetDetailCd())
                .objectNo(alarm.getObjectNo())
                .content(alarm.toAlarmContent())
                .isRead(alarm.isRead())
                .thumbnailImgUrl(alarm.getFromMemberProfileImgUrl())
                .build();
    }

}
