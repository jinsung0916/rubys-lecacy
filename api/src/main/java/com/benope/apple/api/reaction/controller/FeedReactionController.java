package com.benope.apple.api.reaction.controller;

import com.benope.apple.api.reaction.bean.FeedReactionRequest;
import com.benope.apple.api.reaction.service.FeedReactionService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FeedReactionController {

    private final FeedReactionService reactionService;

    @PostMapping(value = "chk.feed.favorite")
    public ApiResponse chkFeedFavorite(
            @RequestBody @Valid FeedReactionRequest input
    ) {
        boolean isLike = reactionService.toggleFeedLike(SessionUtil.getSessionMbNo(), input.getFeedNo());
        return RstCode.SUCCESS.toApiResponse(
                Map.of(
                        "feed_no", input.getFeedNo(),
                        "like", isLike
                )
        );
    }
}
