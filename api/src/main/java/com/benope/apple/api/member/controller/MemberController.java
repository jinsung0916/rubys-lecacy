package com.benope.apple.api.member.controller;

import com.benope.apple.api.follow.service.FollowHelperService;
import com.benope.apple.api.member.bean.MemberRequest;
import com.benope.apple.api.member.bean.MemberResponse;
import com.benope.apple.api.member.bean.MemberSearchRequest;
import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.api.member.service.MemberSummaryService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberSummaryService memberSummaryService;
    private final FollowHelperService followHelperService;

    @PostMapping("/get.member")
    public ApiResponse getMember(@RequestBody @Valid MemberSearchRequest input) {
        MemberResponse memberResponse = toMemberResponse(input);
        return RstCode.SUCCESS.toApiResponse(memberResponse);
    }

    private MemberResponse toMemberResponse(MemberSearchRequest input) {
        Member member = memberService.getOne(
                        Member.builder()
                                .mbNo(input.getSearchMbNo())
                                .build()
                )
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        MemberResponse memberResponse = MemberResponse.fromEntity(member);

        MemberSummaryComposite memberSummaries = memberSummaryService.getMemberSummaries(input.getSearchMbNo());
        memberResponse.setMemberSummaries(memberSummaries);

        if (SessionUtil.isAuthenticated()) {
            boolean isFollow = followHelperService.checkFollow(SessionUtil.getSessionMbNo(), input.getSearchMbNo());
            memberResponse.setFollow(isFollow);
        }

        return memberResponse;
    }

    @PostMapping("/mod.member")
    public ApiResponse modifyMember(
            @RequestBody @Valid MemberRequest input
    ) {
        Member member = memberService.update(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(MemberResponse.fromEntity(member));
    }

}
