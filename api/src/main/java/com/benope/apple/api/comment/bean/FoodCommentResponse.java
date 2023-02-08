package com.benope.apple.api.comment.bean;

import com.benope.apple.domain.comment.bean.CommentScoreView;
import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Builder
public class FoodCommentResponse {
    private Long commentNo;
    private Long mbNo;
    private Long foodNo;
    private String contents;
    private boolean isHide;
    private String hideReasonCd;
    private String hideReasonDtls;
    private LocalDate hideDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private BigDecimal score;

    private String nickname;

    private String brand;
    private String foodNm;
    private String frontImgUrl;

    public static FoodCommentResponse fromEntity(FoodComment comment) {
        return FoodCommentResponse.builder()
                .commentNo(comment.getCommentNo())
                .mbNo(comment.getMbNo())
                .foodNo(comment.getObjectNo())
                .contents(comment.getContents())
                .isHide("Y".equalsIgnoreCase(comment.getHideYn()))
                .hideReasonCd(comment.getHideResnCd())
                .hideReasonDtls(comment.getHideResnDtls())
                .hideDate(comment.getHideDt())
                .createdAt(comment.getZonedCreateAt())
                .updatedAt(comment.getZonedUpdatedAt())
                .nickname(comment.getMemberNickname())
                .brand(comment.getBrand())
                .foodNm(comment.getFoodNm())
                .frontImgUrl(comment.getFrontImgUrl())
                .build();
    }

    public static FoodCommentResponse fromEntity(CommentScoreView view) {
        FoodCommentResponse response = fromEntity((FoodComment) view.getComment());
        response.score = DomainObjectUtil.nullSafeFunction(view.getScore(), Score::getScoreValue);
        return response;
    }

}
