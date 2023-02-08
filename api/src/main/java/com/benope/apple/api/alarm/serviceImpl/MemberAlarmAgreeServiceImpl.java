package com.benope.apple.api.alarm.serviceImpl;

import com.benope.apple.api.alarm.service.MemberAlarmAgreeService;
import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAlarmAgreeServiceImpl implements MemberAlarmAgreeService {

    private final MemberService memberService;

    @Override
    public List<MemberAlarmAgree> getByMbNo(Long mbNo) {
        Member member = getById(mbNo);
        return member.getMemberAlarmAgrees();
    }

    @Override
    public void agree(Long mbNo, MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd) {
        Member member = getById(mbNo);
        member.alarmAgree(alarmAgreeCd);
    }

    @Override
    public void disagree(Long mbNo, MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd) {
        Member member = getById(mbNo);
        member.alarmDisagree(alarmAgreeCd);
    }

    private Member getById(Long mbNo) {
        return memberService.getOne(
                        Member.builder()
                                .mbNo(mbNo)
                                .build()
                )
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

}
