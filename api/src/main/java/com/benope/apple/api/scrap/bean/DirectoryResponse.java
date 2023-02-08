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
public class DirectoryResponse {
    private ScrapTypeCd type;
    private Long directoryNo;
    private Long mbNo;
    private String directoryName;

    private FeedSimpleResponse feed;

    public static DirectoryResponse fromEntity(Scrap scrap) {
        return DirectoryResponse.builder()
                .type(ScrapTypeCd.DIRECTORY)
                .directoryNo(scrap.getScrapNo())
                .mbNo(scrap.getMbNo())
                .directoryName(scrap.getDirectoryName())
                .feed(DomainObjectUtil.nullSafeEntityFunction(scrap.getFeed(), FeedSimpleResponse::fromEntity))
                .build();
    }
}
