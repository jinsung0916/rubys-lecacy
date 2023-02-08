package com.benope.apple.admin.food.serviceImpl;

import com.benope.apple.admin.food.service.FoodService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.repository.FoodJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodJpaRepository repository;

    private final EntityManagerWrapper em;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Food> getList(Food food, Pageable pageable) {
        Example<Food> example = Example.of(
                food,
                ExampleMatcher.matchingAll().withIgnoreNullValues()
                        .withMatcher("foodNm", ExampleMatcher.GenericPropertyMatcher::contains)
                        .withMatcher("brand", ExampleMatcher.GenericPropertyMatcher::contains)
                        .withMatcher("manufacturer", ExampleMatcher.GenericPropertyMatcher::contains)
                        .withMatcher("storageType", ExampleMatcher.GenericPropertyMatcher::contains)
                        .withMatcher("writer", ExampleMatcher.GenericPropertyMatcher::contains)
        );

        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Food> getOne(Food food) {
        Example<Food> example = Example.of(food, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Food> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public long count(Food food) {
        Example<Food> example = Example.of(food, ExampleMatcher.matchingAll().withIgnoreNullValues());

        return repository.count(example);
    }

    @Override
    public Food regist(Food food) {
        return em.flushAndRefresh(repository.save(food));
    }

    @Override
    public Food update(Food food) {
        Food entity = repository.findById(food.getFoodNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        map(food, entity);
        repository.saveAndFlush(entity);

        return em.flushAndRefresh(entity);
    }

    private void map(Food source, Food destination) {
        modelMapper.map(source, destination);

        if (Objects.isNull(source.getFrontImageNo())) {
            destination.setFrontImageNo(null);
        }
    }

    @Override
    public void deleteById(Long foodNo) {
        Food entity = repository.findById(foodNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
    }

}
