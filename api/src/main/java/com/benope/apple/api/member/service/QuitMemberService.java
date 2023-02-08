package com.benope.apple.api.member.service;

import com.benope.apple.domain.member.bean.MemberAuth;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface QuitMemberService {

    boolean isQuitMember(@NotNull MemberAuth memberAuth);

}
