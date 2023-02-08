package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import com.benope.apple.api.auth.service.LoginHistoryService;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.api.member.service.QuitMemberService;
import com.benope.apple.config.auth.CustomUserDetailsService;
import com.benope.apple.config.auth.TokenProvider;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppLoginService implements LoginService {

    private final QuitMemberService quitMemberService;
    private final CustomUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final LoginHistoryService loginHistoryService;

    @Override
    public boolean isApplicable(LoginRequest loginRequest) {
        return LoginRequest.LoginType.APP.equals(loginRequest.getLoginType());
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        if (quitMemberService.isQuitMember(loginRequest.getUserInput().toMemberAuth())) {
            throw new BusinessException(RstCode.QUIT_MEMBER);
        }

        UserDetails userDetails = toUserDetails(loginRequest.getUserInput());
        Authentication authentication = toAuthentication(userDetails);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        loginHistoryService.regist(
                LoginHistory.builder()
                        .mbNo(Long.valueOf(authentication.getName()))
                        .loginChannelCd(LoginHistory.LoginChannelCd.APP)
                        .loginHistoryCd(LoginHistory.LoginHistoryCd.SUCCESS)
                        .loginHistoryDtls("로그인 성공")
                        .loginUserAgent(loginRequest.getLoginUserAgent())
                        .loginIp(loginRequest.getLoginIp())
                        .refreshToken(refreshToken)
                        .build()
        );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private UserDetails toUserDetails(LoginRequest.UserInput userInput) {
        try {
            return userDetailsService.loadUser(userInput.getType(), userInput.getIdToken());
        } catch (UsernameNotFoundException e) {
            throw new BusinessException(RstCode.MEMBER_NOT_EXISTS);
        }
    }

    private Authentication toAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()
        );
    }
}
