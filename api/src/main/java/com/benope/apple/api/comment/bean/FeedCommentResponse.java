package com.benope.apple.api.comment.bean;

import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Builder
public class FeedCommentResponse {
    private Long commentNo;
    private Long mbNo;
    private Long feedNo;
    private String contents;
    private Long subCommentCnt;
    private boolean isHide;
    private String hideReasonCd;
    private String hideReasonDtls;
    private LocalDate hideDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private Long parentCommentNo;
    private Integer reorderNo;
    private Byte depth;

    private String nickname;
    private Long profileImageNo;

    private Long expertPolicyNo;
    private String expertPolicyNm;

    private String imgFileUrl;

    public static FeedCommentResponse fromEntity(Comment entity) {
        return FeedCommentResponse.builder()
                .commentNo(entity.getCommentNo())
                .mbNo(entity.getMbNo())
                .feedNo(entity.getObjectNo())
                .contents(entity.getContents())
                .subCommentCnt(entity.getSubCommentCnt())
                .isHide("Y".equalsIgnoreCase(entity.getHideYn()))
                .hideReasonCd(entity.getHideResnCd())
                .hideReasonDtls(entity.getHideResnDtls())
                .hideDate(entity.getHideDt())
                .createdAt(entity.getZonedCreateAt())
                .updatedAt(entity.getZonedUpdatedAt())
                .parentCommentNo(entity.getParentCommentNo())
                .reorderNo(entity.getReorderNo())
                .depth(entity.getDepth())
                .nickname(entity.getMemberNickname())
                .expertPolicyNm(DomainObjectUtil.nullSafeFunction(entity.getMemberExpertCd(), ExpertCd::getDesc))
                .imgFileUrl(entity.getMemberProfileImgUrl())
                .build();
    }
}
