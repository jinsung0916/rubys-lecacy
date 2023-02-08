package com.benope.apple.api.banner.controller;

import com.benope.apple.api.banner.bean.BannerRequest;
import com.benope.apple.api.banner.bean.BannerResponse;
import com.benope.apple.api.banner.service.BannerService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.banner.bean.Banner;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PostMapping("/get.banner")
    public ApiResponse getList(
            @RequestBody @Valid BannerRequest input
    ) {
        Page<Banner> page = bannerService.getList(input.toEntity(), input.toPageable());

        List<BannerResponse> responses = page.stream()
                .map(BannerResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(responses, PagingMetadata.withPage(page));
    }

}
