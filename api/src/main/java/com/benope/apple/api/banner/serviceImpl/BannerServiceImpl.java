package com.benope.apple.api.banner.serviceImpl;

import com.benope.apple.api.banner.service.BannerService;
import com.benope.apple.domain.banner.bean.Banner;
import com.benope.apple.domain.banner.repository.BannerJpaRepository;
import com.benope.apple.domain.banner.repository.BannerQuerydslPredicates;
import com.benope.apple.utils.DateTimeUtil;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<Banner> getList(Banner banner, Pageable pageable) {
        Predicate predicate = BannerQuerydslPredicates.currentDateTimeBetween(DateTimeUtil.getCurrentDateTime());
        return repository.findAll(predicate, pageable);
    }

}
