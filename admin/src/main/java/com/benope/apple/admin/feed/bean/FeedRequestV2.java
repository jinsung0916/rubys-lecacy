package com.benope.apple.admin.feed.bean;

import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedRequestV2 implements IdRequest {

    private List<Long> id;

    private Long feedNo;
    private FeedTypeCd feedTypeCd;
    private String text;
    private Long mbNo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public FeedSolrEntity toSolrEntity() {
        return FeedSolrEntity.builder()
                .id(feedNo)
                .feedTypeCd(feedTypeCd)
                .text(Objects.nonNull(text) ? List.of(text) : Collections.emptyList())
                .mbNo(mbNo)
                .build();
    }

}
