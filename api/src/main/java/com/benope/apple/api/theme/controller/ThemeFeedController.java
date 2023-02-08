package com.benope.apple.api.theme.controller;

import com.benope.apple.api.theme.bean.ThemeFeedRequest;
import com.benope.apple.api.theme.bean.ThemeFeedResponse;
import com.benope.apple.api.theme.service.ThemeFeedService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.theme.bean.ThemeFeed;
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
public class ThemeFeedController {

    private final ThemeFeedService themeFeedService;

    @PostMapping("/get.theme.feed")
    public ApiResponse getList(
            @RequestBody @Valid ThemeFeedRequest input
    ) {
        Page<ThemeFeed> page = themeFeedService.getList(input.toEntity(), input.toPageable());

        List<ThemeFeedResponse> response = page
                .stream()
                .map(ThemeFeed::getFeed)
                .map(ThemeFeedResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

}
