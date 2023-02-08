package com.benope.apple.api.feed.bean;

import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedSearchRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    private FeedTypeCd feedTypeCd;

    private Long searchMbNo;

    public Feed toEntity() {
        return Feed.builder()
                .feedTypeCd(feedTypeCd)
                .mbNo(searchMbNo)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Direction.DESC, "feedNo"));
    }

}
