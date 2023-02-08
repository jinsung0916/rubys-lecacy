package com.benope.apple.api.feed.controller;

import com.benope.apple.api.feed.bean.FeedRequest;
import com.benope.apple.api.feed.bean.FeedResponse;
import com.benope.apple.api.feed.bean.FeedSearchRequest;
import com.benope.apple.api.feed.bean.FeedSimpleResponse;
import com.benope.apple.api.feed.service.FeedService;
import com.benope.apple.api.reaction.service.FeedReactionService;
import com.benope.apple.api.scrap.service.ScrapHelperService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Read;
import com.benope.apple.config.validation.Update;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.utils.PagingMetadata;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    private final FeedReactionService reactionService;
    private final ScrapHelperService scrapHelperService;

    @PostMapping(value = "get.feed")
    public ApiResponse getList(
            @RequestBody @Valid FeedSearchRequest input
    ) {
        Page<Feed> page = feedService.getList(input.toEntity(), input.toPageable());

        List<FeedSimpleResponse> result = page.stream()
                .map(FeedSimpleResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

    @PostMapping(value = "get.feed.detail")
    public ApiResponse getDetail(
            @RequestBody @Validated({Default.class, Read.class}) FeedRequest input
    ) {
        FeedResponse response = feedService.getById(input.getFeedNo(), true)
                .map(FeedResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        if (SessionUtil.isAuthenticated()) {
            boolean isLike = reactionService.isFeedLike(SessionUtil.getSessionMbNo(), response.getFeedNo());
            response.setLike(isLike);

            boolean isScrap = scrapHelperService.checkScrap(SessionUtil.getSessionMbNo(), input.getFeedNo());
            response.setScrap(isScrap);
        }

        return RstCode.SUCCESS.toApiResponse(response);
    }

    @PostMapping(value = "reg.feed")
    public ApiResponse regist(
            @RequestBody @Validated({Default.class, Create.class}) FeedRequest input
    ) {
        Feed feed = feedService.regist(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FeedResponse.fromEntity(feed));
    }

    @PostMapping(value = "mod.feed")
    public ApiResponse update(
            @RequestBody @Validated({Default.class, Update.class}) FeedRequest input
    ) {
        Feed feed = feedService.update(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FeedResponse.fromEntity(feed));
    }

    @PostMapping(value = "del.feed")
    public ApiResponse delete(
            @RequestBody @Validated({Default.class, Delete.class}) FeedRequest input
    ) {
        feedService.deleteById(input.getFeedNo());
        return RstCode.SUCCESS.toApiResponse();

    }
}
