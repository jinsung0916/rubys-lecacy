package com.benope.apple.api.term.controller;

import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.api.term.bean.MemberTermAgreeRequest;
import com.benope.apple.api.term.bean.MemberTermAgreeResponse;
import com.benope.apple.api.term.service.MemberTermAgreeService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberTermAgreeController {

    private final MemberService memberService;
    private final MemberTermAgreeService memberTermAgreeService;

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.member.term.agree")
    public ApiResponse getMemberTermAgree() {
        List<MemberTermAgree> memberTermAgreeList = memberTermAgreeService.getByMbNo(SessionUtil.getSessionMbNo());

        return RstCode.SUCCESS.toApiResponse(
                memberTermAgreeList
                        .stream()
                        .map(MemberTermAgreeResponse::fromEntity)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    @PostMapping("/agree.member.term")
    public ApiResponse agreeMemberTerm(
            @RequestBody @Valid MemberTermAgreeRequest input
    ) {
        memberTermAgreeService.agree(SessionUtil.getSessionMbNo(), input.getTermCd(), input.getTermVersion());
        return RstCode.SUCCESS.toApiResponse();
    }

    @PostMapping("/disagree.member.term")
    public ApiResponse disagreeMemberTerm(
            @RequestBody @Valid MemberTermAgreeRequest input
    ) {
        memberTermAgreeService.disagree(SessionUtil.getSessionMbNo(), input.getTermCd(), input.getTermVersion());
        return RstCode.SUCCESS.toApiResponse();
    }

}
