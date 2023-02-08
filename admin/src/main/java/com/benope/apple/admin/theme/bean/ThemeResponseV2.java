package com.benope.apple.admin.theme.bean;

import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.bean.ThemeCondition;
import com.benope.apple.domain.theme.bean.ThemeConditionCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ThemeResponseV2 {

    private Long id;
    private String themeNm;
    private String displayNm;
    private String memo;
    private Long reorderNo;
    private boolean picked;
    private boolean displayAll;
    private List<ThemeConditionResponse> conditions;

    public static ThemeResponseV2 fromEntity(Theme theme) {
        return ThemeResponseV2.builder()
                .id(theme.getThemeNo())
                .themeNm(theme.getThemeNm())
                .displayNm(theme.getDisplayNm())
                .memo(theme.getMemo())
                .reorderNo(theme.getReorderNo())
                .picked(theme.picked())
                .displayAll(theme.displayAll())
                .conditions(
                        DomainObjectUtil.nullSafeStream(theme.getThemeConditions())
                                .map(ThemeConditionResponse::fromEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ThemeConditionResponse {

        @NotNull
        private ThemeConditionCd themeConditionCd;
        private ExpertCd expertCd;
        @NotNull
        private Integer batchSize;

        public static ThemeConditionResponse fromEntity(ThemeCondition condition) {
            return ThemeConditionResponse.builder()
                    .themeConditionCd(condition.getThemeConditionCd())
                    .expertCd(condition.getExpertCd())
                    .batchSize(condition.getBatchSize())
                    .build();
        }
    }

}
