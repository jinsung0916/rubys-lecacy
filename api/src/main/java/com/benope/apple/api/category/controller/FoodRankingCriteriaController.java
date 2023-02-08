package com.benope.apple.api.category.controller;

import com.benope.apple.api.category.bean.FoodRankingCriteriaRequest;
import com.benope.apple.api.category.bean.FoodRankingCriteriaResponse;
import com.benope.apple.api.category.service.RankingCriteriaService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FoodRankingCriteriaController {

    private final RankingCriteriaService rankingCriteriaService;

    @PostMapping("/get.food.ranking.criteria")
    public ApiResponse getList(
            @RequestBody @Valid FoodRankingCriteriaRequest input
    ) {
        List<FoodRankingCriteriaResponse> result = rankingCriteriaService.getList(input.toEntity())
                .stream()
                .map(FoodRankingCriteriaResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result);
    }

}
