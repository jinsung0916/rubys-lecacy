package com.benope.apple.admin.food.controller;

import com.benope.apple.admin.comment.service.FoodCommentService;
import com.benope.apple.admin.food.bean.FoodRequestV2;
import com.benope.apple.admin.food.bean.FoodResponseV2;
import com.benope.apple.admin.food.service.FoodService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.config.webMvc.WebMvcConfig;
import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.domain.food.bean.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/food", headers = WebMvcConfig.V2_HEADER)
@RequiredArgsConstructor
public class FoodControllerV2 extends AbstractRestController<FoodRequestV2, FoodResponseV2> {

    private final FoodService foodService;
    private final FoodCommentService foodCommentService;


    @Override
    protected Page<FoodResponseV2> findAll(FoodRequestV2 input, Pageable pageable) {
        return foodService.getList(input.toEntity(null), pageable)
                .map(FoodResponseV2::fromEntity);
    }

    @Override
    protected FoodResponseV2 findById(Long id) {
        Food entity = foodService.getOne(
                Food.builder()
                        .foodNo(id)
                        .build()
        )
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        return FoodResponseV2.fromEntity(entity)
                .setCommentCount(getCommentFount(entity));
    }

    private long getCommentFount(Food food) {
        FoodComment cond = FoodComment.builder()
                .foodNo(food.getFoodNo())
                .build();

        return foodCommentService.count(cond);
    }

    @Override
    protected List<FoodResponseV2> findByIds(List<Long> ids) {
        return foodService.getByIds(ids)
                .stream()
                .map(FoodResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected FoodResponseV2 create(FoodRequestV2 input) {
        Food entity = foodService.regist(input.toEntity(null));
        return FoodResponseV2.fromEntity(entity);
    }

    @Override
    protected FoodResponseV2 update(Long id, FoodRequestV2 input) {
        Food entity = foodService.regist(input.toEntity(id));
        return FoodResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        foodService.deleteById(id);
    }

}
