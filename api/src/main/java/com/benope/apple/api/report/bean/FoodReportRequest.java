package com.benope.apple.api.report.bean;

import com.benope.apple.domain.report.bean.FoodReport;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodReportRequest {

    @NotBlank
    private String contents_1;
    @NotBlank
    private String contents_2;

    public FoodReport toEntity() {
        return FoodReport.builder()
                .mbNo(SessionUtil.getSessionMbNo())
                .brand(contents_1)
                .foodNm(contents_2)
                .build();
    }
}
