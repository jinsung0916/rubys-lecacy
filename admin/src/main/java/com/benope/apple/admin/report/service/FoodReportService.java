package com.benope.apple.admin.report.service;

import com.benope.apple.domain.report.bean.FoodReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface FoodReportService {

    Page<FoodReport> getList(@NotNull FoodReport report, @NotNull Pageable pageable);

    Optional<FoodReport> getOne(@NotNull FoodReport report);

    List<FoodReport> getByIds(@NotNull List<Long> ids);

}
