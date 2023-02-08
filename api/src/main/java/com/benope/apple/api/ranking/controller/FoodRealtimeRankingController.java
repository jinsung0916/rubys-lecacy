package com.benope.apple.api.ranking.controller;

import com.benope.apple.api.ranking.bean.FoodRealtimeRankingRequest;
import com.benope.apple.api.ranking.bean.FoodRealtimeRankingResponse;
import com.benope.apple.api.ranking.service.FoodRealtimeRankingService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
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
public class FoodRealtimeRankingController {

    private final FoodRealtimeRankingService foodRealtimeRankingService;

    @PostMapping("/get.food.realtime.ranking")
    public ApiResponse getList(
            @RequestBody @Valid FoodRealtimeRankingRequest input
    ) {
        Page<FoodRealtimeRanking> page = foodRealtimeRankingService.getList(input.toEntity(), input.toPageable());

        List<FoodRealtimeRankingResponse> response = page.stream()
                .map(FoodRealtimeRankingResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

}
