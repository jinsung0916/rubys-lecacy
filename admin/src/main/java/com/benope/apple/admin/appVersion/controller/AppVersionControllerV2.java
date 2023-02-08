package com.benope.apple.admin.appVersion.controller;

import com.benope.apple.admin.appVersion.bean.AppVersionRequestV2;
import com.benope.apple.admin.appVersion.bean.AppVersionResponseV2;
import com.benope.apple.admin.appVersion.service.AppVersionService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/appVersion", headers = V2_HEADER)
@RequiredArgsConstructor
public class AppVersionControllerV2 extends AbstractRestController<AppVersionRequestV2, AppVersionResponseV2> {

    private final AppVersionService appVersionService;

    @Override
    protected Page<AppVersionResponseV2> findAll(AppVersionRequestV2 input, Pageable pageable) {
        return appVersionService.getList(input.toEntity(null), pageable)
                .map(AppVersionResponseV2::fromEntity);
    }

    @Override
    protected AppVersionResponseV2 findById(Long id) {
        return appVersionService.getOne(
                        AppVersion.builder()
                                .appVersionNo(id)
                                .build()
                )
                .map(AppVersionResponseV2::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    @Override
    protected List<AppVersionResponseV2> findByIds(List<Long> ids) {
        return appVersionService.getByIds(ids)
                .stream()
                .map(AppVersionResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected AppVersionResponseV2 create(AppVersionRequestV2 input) {
        AppVersion entity = appVersionService.regist(input.toEntity(null));
        return AppVersionResponseV2.fromEntity(entity);
    }

    @Override
    protected AppVersionResponseV2 update(Long id, AppVersionRequestV2 input) {
        AppVersion entity = appVersionService.update(input.toEntity(id));
        return AppVersionResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        appVersionService.deleteById(id);
    }

}
