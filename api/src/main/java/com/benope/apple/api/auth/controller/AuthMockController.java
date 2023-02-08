package com.benope.apple.api.auth.controller;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Profile({"local", "dev"})
@RestController
@RequiredArgsConstructor
public class AuthMockController {

    private final LoginService loginService;

    @PostMapping("/auth.mock.login")
    public ApiResponse mockAccessToken(
            @RequestBody @Valid LoginRequest.MockUserInput mockUserInput
    ) {
        LoginRequest loginRequest = LoginRequest.builder()
                .loginType(LoginRequest.LoginType.MOCK)
                .mockUserInput(mockUserInput)
                .build();

        TokenResponse response = loginService.login(loginRequest);
        return RstCode.SUCCESS.toApiResponse(response);
    }

}
