package com.benope.apple.api.feed.bean;

import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class FeedSimpleResponse {
    private Long feedNo;
    private Long mbNo;
    private FeedTypeCd feedTypeCd;
    private String feedTitle;
    private Long rpstImgNo;
    private List<String> hashTag;
    private Long viewCnt;
    private Long commentCnt;
    private Long likeCnt;
    private Long scrapCnt;
    private boolean isHide;
    private String hideResnCd;
    private String hideResnDtls;
    private LocalDate hideDt;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private String imgFileUrl;

    private String nickname;
    private String profileImgUrl;

    public static FeedSimpleResponse fromEntity(Feed feed) {
        return FeedSimpleResponse.builder()
                .feedNo(feed.getFeedNo())
                .mbNo(feed.getMbNo())
                .feedTypeCd(feed.getFeedTypeCd())
                .feedTitle(feed.getFeedTitle())
                .rpstImgNo(feed.getRpstImgNo())
                .hashTag(feed.getHashTag())
                .viewCnt(feed.getViewCnt())
                .commentCnt(feed.getCommentCnt())
                .likeCnt(feed.getLikeCnt())
                .scrapCnt(feed.getScrapCnt())
                .isHide("Y".equalsIgnoreCase(feed.getHideYn()))
                .hideResnCd(feed.getHideResnCd())
                .hideResnDtls(feed.getHideResnDtls())
                .hideDt(feed.getHideDt())
                .createdAt(feed.getZonedCreateAt())
                .updatedAt(feed.getZonedUpdatedAt())
                .imgFileUrl(feed.getRpstImgUrl())
                .nickname(feed.getMemberNickname())
                .profileImgUrl(feed.getMemberProfileImageUrl())
                .build();
    }

    public static FeedSimpleResponse fromEntity(FeedSolrEntity feed) {
        return FeedSimpleResponse.builder()
                .feedNo(feed.getId())
                .mbNo(feed.getMbNo())
                .feedTypeCd(feed.getFeedTypeCd())
                .feedTitle(feed.getFeedTitle())
                .rpstImgNo(feed.getRpstImgNo())
                .hashTag(feed.getHashTag())
                .viewCnt(feed.getViewCnt())
                .commentCnt(feed.getCommentCnt())
                .likeCnt(feed.getLikeCnt())
                .scrapCnt(feed.getScrapCnt())
                .isHide("Y".equalsIgnoreCase(feed.getHideYn()))
                .hideResnCd(feed.getHideResnCd())
                .hideResnDtls(feed.getHideResnDtls())
                .hideDt(feed.getHideDt())
                .createdAt(feed.getZonedCreateAt())
                .updatedAt(feed.getZonedUpdatedAt())
                .imgFileUrl(feed.getRpstImgUrl())
                .nickname(feed.getMemberNickname())
                .profileImgUrl(feed.getMemberProfileImgUrl())
                .build();
    }
}
