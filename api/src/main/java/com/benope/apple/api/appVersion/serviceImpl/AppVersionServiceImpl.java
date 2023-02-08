package com.benope.apple.api.appVersion.serviceImpl;

import com.benope.apple.api.appVersion.service.AppVersionService;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import com.benope.apple.domain.appVersion.repository.AppVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<AppVersion> getList(AppVersion appVersion, Pageable pageable) {
        Example<AppVersion> example = Example.of(appVersion, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AppVersion> getOne(AppVersion appVersion) {
        Example<AppVersion> example = Example.of(appVersion, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

}
