package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import com.benope.apple.api.auth.service.LoginHistoryService;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.config.auth.TokenProvider;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class Cafe24LoginService implements LoginService {

    private final TokenProvider tokenProvider;
    private final LoginHistoryService loginHistoryService;

    @Override
    public boolean isApplicable(LoginRequest loginRequest) {
        return LoginRequest.LoginType.CAFE24.equals(loginRequest.getLoginType());
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        Authentication authentication = loginRequest.getAuthentication();

        String accessToken = tokenProvider.createAccessToken(authentication);

        loginHistoryService.regist(
                LoginHistory.builder()
                        .mbNo(Long.valueOf(authentication.getName()))
                        .loginChannelCd(LoginHistory.LoginChannelCd.CAFE24)
                        .loginHistoryCd(LoginHistory.LoginHistoryCd.SUCCESS)
                        .loginHistoryDtls("로그인 성공")
                        .loginUserAgent(loginRequest.getLoginUserAgent())
                        .loginIp(loginRequest.getLoginIp())
                        .build()
        );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
