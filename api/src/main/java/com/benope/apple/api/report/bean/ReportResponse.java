package com.benope.apple.api.report.bean;

import com.benope.apple.domain.report.bean.Report;
import com.benope.apple.domain.report.bean.ReportTargetCd;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {

    private ReportTargetCd reportTargetCd;
    private Long reportTargetNo;
    private String contents;

    public static ReportResponse fromEntity(Report report) {
        return ReportResponse.builder()
                .reportTargetCd(report.getReportTargetCd())
                .reportTargetNo(report.getReportTargetNo())
                .contents(report.getContents())
                .build();
    }
}
