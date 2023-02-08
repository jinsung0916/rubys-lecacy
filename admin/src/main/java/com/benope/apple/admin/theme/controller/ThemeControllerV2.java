package com.benope.apple.admin.theme.controller;

import com.benope.apple.admin.theme.bean.ThemeRequestV2;
import com.benope.apple.admin.theme.bean.ThemeResponseV2;
import com.benope.apple.admin.theme.service.ThemeService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.config.webMvc.WebMvcConfig;
import com.benope.apple.domain.theme.bean.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/theme", headers = WebMvcConfig.V2_HEADER)
@RequiredArgsConstructor
public class ThemeControllerV2 extends AbstractRestController<ThemeRequestV2, ThemeResponseV2> {

    private final ThemeService themeService;

    @Override
    protected Page<ThemeResponseV2> findAll(ThemeRequestV2 input, Pageable pageable) {
        return themeService.getList(input.toEntity(null), pageable)
                .map(ThemeResponseV2::fromEntity);
    }

    @Override
    protected ThemeResponseV2 findById(Long id) {
        return themeService.getOne(
                        Theme.builder()
                                .themeNo(id)
                                .build()
                )
                .map(ThemeResponseV2::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    @Override
    protected List<ThemeResponseV2> findByIds(List<Long> ids) {
        return themeService.getByIds(ids)
                .stream()
                .map(ThemeResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected ThemeResponseV2 create(ThemeRequestV2 input) {
        Theme entity = themeService.regist(input.toEntity(null));
        return ThemeResponseV2.fromEntity(entity);
    }

    @Override
    protected ThemeResponseV2 update(Long id, ThemeRequestV2 input) {
        Theme entity = themeService.update(input.toEntity(id));
        return ThemeResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        themeService.deleteById(id);
    }

}
