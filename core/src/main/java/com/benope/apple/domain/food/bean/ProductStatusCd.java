package com.benope.apple.domain.food.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductStatusCd implements EnumDescribed {

    FOR_SALE("입점"),
    NOT_FOR_SALE("미입점");

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
