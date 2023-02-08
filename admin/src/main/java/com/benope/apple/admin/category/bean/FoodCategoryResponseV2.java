package com.benope.apple.admin.category.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.category.bean.CategoryTypeCd;
import com.benope.apple.domain.category.bean.FoodCategory;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCategoryResponseV2 {

    private Long id;
    private CategoryTypeCd categoryTypeCd;
    private Long parentCategoryNo;
    private String categoryNm;

    private UploadImgResponseV2 iconImg;

    public static FoodCategoryResponseV2 fromEntity(FoodCategory category) {
        return FoodCategoryResponseV2.builder()
                .id(category.getCategoryNo())
                .categoryTypeCd(category.getCategoryTypeCd())
                .parentCategoryNo(category.getParentCategoryNo())
                .categoryNm(category.getCategoryNm())
                .iconImg(
                        UploadImgResponseV2.builder()
                                .imgNo(category.getIconImgNo())
                                .src(category.getIconImgUrl())
                                .build()
                )
                .build();
    }

}
