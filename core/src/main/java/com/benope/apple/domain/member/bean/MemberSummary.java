package com.benope.apple.domain.member.bean;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberSummary {

    private final Long mb_no;
    private final MbSummaryCd mbSummaryCd;
    private final Long summaryCnt;

    @QueryProjection
    public MemberSummary(Long mbNo, MbSummaryCd mbSummaryCd, Long summaryCnt) {
        this.mb_no = mbNo;
        this.mbSummaryCd = mbSummaryCd;
        this.summaryCnt = summaryCnt;
    }

}