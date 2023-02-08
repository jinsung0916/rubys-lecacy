package com.benope.apple.api.food.controller;

import com.benope.apple.api.food.bean.FoodRandomRequest;
import com.benope.apple.api.food.bean.FoodRequest;
import com.benope.apple.api.food.bean.FoodResponse;
import com.benope.apple.api.food.service.FoodScoreViewService;
import com.benope.apple.api.food.service.FoodService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodScoreView;
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
public class FoodController {

    private final FoodService foodService;
    private final FoodScoreViewService foodScoreViewService;

    @PostMapping("/get.food")
    public ApiResponse getFood(
            @RequestBody @Valid FoodRequest input
    ) {
        Food food = foodService.getById(input.getFoodNo(), true)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        return RstCode.SUCCESS.toApiResponse(FoodResponse.fromEntity(food));
    }

    @PostMapping("/get.random.food")
    public ApiResponse getRandomFood(
            @RequestBody @Valid FoodRandomRequest input
    ) {
        Page<FoodScoreView> page = foodScoreViewService.getRandomList(input.toEntity(), input.toPageable());

        List<FoodResponse> response = page.stream()
                .map(FoodResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

}
