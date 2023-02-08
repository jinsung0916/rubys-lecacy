package com.benope.apple.domain.term.repository;

import com.benope.apple.domain.term.bean.TermDetail;
import com.benope.apple.domain.term.bean.TermDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TermDetailJpaRepository extends JpaRepository<TermDetail, TermDetailId>, JpaSpecificationExecutor<TermDetail> {
}
