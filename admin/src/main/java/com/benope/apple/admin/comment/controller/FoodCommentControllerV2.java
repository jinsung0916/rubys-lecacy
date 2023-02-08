package com.benope.apple.admin.comment.controller;

import com.benope.apple.admin.comment.bean.FoodCommentRequestV2;
import com.benope.apple.admin.comment.bean.FoodCommentResponseV2;
import com.benope.apple.admin.comment.service.FoodCommentService;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.WebMvcConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/comment/food", headers = WebMvcConfig.V2_HEADER)
@RequiredArgsConstructor
public class FoodCommentControllerV2 extends AbstractRestController<FoodCommentRequestV2, FoodCommentResponseV2> {

    private final FoodCommentService foodCommentService;

    @Override
    protected Page<FoodCommentResponseV2> findAll(FoodCommentRequestV2 input, Pageable pageable) {
        return foodCommentService.getList(input.toEntity(), pageable)
                .map(FoodCommentResponseV2::fromEntity);
    }

    @Override
    protected FoodCommentResponseV2 findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<FoodCommentResponseV2> findByIds(List<Long> ids) {
        return foodCommentService.getByIds(ids)
                .stream()
                .map(FoodCommentResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected FoodCommentResponseV2 create(FoodCommentRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected FoodCommentResponseV2 update(Long id, FoodCommentRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

}
