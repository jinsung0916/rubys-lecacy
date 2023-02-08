package com.benope.apple.api.scrap.bean;

import com.benope.apple.api.feed.bean.FeedSimpleResponse;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
@Builder(access = AccessLevel.PROTECTED)
public class ScrapResponse {
    private ScrapTypeCd type;
    private Long scrapNo;
    private Long mbNo;

    private FeedSimpleResponse feed;

    public static ScrapResponse fromEntity(Scrap scrap) {
        return ScrapResponse.builder()
                .type(ScrapTypeCd.SCRAP)
                .scrapNo(scrap.getScrapNo())
                .mbNo(scrap.getMbNo())
                .feed(DomainObjectUtil.nullSafeEntityFunction(scrap.getFeed(), FeedSimpleResponse::fromEntity))
                .build();
    }
}
