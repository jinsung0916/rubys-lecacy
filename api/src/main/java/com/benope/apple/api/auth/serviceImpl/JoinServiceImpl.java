package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.JoinRequest;
import com.benope.apple.api.auth.service.JoinService;
import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.api.member.service.QuitMemberService;
import com.benope.apple.api.term.service.TermService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JoinServiceImpl implements JoinService {

    private final MemberService memberService;
    private final QuitMemberService quitMemberService;
    private final TermService termService;

    @Override
    public void join(JoinRequest joinRequest) {
        if (isMemberExists(joinRequest)) {
            throw new BusinessException(RstCode.ALREADY_REGISTERED_MEMBER);
        }

        if (isQuitMember(joinRequest)) {
            throw new BusinessException(RstCode.QUIT_MEMBER);
        }

        Member member = memberService.regist(joinRequest.toMember());

        termService.requireMandatoryTerms(member.getMemberTermAgrees());
    }

    private boolean isMemberExists(JoinRequest joinRequest) {
        return memberService.getByMemberAuth(
                        MemberAuth.builder()
                                .mbAuthCd(joinRequest.getType())
                                .identifier(joinRequest.getIdToken())
                                .build()
                )
                .isPresent();
    }

    private boolean isQuitMember(JoinRequest joinRequest) {
        MemberAuth memberAuth = joinRequest.toMember().getMemberAuths().stream().findFirst().orElse(null);
        return quitMemberService.isQuitMember(memberAuth);
    }

}
