package com.benope.apple.api.report.bean;

import com.benope.apple.domain.report.bean.CommentReport;
import com.benope.apple.domain.report.bean.FeedReport;
import com.benope.apple.domain.report.bean.Report;
import com.benope.apple.domain.report.bean.ReportTargetCd;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportRequest {

    @NotNull
    private ReportTargetCdRequest reportTargetCd;
    @NotNull
    private Long reportTargetNo;
    @NotBlank
    private String contents;

    public Report toEntity() {
        ReportTargetCd actualCd = reportTargetCd.getActualCd();

        if(ReportTargetCd.FEED.equals(actualCd)) {
            return FeedReport.builder()
                    .mbNo(SessionUtil.getSessionMbNo())
                    .feedNo(reportTargetNo)
                    .contents(contents)
                    .build();
        } else if(ReportTargetCd.COMMENT.equals(actualCd)) {
            return CommentReport.builder()
                    .mbNo(SessionUtil.getSessionMbNo())
                    .commentNo(reportTargetNo)
                    .contents(contents)
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum ReportTargetCdRequest {
        FEED(ReportTargetCd.FEED),
        FEED_COMMENT(ReportTargetCd.COMMENT),
        COMMUNITY(ReportTargetCd.COMMUNITY);

        private final ReportTargetCd actualCd;
    }

}
