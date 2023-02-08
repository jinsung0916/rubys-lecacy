package com.benope.apple.api.food.serviceImpl;

import com.benope.apple.api.food.service.TagHelperService;
import com.benope.apple.api.food.service.TagService;
import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.domain.food.bean.FoodTagTargetCd;
import com.benope.apple.domain.food.repository.FoodTagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedTagHelperService implements TagHelperService {

    private final TagService tagService;

    private final FoodTagJpaRepository repository;

    @Override
    public void tag(Long foodNo, Long mbNo, Long objectNo) {
        FoodTag entity = FoodTag.builder()
                .foodNo(foodNo)
                .mbNo(mbNo)
                .foodTagTargetCd(FoodTagTargetCd.FEED)
                .objectNo(objectNo)
                .build();

        tagService.regist(entity);
    }

    @Override
    public void unTag(Long mbNo, Long objectNo) {
        repository.deleteByMbNoAndFoodTagTargetCdAndObjectNo(mbNo, FoodTagTargetCd.FEED, objectNo);
    }


}
