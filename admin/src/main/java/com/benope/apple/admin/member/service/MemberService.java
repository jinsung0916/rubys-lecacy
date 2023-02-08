package com.benope.apple.admin.member.service;

import com.benope.apple.domain.member.bean.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface MemberService {

    Page<Member> getList(@NotNull Member member, @NotNull Pageable pageable);

    Optional<Member> getOne(@NotNull Member member);

    List<Member> getByIds(@NotNull List<Long> ids);

    Member update(@NotNull Member member);

}
