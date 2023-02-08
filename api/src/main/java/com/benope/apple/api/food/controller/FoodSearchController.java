package com.benope.apple.api.food.controller;

import com.benope.apple.api.food.bean.FoodResponse;
import com.benope.apple.api.food.bean.FoodSolrSearch;
import com.benope.apple.api.food.service.FoodSearchService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FoodSearchController {

    private final FoodSearchService foodSearchService;

    @PostMapping("/search.food.by.text")
    public ApiResponse searchFoodByText(
            @RequestBody @Valid FoodSolrSearch input
    ) {
        Page<FoodResponse> page = foodSearchService.search(input.toEntity(), input.toPageable())
                .map(FoodResponse::fromEntity);

        return RstCode.SUCCESS.toApiResponse(page.getContent(), PagingMetadata.withPage(page));
    }

}
