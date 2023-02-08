package com.benope.apple.api.category.controller;

import com.benope.apple.api.category.bean.CategoryRequest;
import com.benope.apple.api.category.bean.CategoryResponse;
import com.benope.apple.api.category.service.CategoryService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.category.bean.Category;
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
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/get.category")
    public ApiResponse getList(
            @RequestBody @Valid CategoryRequest input
    ) {
        Page<Category> page = categoryService.getList(input.toEntity(), input.toPageable());

        List<CategoryResponse> responses = page.stream()
                .map(CategoryResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(responses, PagingMetadata.withPage(page));
    }

}
