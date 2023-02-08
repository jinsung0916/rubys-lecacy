package com.benope.apple.domain.loginHistory.repository;

import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoginHistoryJpaRepository extends JpaRepository<LoginHistory, Long>, JpaSpecificationExecutor<LoginHistory> {

    @Modifying
    @Query("UPDATE LoginHistory SET rowStatCd = 'D' WHERE mbNo = :mbNo")
    void deleteByMbNo(Long mbNo);

}
