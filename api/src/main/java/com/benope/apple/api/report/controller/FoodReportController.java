package com.benope.apple.api.report.controller;

import com.benope.apple.api.report.bean.FoodReportRequest;
import com.benope.apple.api.report.bean.FoodReportResponse;
import com.benope.apple.api.report.service.FoodReportService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.report.bean.FoodReport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FoodReportController {

    private final FoodReportService foodReportService;

    @PostMapping("/reg.food.report")
    public ApiResponse registFoodReport(
            @RequestBody @Valid FoodReportRequest input
    ) {
        FoodReport entity = foodReportService.regist(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FoodReportResponse.fromEntity(entity));
    }

}
