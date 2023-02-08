package com.benope.apple.api.report.serviceImpl;

import com.benope.apple.api.report.service.ReportService;
import com.benope.apple.domain.report.bean.Report;
import com.benope.apple.domain.report.repository.ReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportJpaRepository repository;

    @Override
    public Report regist(Report report) {
        return repository.save(report);
    }

}
