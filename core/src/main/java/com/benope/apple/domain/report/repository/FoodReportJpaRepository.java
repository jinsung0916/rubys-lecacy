package com.benope.apple.domain.report.repository;

import com.benope.apple.domain.report.bean.FoodReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoodReportJpaRepository extends JpaRepository<FoodReport, Long>, JpaSpecificationExecutor<FoodReport> {
}
