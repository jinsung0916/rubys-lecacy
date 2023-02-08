package com.benope.apple.api.term.serviceImpl;

import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.api.term.service.MemberTermAgreeService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.domain.term.bean.Term;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberTermAgreeServiceImpl implements MemberTermAgreeService {

    private final MemberService memberService;

    @Override
    public List<MemberTermAgree> getByMbNo(Long mbNo) {
        Member member = getById(mbNo);
        return member.getLatestTermAgrees();
    }

    @Override
    public void agree(Long mbNo, Term.TermCd termCd, String termVersion) {
        Member member = getById(mbNo);
        member.termAgree(termCd, termVersion);
    }

    @Override
    public void disagree(Long mbNo, Term.TermCd termCd, String termVersion) {
        Member member = getById(mbNo);
        member.termDisagree(termCd, termVersion);
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
