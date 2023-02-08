package com.benope.apple.api.report.service;

import com.benope.apple.domain.report.bean.FoodReport;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface FoodReportService {

    @NotNull FoodReport regist(@NotNull FoodReport foodReport);

}
