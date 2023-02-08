package com.benope.apple.api.theme.controller;

import com.benope.apple.api.theme.bean.ThemePickedRequest;
import com.benope.apple.api.theme.bean.ThemeRequest;
import com.benope.apple.api.theme.bean.ThemeResponse;
import com.benope.apple.api.theme.service.ThemeFeedService;
import com.benope.apple.api.theme.service.ThemeService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    private final ThemeFeedService themeFeedService;

    @PostMapping("/get.theme")
    public ApiResponse getList(
            @RequestBody @Valid ThemeRequest input
    ) {
        Page<Theme> page = themeService.getList(input.toEntity(), input.toPageable());

        List<ThemeResponse> themeResponse = page
                .stream()
                .map(ThemeResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(themeResponse, PagingMetadata.withPage(page));
    }

    @PostMapping("/get.theme.picked")
    public ApiResponse getPickedList(
            @RequestBody @Valid ThemePickedRequest input
    ) {
        Page<Theme> page = themeService.getList(input.toEntity(), input.toPageable());

        List<ThemeResponse> themeResponse = page
                .stream()
                .map(ThemeResponse::fromEntity)
                .peek(this::retrieveThemeFeeds)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(themeResponse, PagingMetadata.withPage(page));
    }

    private void retrieveThemeFeeds(ThemeResponse response) {
        Page<ThemeFeed> themeFeeds = themeFeedService.getList(
                ThemeFeed.builder()
                        .themeNo(response.getThemeNo())
                        .build(),
                PageRequest.ofSize(10).withSort(Sort.by(Sort.Order.asc("reorderNo"), Sort.Order.desc("themeFeedNo")))
        );

        response.setThemeFeeds(themeFeeds.toList());
    }

}
