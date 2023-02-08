package com.benope.apple.admin.report.bean;

import com.benope.apple.domain.report.bean.FoodReport;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodReportResponseV2 {

    private Long id;
    private Long mbNo;
    private String brand;
    private String foodNm;
    private ZonedDateTime createdAt;

    public static FoodReportResponseV2 fromEntity(FoodReport entity) {
        return FoodReportResponseV2.builder()
                .id(entity.getReportNo())
                .mbNo(entity.getMbNo())
                .brand(entity.getBrand())
                .foodNm(entity.getFoodNm())
                .createdAt(entity.getZonedCreateAt())
                .build();
    }

}
