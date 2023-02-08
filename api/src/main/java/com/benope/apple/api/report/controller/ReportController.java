package com.benope.apple.api.report.controller;

import com.benope.apple.api.report.bean.ReportRequest;
import com.benope.apple.api.report.bean.ReportResponse;
import com.benope.apple.api.report.service.ReportService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.report.bean.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/reg.report")
    public ApiResponse registReport(@RequestBody @Valid ReportRequest request) {
        Report entity =reportService.regist(request.toEntity());
        return RstCode.SUCCESS.toApiResponse(ReportResponse.fromEntity(entity));
    }

}
