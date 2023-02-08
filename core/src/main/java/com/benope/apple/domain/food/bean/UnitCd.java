package com.benope.apple.domain.food.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UnitCd implements EnumDescribed {

    MG("mg"),
    G("g"),
    KG("kg"),
    ML("ml"),
    L("l");

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
