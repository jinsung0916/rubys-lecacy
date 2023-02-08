package com.benope.apple.api.alarm.bean;

import com.benope.apple.config.webMvc.EnumResponse;
import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class MemberAlarmAgreeResponse {

    private EnumResponse alarmAgreeCd;
    private boolean isAgree;
    private LocalDate agreeDate;

    public static MemberAlarmAgreeResponse fromEntity(MemberAlarmAgree entity) {
        return MemberAlarmAgreeResponse.builder()
                .alarmAgreeCd(EnumResponse.fromEntity(entity.getAlarmAgreeCd()))
                .isAgree(entity.isAgree())
                .agreeDate(entity.getAlarmAgreeDt())
                .build();
    }
}
