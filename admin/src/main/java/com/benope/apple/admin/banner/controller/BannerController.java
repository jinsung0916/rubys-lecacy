package com.benope.apple.admin.banner.controller;

import com.benope.apple.admin.banner.bean.BannerRequest;
import com.benope.apple.admin.banner.bean.BannerResponse;
import com.benope.apple.admin.banner.service.BannerService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.banner.bean.Banner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/banner", headers = V2_HEADER)
@RequiredArgsConstructor
public class BannerController extends AbstractRestController<BannerRequest, BannerResponse> {

    private final BannerService bannerService;

    @Override
    protected Page<BannerResponse> findAll(BannerRequest input, Pageable pageable) {
        return bannerService.getList(input.toEntity(null), pageable)
                .map(BannerResponse::fromEntity);
    }

    @Override
    protected BannerResponse findById(Long id) {
        return bannerService.getOne(
                        Banner.builder()
                                .bannerNo(id)
                                .build()
                )
                .map(BannerResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    @Override
    protected List<BannerResponse> findByIds(List<Long> ids) {
        return bannerService.getByIds(ids)
                .stream()
                .map(BannerResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected BannerResponse create(BannerRequest input) {
        Banner entity = bannerService.regist(input.toEntity(null));
        return BannerResponse.fromEntity(entity);
    }

    @Override
    protected BannerResponse update(Long id, BannerRequest input) {
        Banner entity = bannerService.update(input.toEntity(id));
        return BannerResponse.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        bannerService.deleteById(id);
    }

}
