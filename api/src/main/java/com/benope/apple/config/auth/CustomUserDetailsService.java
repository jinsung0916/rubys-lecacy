package com.benope.apple.config.auth;

import com.benope.apple.domain.member.bean.MemberAuth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
public interface CustomUserDetailsService {

    @NotNull UserDetails loadUser(@NotNull Long mbNo);

    @NotNull UserDetails loadUser(@NotNull MemberAuth.MbAuthCd mbAuthCd, @NotBlank String username);

}
