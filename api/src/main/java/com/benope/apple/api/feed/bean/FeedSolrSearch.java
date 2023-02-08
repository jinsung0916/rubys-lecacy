package com.benope.apple.api.feed.bean;

import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedSolrSearch {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    private FeedTypeCd feedTypeCd;
    private String text;
    private Long foodNo;

    public FeedSolrEntity toEntity() {
        return FeedSolrEntity.builder()
                .feedTypeCd(feedTypeCd)
                .text(Objects.nonNull(text) ? List.of(text) : Collections.emptyList())
                .taggedFoods(Objects.nonNull(foodNo) ? List.of(foodNo) : Collections.emptyList())
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.desc("createdAt")));
    }

}
