package com.benope.apple.api.member.service;

import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface MemberService {

    Optional<Member> getOne(@NotNull Member member);

    Optional<Member> getByMemberAuth(@NotNull MemberAuth memberAuth);

    boolean isNicknameDuplicated(@NotNull String nickname);

    @NotNull Member regist(@NotNull Member member);

    @PostAuthorize("returnObject.name == authentication.name")
    @NotNull Member update(@NotNull Member member);

    @PostAuthorize("returnObject.name == authentication.name")
    Member deleteById(@NotNull Long mbNo);

}
