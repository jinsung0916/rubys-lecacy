package com.benope.apple.admin.category.controller;

import com.benope.apple.admin.category.bean.FoodCategoryRequestV2;
import com.benope.apple.admin.category.bean.FoodCategoryResponseV2;
import com.benope.apple.admin.category.service.FoodCategoryService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.category.bean.FoodCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/category/food", headers = V2_HEADER)
@RequiredArgsConstructor
public class FoodCategoryControllerV2 extends AbstractRestController<FoodCategoryRequestV2, FoodCategoryResponseV2> {

    private final FoodCategoryService foodCategoryService;

    @Override
    protected Page<FoodCategoryResponseV2> findAll(FoodCategoryRequestV2 input, Pageable pageable) {
        return foodCategoryService.getList(input.toEntity(null), pageable)
                .map(FoodCategoryResponseV2::fromEntity);
    }

    @Override
    protected FoodCategoryResponseV2 findById(Long id) {
        return foodCategoryService.getOne(
                        FoodCategory.builder()
                                .categoryNo(id)
                                .build()
                )
                .map(FoodCategoryResponseV2::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    @Override
    protected List<FoodCategoryResponseV2> findByIds(List<Long> ids) {
        return foodCategoryService.getByIds(ids)
                .stream()
                .map(FoodCategoryResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected FoodCategoryResponseV2 create(FoodCategoryRequestV2 input) {
        FoodCategory entity = foodCategoryService.regist(input.toEntity(null));
        return FoodCategoryResponseV2.fromEntity(entity);
    }

    @Override
    protected FoodCategoryResponseV2 update(Long id, FoodCategoryRequestV2 input) {
        FoodCategory entity = foodCategoryService.update(input.toEntity(id));
        return FoodCategoryResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        foodCategoryService.deleteById(id);
    }

}
