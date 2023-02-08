package com.benope.apple.admin.appVersion.serviceImpl;

import com.benope.apple.admin.appVersion.service.AppVersionHelperService;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import com.benope.apple.domain.appVersion.repository.AppVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppVersionHelperServiceImpl implements AppVersionHelperService {

    private final AppVersionJpaRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean isExist(AppVersion appVersion) {
        Example<AppVersion> example = Example.of(appVersion, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.exists(example);
    }

}
