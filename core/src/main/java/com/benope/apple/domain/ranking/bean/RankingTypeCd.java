package com.benope.apple.domain.ranking.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RankingTypeCd implements EnumDescribed {

    FOOD("제품"),
    FOOD_REALTIME("인기 급상승 식단템");

    private final String desc;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

}
