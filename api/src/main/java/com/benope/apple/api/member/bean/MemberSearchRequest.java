package com.benope.apple.api.member.bean;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchRequest {

    @NotNull
    private Long searchMbNo;

}
