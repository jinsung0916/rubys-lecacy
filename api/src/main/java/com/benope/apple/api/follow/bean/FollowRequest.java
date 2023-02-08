package com.benope.apple.api.follow.bean;

import com.benope.apple.domain.follow.bean.Follow;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRequest {

    @NotNull
    private Long followMbNo;

    public Follow toEntity() {
        return Follow.builder()
                .mbNo(SessionUtil.getSessionMbNo())
                .followMbNo(followMbNo)
                .build();
    }

}
