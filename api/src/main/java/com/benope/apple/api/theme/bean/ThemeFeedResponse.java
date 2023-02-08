package com.benope.apple.api.theme.bean;

import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThemeFeedResponse {

    private Long feedNo;
    private FeedTypeCd feedTypeCd;
    private String brand;
    private String foodNm;
    private String frontImgUrl;
    private String imgFileUrl;

    public static ThemeFeedResponse fromEntity(Feed feed) {
        return ThemeFeedResponse.builder()
                .feedNo(feed.getFeedNo())
                .feedTypeCd(feed.getFeedTypeCd())
                .imgFileUrl(feed.getRpstImgUrl())
                .build();
    }

}
