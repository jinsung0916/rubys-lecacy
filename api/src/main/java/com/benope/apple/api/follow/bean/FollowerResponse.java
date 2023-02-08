package com.benope.apple.api.follow.bean;

import com.benope.apple.domain.follow.bean.Follow;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowerResponse {
    private Long mbNo;
    private String nickname;
    private String profileImgUrl;
    private LocalDate followDate;

    public static FollowerResponse fromEntity(Follow follow) {
        return FollowerResponse.builder()
                .mbNo(follow.toFollowerMbNo())
                .nickname(follow.toFollowerNickname())
                .profileImgUrl(follow.toFollowerProfileImageUrl())
                .followDate(follow.getFollowDate())
                .build();
    }
}
