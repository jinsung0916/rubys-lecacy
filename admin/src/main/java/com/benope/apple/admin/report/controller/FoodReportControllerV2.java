package com.benope.apple.admin.report.controller;

import com.benope.apple.admin.report.bean.FoodReportRequestV2;
import com.benope.apple.admin.report.bean.FoodReportResponseV2;
import com.benope.apple.admin.report.service.FoodReportService;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.WebMvcConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/report/food", headers = WebMvcConfig.V2_HEADER)
@RequiredArgsConstructor
public class FoodReportControllerV2 extends AbstractRestController<FoodReportRequestV2, FoodReportResponseV2> {

    private final FoodReportService foodReportService;

    @Override
    protected Page<FoodReportResponseV2> findAll(FoodReportRequestV2 input, Pageable pageable) {
        return foodReportService.getList(input.toEntity(), pageable)
                .map(FoodReportResponseV2::fromEntity);
    }

    @Override
    protected FoodReportResponseV2 findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<FoodReportResponseV2> findByIds(List<Long> ids) {
        return foodReportService.getByIds(ids)
                .stream()
                .map(FoodReportResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected FoodReportResponseV2 create(FoodReportRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected FoodReportResponseV2 update(Long id, FoodReportRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

}
