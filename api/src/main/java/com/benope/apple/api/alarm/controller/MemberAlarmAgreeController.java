package com.benope.apple.api.alarm.controller;

import com.benope.apple.api.alarm.bean.MemberAlarmAgreeRequest;
import com.benope.apple.api.alarm.bean.MemberAlarmAgreeResponse;
import com.benope.apple.api.alarm.service.MemberAlarmAgreeService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberAlarmAgreeController {

    private final MemberAlarmAgreeService memberAlarmAgreeService;

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.member.alarm.agree")
    public ApiResponse getMemberAlarmAgree() {
        List<MemberAlarmAgree> memberAlarmAgrees = memberAlarmAgreeService.getByMbNo(SessionUtil.getSessionMbNo());
        List<MemberAlarmAgree> result = toFullyGenerated(memberAlarmAgrees);

        return RstCode.SUCCESS.toApiResponse(
                result.stream()
                        .map(MemberAlarmAgreeResponse::fromEntity)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    private List<MemberAlarmAgree> toFullyGenerated(List<MemberAlarmAgree> before) {
        List<MemberAlarmAgree> after = new ArrayList<>();

        for (MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd : MemberAlarmAgree.AlarmAgreeCd.values()) {
            Optional<MemberAlarmAgree> optional = before.stream().filter(it -> alarmAgreeCd.equals(it.getAlarmAgreeCd())).findFirst();
            if (optional.isPresent()) {
                after.add(optional.get());
            } else {
                after.add(
                        MemberAlarmAgree.builder()
                                .alarmAgreeCd(alarmAgreeCd)
                                .alarmAgreeYn("N")
                                .build()
                );
            }
        }

        return after;
    }

    @PostMapping("/agree.member.alarm")
    public ApiResponse agreeMemberAlarm(
            @RequestBody @Valid MemberAlarmAgreeRequest input
    ) {
        memberAlarmAgreeService.agree(SessionUtil.getSessionMbNo(), input.getAlarmAgreeCd());
        return RstCode.SUCCESS.toApiResponse();
    }

    @PostMapping("/disagree.member.alarm")
    public ApiResponse disagreeMemberAlarm(
            @RequestBody @Valid MemberAlarmAgreeRequest input
    ) {
        memberAlarmAgreeService.disagree(SessionUtil.getSessionMbNo(), input.getAlarmAgreeCd());
        return RstCode.SUCCESS.toApiResponse();
    }

}
