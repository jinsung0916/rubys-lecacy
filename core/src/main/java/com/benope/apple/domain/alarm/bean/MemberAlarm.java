package com.benope.apple.domain.alarm.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.internal.util.Assert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
@SQLDelete(sql = "UPDATE alarm SET row_stat_cd = 'D' WHERE alarm_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAlarm extends Alarm {

    @Column(name = "object_no")
    private Long mbNo;

    public String getAlarmTargetDetailCd() {
        return AlarmTargetCd.MEMBER.name();
    }

    @Override
    protected void validate() {
        super.validate();

        Assert.notNull(mbNo);
    }
}
