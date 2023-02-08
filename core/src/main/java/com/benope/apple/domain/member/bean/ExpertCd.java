package com.benope.apple.domain.member.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExpertCd implements EnumDescribed {

    PRO("프로식단러");

    private final String desc;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
