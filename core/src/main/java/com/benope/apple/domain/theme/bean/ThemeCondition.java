package com.benope.apple.domain.theme.bean;

import com.benope.apple.domain.member.bean.ExpertCd;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ThemeCondition {

    @Enumerated(EnumType.STRING)
    private ThemeConditionCd themeConditionCd;
    @Enumerated(EnumType.STRING)
    private ExpertCd expertCd;
    private int batchSize;

}
