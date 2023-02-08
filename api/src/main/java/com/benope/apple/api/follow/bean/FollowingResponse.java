package com.benope.apple.api.follow.bean;

import com.benope.apple.domain.follow.bean.Follow;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingResponse {

    private Long mbNo;
    private String nickname;
    private String profileImgUrl;
    private LocalDate followDate;

    public static FollowingResponse fromEntity(Follow follow) {
        return FollowingResponse.builder()
                .mbNo(follow.toFollowingMbNo())
                .nickname(follow.toFollowingNickname())
                .profileImgUrl(follow.toFollowingProfileImageUrl())
                .followDate(follow.getFollowDate())
                .build();
    }

}
