package com.benope.apple.admin.category.controller;

import com.benope.apple.admin.category.bean.RankingCategoryRequestV2;
import com.benope.apple.admin.category.bean.RankingCategoryResponseV2;
import com.benope.apple.admin.category.service.RankingCategoryService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.category.bean.RankingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/category/ranking", headers = V2_HEADER)
@RequiredArgsConstructor
public class RankingCategoryControllerV2 extends AbstractRestController<RankingCategoryRequestV2, RankingCategoryResponseV2> {

    private final RankingCategoryService rankingCategoryService;

    @Override
    protected Page<RankingCategoryResponseV2> findAll(RankingCategoryRequestV2 input, Pageable pageable) {
        return rankingCategoryService.getList(input.toEntity(null), pageable)
                .map(RankingCategoryResponseV2::fromEntity);
    }

    @Override
    protected RankingCategoryResponseV2 findById(Long id) {
        return rankingCategoryService.getOne(
                        RankingCategory.builder()
                                .categoryNo(id)
                                .build()
                )
                .map(RankingCategoryResponseV2::fromEntity)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    @Override
    protected List<RankingCategoryResponseV2> findByIds(List<Long> ids) {
        return rankingCategoryService.getByIds(ids)
                .stream()
                .map(RankingCategoryResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected RankingCategoryResponseV2 create(RankingCategoryRequestV2 input) {
        RankingCategory entity = rankingCategoryService.regist(input.toEntity(null));
        return RankingCategoryResponseV2.fromEntity(entity);
    }

    @Override
    protected RankingCategoryResponseV2 update(Long id, RankingCategoryRequestV2 input) {
        RankingCategory entity = rankingCategoryService.update(input.toEntity(id));
        return RankingCategoryResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        rankingCategoryService.deleteById(id);
    }

}
