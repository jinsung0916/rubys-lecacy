package com.benope.apple.admin.category.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.category.bean.FoodCategory;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCategoryRequestV2 implements IdRequest {

    private List<Long> id;

    private Long parentCategoryNo;
    @NotNull(groups = Create.class)
    private String categoryNm;

    private Long iconImgNo;

    public FoodCategory toEntity(Long categoryNo) {
        return FoodCategory.builder()
                .categoryNo(categoryNo)
                .parentCategoryNo(parentCategoryNo)
                .categoryNm(categoryNm)
                .iconImgNo(iconImgNo)
                .build();
    }

}
