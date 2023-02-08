package com.benope.apple.domain.alarm.bean;

import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import lombok.Getter;

@Getter
public enum AlarmTypeCd {

    LIKE(MemberAlarmAgree.AlarmAgreeCd.ALARM01),
    COMMENT(MemberAlarmAgree.AlarmAgreeCd.ALARM02),
    SUB_COMMENT(MemberAlarmAgree.AlarmAgreeCd.ALARM03),
    FOLLOW(MemberAlarmAgree.AlarmAgreeCd.ALARM04);

    private final MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd;

    AlarmTypeCd(MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd) {
        this.alarmAgreeCd = alarmAgreeCd;
    }

}
