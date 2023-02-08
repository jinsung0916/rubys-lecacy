package com.benope.apple.domain.category.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryTypeCd implements EnumDescribed {

    FOOD("제품 DB"),
    RANKING("제품 랭킹");

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
