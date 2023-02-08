package com.benope.apple.admin.feed.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedSimpleResponseV2 {

    private Long id;
    private Long mbNo;
    private FeedTypeCd feedTypeCd;
    private String feedTitle;
    private List<HashTag> hashTag;
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

    private UploadImgResponseV2 rpstImg;

    public static FeedSimpleResponseV2 fromEntity(Feed feed) {
        return FeedSimpleResponseV2.builder()
                .id(feed.getFeedNo())
                .mbNo(feed.getMbNo())
                .feedTypeCd(feed.getFeedTypeCd())
                .feedTitle(feed.getFeedTitle())
                .hashTag(feed.getHashTag().stream().map(HashTag::fromValue).collect(Collectors.toUnmodifiableList()))
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
                .rpstImg(
                        UploadImgResponseV2.builder()
                                .imgNo(feed.getRpstImgNo())
                                .src(feed.getRpstImgUrl())
                                .build()
                )
                .build();
    }

    public static FeedSimpleResponseV2 fromEntity(FeedSolrEntity feed) {
        return FeedSimpleResponseV2.builder()
                .id(feed.getId())
                .mbNo(feed.getMbNo())
                .feedTypeCd(feed.getFeedTypeCd())
                .feedTitle(feed.getFeedTitle())
                .hashTag(
                        DomainObjectUtil.nullSafeStream(feed.getHashTag())
                                .map(HashTag::fromValue)
                                .collect(Collectors.toUnmodifiableList())
                )
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
                .rpstImg(
                        UploadImgResponseV2.builder()
                                .imgNo(feed.getRpstImgNo())
                                .src(feed.getRpstImgUrl())
                                .build()
                )
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class HashTag {
        private String value;

        public static HashTag fromValue(String value) {
            return HashTag.builder()
                    .value(value)
                    .build();
        }
    }

}
