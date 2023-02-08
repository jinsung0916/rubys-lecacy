package com.benope.apple.admin.report.serviceImpl;

import com.benope.apple.admin.report.service.FoodReportService;
import com.benope.apple.domain.report.bean.FoodReport;
import com.benope.apple.domain.report.repository.FoodReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodReportServiceImpl implements FoodReportService {

    private final FoodReportJpaRepository repository;

    @Override
    public Page<FoodReport> getList(FoodReport report, Pageable pageable) {
        Example<FoodReport> example = Example.of(
                report,
                ExampleMatcher.matchingAll().withIgnoreNullValues()
                        .withMatcher("brand", ExampleMatcher.GenericPropertyMatcher::contains)
                        .withMatcher("foodNm", ExampleMatcher.GenericPropertyMatcher::contains)
        );

        return repository.findAll(example, pageable);
    }

    @Override
    public Optional<FoodReport> getOne(FoodReport report) {
        Example<FoodReport> example = Example.of(report, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Override
    public List<FoodReport> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

}
