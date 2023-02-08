package com.benope.apple.api.report.serviceImpl;

import com.benope.apple.api.report.service.FoodReportService;
import com.benope.apple.domain.report.bean.FoodReport;
import com.benope.apple.domain.report.repository.ReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodReportServiceImpl implements FoodReportService {

    private final ReportJpaRepository repository;

    @Override
    public FoodReport regist(FoodReport foodReport) {
        return repository.save(foodReport);
    }

}
