package com.benope.apple.domain.report.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReportTargetCd implements EnumDescribed {

    FEED("포스트 신고"),
    COMMUNITY("커뮤니티 신고"),
    COMMENT("댓글 신고"),
    FOOD("제품 요청");

    private final String desc;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
