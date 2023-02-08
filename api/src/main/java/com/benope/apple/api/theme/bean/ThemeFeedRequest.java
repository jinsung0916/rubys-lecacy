package com.benope.apple.api.theme.bean;

import com.benope.apple.domain.theme.bean.ThemeFeed;
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
public class ThemeFeedRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    @NotNull
    private Long themeNo;

    public ThemeFeed toEntity() {
        return ThemeFeed.builder()
                .themeNo(themeNo)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.asc("reorderNo"), Sort.Order.desc("themeFeedNo")));
    }

}
