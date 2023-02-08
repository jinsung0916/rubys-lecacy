package com.benope.apple.api.food.serviceImpl;

import com.benope.apple.api.food.service.FoodService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.event.FoodViewEvent;
import com.benope.apple.domain.food.repository.FoodJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodJpaRepository repository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<Food> getById(Long foodNo, boolean isViewCounted) {
        Optional<Food> optional = repository.findById(foodNo);

        if (isViewCounted && optional.isPresent()) {
            applicationEventPublisher.publishEvent(new FoodViewEvent(optional.get()));
        }

        return optional;
    }

    @Override
    public Food update(Food food) {
        Food entity = repository.findById(food.getFoodNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(food, entity);
        repository.saveAndFlush(entity);

        return entity;
    }

}
