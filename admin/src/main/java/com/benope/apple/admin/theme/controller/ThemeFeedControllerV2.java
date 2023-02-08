package com.benope.apple.admin.theme.controller;

import com.benope.apple.admin.theme.bean.ThemeFeedRequestV2;
import com.benope.apple.admin.theme.bean.ThemeFeedResponseV2;
import com.benope.apple.admin.theme.service.ThemeFeedService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.config.webMvc.WebMvcConfig;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/themeFeed", headers = WebMvcConfig.V2_HEADER)
@RequiredArgsConstructor
public class ThemeFeedControllerV2 extends AbstractRestController<ThemeFeedRequestV2, ThemeFeedResponseV2> {

    private final ThemeFeedService themeFeedService;

    @Override
    protected Page<ThemeFeedResponseV2> findAll(ThemeFeedRequestV2 input, Pageable pageable) {
        return themeFeedService.getList(input.toEntity(null), pageable)
                .map(ThemeFeedResponseV2::fromEntity);
    }

    @Override
    protected ThemeFeedResponseV2 findById(Long id) {
        return themeFeedService.getOne(
                        ThemeFeed.builder()
                                .themeFeedNo(id)
                                .build()
                )
                .map(ThemeFeedResponseV2::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    @Override
    protected List<ThemeFeedResponseV2> findByIds(List<Long> ids) {
        return themeFeedService.getByIds(ids)
                .stream()
                .map(ThemeFeedResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected ThemeFeedResponseV2 create(ThemeFeedRequestV2 input) {
        ThemeFeed entity = themeFeedService.regist(input.toEntity(null));
        return ThemeFeedResponseV2.fromEntity(entity);
    }

    @Override
    protected ThemeFeedResponseV2 update(Long id, ThemeFeedRequestV2 input) {
        ThemeFeed entity = themeFeedService.update(input.toEntity(id));
        return ThemeFeedResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        themeFeedService.deleteById(id);
    }

}
