package com.benope.apple.api.auth.service;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface LoginService {

    boolean isApplicable(@NotNull LoginRequest loginRequest);

    TokenResponse login(@NotNull LoginRequest loginRequest);

}
