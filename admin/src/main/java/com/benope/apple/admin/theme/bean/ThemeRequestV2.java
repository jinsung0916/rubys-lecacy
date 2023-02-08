package com.benope.apple.admin.theme.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.bean.ThemeCondition;
import com.benope.apple.domain.theme.bean.ThemeConditionCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeRequestV2 implements IdRequest {

    private List<Long> id;

    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String themeNm;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String displayNm;
    @NotNull(groups = Create.class)
    private Long reorderNo;
    private String memo;
    @NotNull(groups = Create.class)
    private Boolean picked;
    @NotNull(groups = Create.class)
    private Boolean displayAll;
    private List<@Valid ThemeConditionRequest> conditions;

    public Theme toEntity(Long themeNo) {
        return Theme.builder()
                .themeNo(themeNo)
                .themeNm(themeNm)
                .displayNm(displayNm)
                .memo(memo)
                .reorderNo(reorderNo)
                .picked(picked)
                .displayAll(displayAll)
                .themeConditions(
                        DomainObjectUtil.nullSafeFunction(
                                conditions,
                                it -> DomainObjectUtil.nullSafeStream(it)
                                        .map(ThemeConditionRequest::toEntity)
                                        .collect(Collectors.toUnmodifiableList())
                        )
                )
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ThemeConditionRequest {

        @NotNull
        private ThemeConditionCd themeConditionCd;
        private ExpertCd expertCd;
        @NotNull
        private Integer batchSize;

        public ThemeCondition toEntity() {
            return ThemeCondition.builder()
                    .themeConditionCd(themeConditionCd)
                    .expertCd(expertCd)
                    .batchSize(batchSize)
                    .build();
        }
    }

}
