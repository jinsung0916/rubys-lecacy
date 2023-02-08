package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.service.LoginHistoryService;
import com.benope.apple.api.auth.service.RefreshTokenService;
import com.benope.apple.config.auth.CustomUserDetailsService;
import com.benope.apple.config.auth.TokenProvider;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.loginHistory.event.RefreshTokenAccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginHistoryRefreshTokenService implements RefreshTokenService {

    private final LoginHistoryService loginHistoryService;
    private final CustomUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true)
    @Override
    public String exchangeToken(String refreshToken) {
        LoginHistory loginHistory = retrieveLoginHistory(refreshToken);
        UserDetails userDetails = toUserDetails(loginHistory);
        Authentication authentication = toAuthentication(userDetails);
        return tokenProvider.createAccessToken(authentication);
    }

    private UserDetails toUserDetails(LoginHistory loginHistory) {
        try {
            return userDetailsService.loadUser(loginHistory.getMbNo());
        } catch (AuthenticationException e) {
            throw new InsufficientAuthenticationException(e.getMessage(), e);
        }
    }

    private LoginHistory retrieveLoginHistory(String refreshToken) {
        LoginHistory loginHistory = getByRefreshToken(refreshToken)
                .orElseThrow(() -> new InsufficientAuthenticationException("Invalid refresh token: " + refreshToken));

        publishRefreshTokenAccessEvent(loginHistory);

        return loginHistory;
    }

    private Optional<LoginHistory> getByRefreshToken(String refreshToken) {
        LoginHistory cond = LoginHistory.builder()
                .refreshToken(refreshToken)
                .build();

        return loginHistoryService.getOne(cond);
    }

    private void publishRefreshTokenAccessEvent(LoginHistory loginHistory) {
        applicationEventPublisher.publishEvent(new RefreshTokenAccessEvent(loginHistory));
    }

    private Authentication toAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getFcmTokensByMbNo(Long mbNo) {
        return getByMbNo(mbNo)
                .stream()
                .map(LoginHistory::getFcmToken)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<LoginHistory> getByMbNo(Long mbNo) {
        LoginHistory cond = LoginHistory.builder()
                .mbNo(mbNo)
                .build();

        Pageable pageable = Pageable.unpaged();

        return loginHistoryService.getList(cond, pageable).toList();
    }

    @Override
    public void registFcmToken(String refreshToken, String fcmToken) {
        LoginHistory entity = getByRefreshToken(refreshToken)
                .orElseThrow(() -> new InsufficientAuthenticationException("Invalid refresh token: " + refreshToken));

        LoginHistory loginHistory = LoginHistory.builder()
                .loginHistoryNo(entity.getLoginHistoryNo())
                .fcmToken(fcmToken)
                .build();

        loginHistoryService.update(loginHistory);
    }

    @Override
    public void expireToken(String refreshToken) {
        loginHistoryService.deleteByRefreshToken(refreshToken);
    }

    @Override
    public void expireAllTokens(Long mbNo) {
        loginHistoryService.deleteByMbNo(mbNo);
    }

}
