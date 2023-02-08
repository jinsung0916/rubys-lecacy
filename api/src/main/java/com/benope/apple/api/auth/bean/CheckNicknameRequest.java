package com.benope.apple.api.auth.bean;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckNicknameRequest {

    @NotNull
    private String nickname;

}
