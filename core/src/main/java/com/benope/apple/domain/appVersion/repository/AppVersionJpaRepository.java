package com.benope.apple.domain.appVersion.repository;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppVersionJpaRepository extends JpaRepository<AppVersion, Long>, JpaSpecificationExecutor<AppVersion> {
}
