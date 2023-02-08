package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.config.auth.SsoAttributes;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Transactional
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SsoAttributes ssoAttributes = CookieUtils.getCookie(request, SsoAttributes.SSO_ATTRIBUTES_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, SsoAttributes.class))
                .orElseThrow(() -> new BusinessException(RstCode.INVALID_APPROACH));

        LoginRequest loginRequest = LoginRequest.builder()
                .loginType(LoginRequest.LoginType.CAFE24)
                .httpServletRequest(request)
                .authentication(authentication)
                .build();

        TokenResponse tokenResponse = loginService.login(loginRequest);

        String url = Objects.requireNonNullElse(ssoAttributes.getRedirectUri(), "/");

        String redirectUri = UriComponentsBuilder.fromUriString(url)
                .queryParam("code", tokenResponse.getAccessToken())
                .queryParam("state", ssoAttributes.getState())
                .toUriString();

        response.sendRedirect(redirectUri);
    }

}
