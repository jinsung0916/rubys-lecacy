package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.config.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MockLoginService implements LoginService {

    private final TokenProvider tokenProvider;

    @Override
    public boolean isApplicable(LoginRequest loginRequest) {
        return LoginRequest.LoginType.MOCK.equals(loginRequest.getLoginType());
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        Authentication authentication = mockAuthentication(loginRequest.getMockUserInput());
        String accessToken = tokenProvider.createAccessToken(authentication);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private Authentication mockAuthentication(LoginRequest.MockUserInput mockUserInput) {
        return new UsernamePasswordAuthenticationToken(mockUserInput.getMbNo(), null, null);
    }

}
