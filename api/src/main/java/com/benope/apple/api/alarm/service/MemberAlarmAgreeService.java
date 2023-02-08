package com.benope.apple.api.alarm.service;

import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface MemberAlarmAgreeService {

    List<MemberAlarmAgree> getByMbNo(@NotNull Long mbNo);

    void agree(@NotNull Long mbNo, @NotNull MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd);

    void disagree(@NotNull Long mbNo, @NotNull MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd);

}
