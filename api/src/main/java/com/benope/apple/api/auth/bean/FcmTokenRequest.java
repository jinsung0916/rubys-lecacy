package com.benope.apple.api.auth.bean;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmTokenRequest {
    @NotBlank
    private String fcmToken;
}
