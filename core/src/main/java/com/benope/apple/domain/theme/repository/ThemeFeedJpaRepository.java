package com.benope.apple.domain.theme.repository;

import com.benope.apple.domain.theme.bean.ThemeFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ThemeFeedJpaRepository extends JpaRepository<ThemeFeed, Long>, JpaSpecificationExecutor<ThemeFeed> {

    void deleteByThemeNo(Long themeNo);

}
