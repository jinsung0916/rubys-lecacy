package com.benope.apple.domain.report.repository;

import com.benope.apple.domain.report.bean.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReportJpaRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
}
