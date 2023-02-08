package com.benope.apple.utils;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PagingMetadata {

    private Integer currentPageNo;
    private Integer recordsPerPage;

    private Long totalElements;
    private Integer numberOfElements;
    private Integer totalPages;
    private Integer prePage;
    private Integer nextPage;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasPrevious;
    private boolean hasNext;

    public static PagingMetadata withPage(Page page) {
        return PagingMetadata.builder()
                .currentPageNo(page.getPageable().getPageNumber() + 1)
                .recordsPerPage(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .prePage(page.hasPrevious() ? page.previousPageable().getPageNumber() : null)
                .nextPage(page.hasNext() ? page.nextPageable().getPageNumber() : null)
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .hasPrevious(page.hasPrevious())
                .hasNext(page.hasNext())
                .build();
    }

}
