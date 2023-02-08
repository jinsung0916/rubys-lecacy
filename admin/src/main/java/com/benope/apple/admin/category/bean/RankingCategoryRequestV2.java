package com.benope.apple.admin.category.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.category.bean.RankingCategory;
import com.benope.apple.domain.category.bean.RankingCondition;
import com.benope.apple.domain.category.bean.RankingConditionTypeCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingCategoryRequestV2 implements IdRequest {

    private List<Long> id;

    @NotNull(groups = Create.class)
    private String categoryNm;

    private List<@Valid RankingConditionRequest> rankingConditions;
    private Long iconImgNo;

    public RankingCategory toEntity(Long categoryNo) {
        return RankingCategory.builder()
                .categoryNo(categoryNo)
                .categoryNm(categoryNm)
                .rankingConditions(
                        DomainObjectUtil.nullSafeStream(rankingConditions)
                                .map(RankingConditionRequest::toEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .iconImgNo(iconImgNo)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RankingConditionRequest {

        @NotNull
        private RankingConditionTypeCd rankingConditionTypeCd;
        private Long categoryNo;

        public RankingCondition toEntity() {
            return RankingCondition.builder()
                    .rankingConditionTypeCd(rankingConditionTypeCd)
                    .foodCategoryNo(categoryNo)
                    .build();
        }
    }

}
