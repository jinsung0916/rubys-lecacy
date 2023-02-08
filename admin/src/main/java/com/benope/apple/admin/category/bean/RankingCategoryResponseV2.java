package com.benope.apple.admin.category.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.category.bean.CategoryTypeCd;
import com.benope.apple.domain.category.bean.RankingCategory;
import com.benope.apple.domain.category.bean.RankingCondition;
import com.benope.apple.domain.category.bean.RankingConditionTypeCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingCategoryResponseV2 {

    private Long id;
    private CategoryTypeCd categoryTypeCd;
    private Long parentCategoryNo;
    private String categoryNm;

    private UploadImgResponseV2 iconImg;
    private List<RankingConditionResponse> rankingConditions;

    public static RankingCategoryResponseV2 fromEntity(RankingCategory category) {
        return RankingCategoryResponseV2.builder()
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
                .rankingConditions(
                        DomainObjectUtil.nullSafeStream(category.getRankingConditions())
                                .map(RankingConditionResponse::fromEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RankingConditionResponse {

        @NotNull
        private RankingConditionTypeCd rankingConditionTypeCd;
        @NotNull
        private Long categoryNo;

        public static RankingConditionResponse fromEntity(RankingCondition entity) {
            return RankingConditionResponse.builder()
                    .rankingConditionTypeCd(entity.getRankingConditionTypeCd())
                    .categoryNo(entity.getFoodCategoryNo())
                    .build();
        }

    }
}
