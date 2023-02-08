package com.benope.apple.domain.member.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
@Access(AccessType.FIELD)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAlarmAgree {

    @Enumerated(EnumType.STRING)
    private AlarmAgreeCd alarmAgreeCd;
    private String alarmAgreeYn;
    private LocalDate alarmAgreeDt;

    public boolean isAgree() {
        return "Y".equalsIgnoreCase(getAlarmAgreeYn());
    }

    protected MemberAlarmAgree setAgree() {
        this.alarmAgreeYn = "Y";
        this.alarmAgreeDt = DateTimeUtil.getCurrentDate();
        return this;
    }

    protected MemberAlarmAgree setDisagree() {
        this.alarmAgreeYn = "N";
        this.alarmAgreeDt = DateTimeUtil.getCurrentDate();
        return this;
    }

    protected void validate() {
        Assert.notNull(alarmAgreeCd);
        Assert.notNull(alarmAgreeYn);
    }

    @RequiredArgsConstructor
    @Getter
    public enum AlarmAgreeCd implements EnumDescribed {

        ALARM01("좋아요 알림"),
        ALARM02("댓글 알림"),
        ALARM03("답글 알림"),
        ALARM04("팔로우 알림");

        private final String desc;

        @Override
        public String getCode() {
            return this.name();
        }

        public String getDesc() {
            return this.desc;
        }

    }
}