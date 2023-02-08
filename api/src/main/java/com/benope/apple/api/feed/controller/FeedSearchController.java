package com.benope.apple.api.feed.controller;

import com.benope.apple.api.feed.bean.FeedSimpleResponse;
import com.benope.apple.api.feed.bean.FeedSolrSearch;
import com.benope.apple.api.feed.service.FeedSearchService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
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
public class FeedSearchController {

    private final FeedSearchService feedSearchService;

    @PostMapping(value = "search.feed.by.food.no")
    public ApiResponse searchFeedByFoodNo(
            @RequestBody @Valid FeedSolrSearch input
    ) {
        Page<FeedSolrEntity> page = feedSearchService.search(input.toEntity(), input.toPageable());

        List<FeedSimpleResponse> result = page.getContent()
                .stream()
                .map(FeedSimpleResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

    @PostMapping(value = "search.feed.by.text")
    public ApiResponse searchFeedByText(
            @RequestBody @Valid FeedSolrSearch input
    ) {
        Page<FeedSolrEntity> page = feedSearchService.search(input.toEntity(), input.toPageable());

        List<FeedSimpleResponse> result = page.getContent()
                .stream()
                .map(FeedSimpleResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

}
