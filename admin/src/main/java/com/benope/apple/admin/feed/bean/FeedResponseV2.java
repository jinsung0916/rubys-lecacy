package com.benope.apple.admin.feed.bean;

import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.utils.DomainObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponseV2 {

    @Delegate
    @JsonIgnore
    private FeedSimpleResponseV2 feedSimpleResponse;
    private List<FeedDetailResponseV2> feedDetails;

    private Long themeNo;

    public static FeedResponseV2 fromEntity(Feed feed) {
        return FeedResponseV2.builder()
                .feedSimpleResponse(FeedSimpleResponseV2.fromEntity(feed))
                .build();
    }

    public static FeedResponseV2 fromEntity(FeedSolrEntity feed) {
        return FeedResponseV2.builder()
                .feedSimpleResponse(FeedSimpleResponseV2.fromEntity(feed))
                .build();
    }

    public static FeedResponseV2 fromEntityToDetail(Feed feed) {
        return FeedResponseV2.builder()
                .feedSimpleResponse(FeedSimpleResponseV2.fromEntity(feed))
                .feedDetails(toFeedDetailResponse(feed))
                .build();
    }

    private static List<FeedDetailResponseV2> toFeedDetailResponse(Feed feed) {
        return DomainObjectUtil.nullSafeStream(feed.getFeedDetails())
                .map(FeedDetailResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public FeedResponseV2 setThemeNo(Long themeNo) {
        this.themeNo = themeNo;
        return this;
    }

}
