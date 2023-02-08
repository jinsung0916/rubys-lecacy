package com.benope.apple.domain.scrap.repository;

import com.benope.apple.domain.scrap.bean.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScrapJpaRepository extends JpaRepository<Scrap, Long>, JpaSpecificationExecutor<Scrap> {
}
