package com.benope.apple.api.alarm.bean;

import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAlarmAgreeRequest {

    @NotNull
    private MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd;

    public MemberAlarmAgree toEntity() {
        return MemberAlarmAgree.builder()
                .alarmAgreeCd(alarmAgreeCd)
                .build();
    }

}
