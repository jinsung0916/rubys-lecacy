package com.benope.apple.api.food.bean;

import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.google.common.base.CaseFormat;
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
public class FoodSolrSearch {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    private String text;
    private Long categoryNo;
    private Long subCategoryNo;

    private String orderByCol;
    private Sort.Direction orderByCond;

    public FoodSolrEntity toEntity() {
        return FoodSolrEntity.builder()
                .text(Objects.nonNull(text) ? List.of(text) : Collections.emptyList())
                .categoryNo(categoryNo)
                .subCategoryNo(subCategoryNo)
                .build();
    }

    public Pageable toPageable() {
        Sort sort = Objects.nonNull(orderByCol) && Objects.nonNull(orderByCond)
                ? Sort.by(orderByCond, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, orderByCol))
                : Sort.unsorted();

        if ("average_score".equalsIgnoreCase(orderByCol)) {
            sort = Sort.by(Sort.Order.desc("isScoreCountAboveTen")).and(sort);
        }

        return PageRequest.of(currentPageNo - 1, recordsPerPage, sort);
    }
}
