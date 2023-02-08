package com.benope.apple.api.category.bean;

import com.benope.apple.domain.category.bean.Category;
import com.benope.apple.domain.category.bean.CategoryTypeCd;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    @NotNull
    private CategoryTypeCd categoryTypeCd;
    private Long parentCategoryNo;

    public Category toEntity() {
        return Category.builder()
                .categoryTypeCd(categoryTypeCd)
                .parentCategoryNo(Objects.requireNonNullElse(parentCategoryNo, Category.ROOT_CATEGORY_NO))
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage);
    }
}
