package com.benope.apple.domain.feed.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FeedTypeCd implements EnumDescribed {

    FEED("피드");

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
