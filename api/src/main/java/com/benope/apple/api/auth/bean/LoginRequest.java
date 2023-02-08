package com.benope.apple.api.auth.bean;

import com.benope.apple.domain.member.bean.MemberAuth;
import com.benope.apple.utils.HttpServletRequestUtil;
import lombok.*;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    private LoginType loginType;
    private HttpServletRequest httpServletRequest;
    private Authentication authentication;

    private UserInput userInput;
    private MockUserInput mockUserInput;

    public String getLoginUserAgent() {
        return HttpServletRequestUtil.getUserAgent(httpServletRequest);
    }

    public String getLoginIp() {
        return HttpServletRequestUtil.getIpAddress(httpServletRequest);
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInput {

        @NotNull
        private MemberAuth.MbAuthCd type;

        @NotBlank
        private String idToken;

        public MemberAuth toMemberAuth() {
            return MemberAuth.builder()
                    .mbAuthCd(type)
                    .identifier(idToken)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MockUserInput {
        @NotNull
        private Long mbNo;
    }

    public enum LoginType {
        CAFE24, APP, MOCK
    }

}
