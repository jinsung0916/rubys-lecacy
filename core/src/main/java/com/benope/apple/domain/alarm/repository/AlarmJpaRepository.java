package com.benope.apple.domain.alarm.repository;

import com.benope.apple.domain.alarm.bean.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlarmJpaRepository extends JpaRepository<Alarm, Long>, JpaSpecificationExecutor<Alarm> {
}
