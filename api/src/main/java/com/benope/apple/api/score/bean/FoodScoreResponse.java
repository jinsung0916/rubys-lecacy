package com.benope.apple.api.score.bean;

import com.benope.apple.config.webMvc.EnumResponse;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreFollowView;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder
public class FoodScoreResponse {

    private Long scoreNo;
    private Long mbNo;
    private Long foodNo;
    private BigDecimal score;
    private String nickname;
    private String profileImgUrl;
    private EnumResponse expertCd;
    private boolean isFollowing;

    private String brand;
    private String foodNm;
    private String frontImgUrl;

    public static FoodScoreResponse fromEntity(Score score) {
        return FoodScoreResponse.builder()
                .scoreNo(score.getScoreNo())
                .mbNo(score.getMbNo())
                .foodNo(score.getFoodNo())
                .score(score.getScoreValue().stripTrailingZeros())
                .nickname(score.getMemberNickname())
                .profileImgUrl(score.getMemberProfileImageUrl())
                .expertCd(EnumResponse.fromEntity(score.getMemberExpertCd()))
                .brand(score.getBrand())
                .foodNm(score.getFoodNm())
                .frontImgUrl(score.getFrontImgUrl())
                .build();
    }

    public static FoodScoreResponse fromEntity(ScoreFollowView view) {
        FoodScoreResponse response = fromEntity(view.getScore());
        response.isFollowing = Objects.nonNull(view.getFollow());
        return response;
    }

}
