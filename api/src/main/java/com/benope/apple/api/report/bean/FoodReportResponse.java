package com.benope.apple.api.report.bean;

import com.benope.apple.domain.report.bean.FoodReport;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodReportResponse {

    private String contents_1;
    private String contents_2;

    public static FoodReportResponse fromEntity(FoodReport report) {
        return FoodReportResponse.builder()
                .contents_1(report.getBrand())
                .contents_2(report.getFoodNm())
                .build();
    }

}
