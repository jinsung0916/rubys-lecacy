package com.benope.apple.api.report.service;

import com.benope.apple.domain.report.bean.Report;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ReportService {

    @NotNull Report regist(@NotNull Report report);

}
