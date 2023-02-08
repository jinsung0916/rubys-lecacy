package com.benope.apple.api.auth.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface RefreshTokenService {

    @NotNull String exchangeToken(@NotBlank String refreshToken);

    List<String> getFcmTokensByMbNo(@NotNull Long mbNo);

    void registFcmToken(@NotNull String refreshToken, @NotNull String fcmToken);

    void expireToken(@NotNull String refreshToken);

    void expireAllTokens(@NotNull Long mbNo);

}
