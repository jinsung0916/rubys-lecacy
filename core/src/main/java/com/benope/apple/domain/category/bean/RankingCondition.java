package com.benope.apple.domain.category.bean;

import lombok.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingCondition {

    @Enumerated(EnumType.STRING)
    private RankingConditionTypeCd rankingConditionTypeCd;
    private Long foodCategoryNo;

    protected void validate() {
        Assert.notNull(rankingConditionTypeCd);
        Assert.notNull(foodCategoryNo);
    }

}
