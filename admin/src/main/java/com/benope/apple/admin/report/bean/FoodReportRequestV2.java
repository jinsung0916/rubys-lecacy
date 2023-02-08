package com.benope.apple.admin.report.bean;

import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.report.bean.FoodReport;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodReportRequestV2 implements IdRequest {

    private List<Long> id;

    private Long mbNo;
    private String brand;
    private String foodNm;

    public FoodReport toEntity() {
        return FoodReport.builder()
                .mbNo(mbNo)
                .brand(brand)
                .foodNm(foodNm)
                .build();
    }

}
