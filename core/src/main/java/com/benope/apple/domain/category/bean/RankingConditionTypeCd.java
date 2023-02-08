package com.benope.apple.domain.category.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RankingConditionTypeCd implements EnumDescribed {

    ALL("전체"),
    MAIN("카테고리"),
    SUB("서브 카테고리");

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
