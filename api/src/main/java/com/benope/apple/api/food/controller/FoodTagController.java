package com.benope.apple.api.food.controller;

import com.benope.apple.api.food.bean.FoodTagRequest;
import com.benope.apple.api.food.bean.FoodTagResponse;
import com.benope.apple.api.food.service.TagService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.food.bean.FoodTag;
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
public class FoodTagController {

    private final TagService tagService;

    @PostMapping("/get.food.tag")
    public ApiResponse getList(
            @RequestBody @Valid FoodTagRequest input
    ) {
        Page<FoodTag> page = tagService.getList(input.toEntity(), input.toPageable());

        List<FoodTagResponse> result = page.stream()
                .map(FoodTagResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

}
