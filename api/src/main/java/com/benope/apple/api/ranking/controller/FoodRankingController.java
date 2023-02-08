package com.benope.apple.api.ranking.controller;

import com.benope.apple.api.ranking.bean.FoodRankingRequest;
import com.benope.apple.api.ranking.bean.FoodRankingResponse;
import com.benope.apple.api.ranking.service.RankingViewService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.ranking.bean.RankingView;
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
public class FoodRankingController {

    private final RankingViewService rankingViewService;

    @PostMapping("/get.food.ranking")
    public ApiResponse getList(
            @RequestBody @Valid FoodRankingRequest input
    ) {
        Page<RankingView> page = rankingViewService.getList(input.toEntity(), input.toPageable());

        List<FoodRankingResponse> response = page.stream()
                .map(FoodRankingResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

}
