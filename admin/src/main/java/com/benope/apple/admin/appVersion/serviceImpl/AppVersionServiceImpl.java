package com.benope.apple.admin.appVersion.serviceImpl;

import com.benope.apple.admin.appVersion.service.AppVersionHelperService;
import com.benope.apple.admin.appVersion.service.AppVersionService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import com.benope.apple.domain.appVersion.repository.AppVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionJpaRepository repository;

    private final AppVersionHelperService appVersionHelperService;

    private final ModelMapper modelMapper;

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

    @Transactional(readOnly = true)
    @Override
    public List<AppVersion> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public AppVersion regist(AppVersion appVersion) {
        AppVersion entity = repository.save(appVersion);

        if (isVersionExists(appVersion)) {
            throw new BusinessException(RstCode.VERSION_ALREADY_REGISTERED);
        }

        return entity;
    }

    @Override
    public AppVersion update(AppVersion appVersion) {
        AppVersion entity = repository.findById(appVersion.getAppVersionNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(appVersion, entity);
        repository.saveAndFlush(entity);

        if (isVersionExists(appVersion)) {
            throw new BusinessException(RstCode.VERSION_ALREADY_REGISTERED);
        }

        return entity;
    }

    private boolean isVersionExists(AppVersion version) {
        return appVersionHelperService.isExist(
                AppVersion.builder()
                        .os(version.getOs())
                        .version(Objects.requireNonNullElse(version.getVersion(), ""))
                        .build()
        );
    }

    @Override
    public void deleteById(Long appVersionNo) {
        repository.deleteById(appVersionNo);
    }

}
