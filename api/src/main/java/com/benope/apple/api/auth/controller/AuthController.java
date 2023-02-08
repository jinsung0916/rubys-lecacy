package com.benope.apple.api.auth.controller;

import com.benope.apple.api.auth.bean.*;
import com.benope.apple.api.auth.service.JoinService;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.api.auth.service.RefreshTokenService;
import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.utils.HttpServletRequestUtil;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final JoinService joinService;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth.login")
    public ApiResponse login(
            @RequestBody @Valid LoginRequest.UserInput userInput,
            HttpServletRequest httpServletRequest
    ) {
        LoginRequest loginRequest = LoginRequest.builder()
                .loginType(LoginRequest.LoginType.APP)
                .httpServletRequest(httpServletRequest)
                .userInput(userInput)
                .build();

        TokenResponse response = loginService.login(loginRequest);
        return RstCode.SUCCESS.toApiResponse(response);
    }

    @PostMapping("/auth.join")
    public ApiResponse join(
            @RequestBody @Valid JoinRequest request
    ) {
        joinService.join(request);
        return RstCode.SUCCESS.toApiResponse();
    }

    @PostMapping("/auth.check.nickname")
    public ApiResponse checkNickname(
            @RequestBody @Valid CheckNicknameRequest input
    ) {
        if (isNicknameDuplicated(input)) {
            throw new BusinessException(RstCode.DUPLICATED_NICKNAME);
        }

        return RstCode.SUCCESS.toApiResponse();
    }

    private boolean isNicknameDuplicated(CheckNicknameRequest input) {
        return memberService.isNicknameDuplicated(input.getNickname());
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.REFRESH + "')")
    @PostMapping("/auth.refresh.token")
    public ApiResponse refreshToken(
            HttpServletRequest httpServletRequest
    ) {
        String refreshToken = HttpServletRequestUtil.getJwtFromRequest(httpServletRequest);
        String accessToken = refreshTokenService.exchangeToken(refreshToken);
        return RstCode.SUCCESS.toApiResponse(
                TokenResponse.builder()
                        .accessToken(accessToken)
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.REFRESH + "')")
    @PostMapping("/auth.reg.fcm.token")
    public ApiResponse registFcmToken(
            @RequestBody @Valid FcmTokenRequest input,
            HttpServletRequest httpServletRequest
    ) {
        String refreshToken = HttpServletRequestUtil.getJwtFromRequest(httpServletRequest);
        String fcmToken = input.getFcmToken();
        refreshTokenService.registFcmToken(refreshToken, fcmToken);
        return RstCode.SUCCESS.toApiResponse();
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.REFRESH + "')")
    @PostMapping("/auth.logout")
    public ApiResponse logout(
            HttpServletRequest httpServletRequest
    ) {
        String refreshToken = HttpServletRequestUtil.getJwtFromRequest(httpServletRequest);
        refreshTokenService.expireToken(refreshToken);
        return RstCode.SUCCESS.toApiResponse();
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/auth.quit")
    public ApiResponse quit() {
        Long mbNo = SessionUtil.getSessionMbNo();
        memberService.deleteById(mbNo);
        refreshTokenService.expireAllTokens(mbNo);
        return RstCode.SUCCESS.toApiResponse();
    }

}
