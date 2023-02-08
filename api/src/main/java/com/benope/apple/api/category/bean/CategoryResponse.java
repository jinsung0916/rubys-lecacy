package com.benope.apple.api.category.bean;

import com.benope.apple.config.webMvc.EnumResponse;
import com.benope.apple.domain.category.bean.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long categoryNo;
    private EnumResponse categoryTypeCd;
    private Long parentCategoryNo;
    private String categoryNm;

    public static CategoryResponse fromEntity(Category category) {
        return CategoryResponse.builder()
                .categoryNo(category.getCategoryNo())
                .categoryTypeCd(EnumResponse.fromEntity(category.getCategoryTypeCd()))
                .parentCategoryNo(category.getParentCategoryNo())
                .categoryNm(category.getCategoryNm())
                .build();
    }
}
