package com.benope.apple.admin.category.serviceImpl;

import com.benope.apple.admin.category.service.FoodCategoryService;
import com.benope.apple.admin.food.service.FoodService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.category.bean.Category;
import com.benope.apple.domain.category.bean.CategoryTypeCd;
import com.benope.apple.domain.category.bean.FoodCategory;
import com.benope.apple.domain.category.repository.FoodCategoryJpaRepository;
import com.benope.apple.domain.food.bean.Food;
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
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ModelMapper modelMapper;

    private final FoodService foodService;

    @Transactional(readOnly = true)
    @Override
    public Page<FoodCategory> getList(FoodCategory category, Pageable pageable) {
        Example<FoodCategory> example = Example.of(
                category,
                ExampleMatcher.matchingAll().withIgnoreNullValues()
                        .withMatcher("categoryNm", ExampleMatcher.GenericPropertyMatcher::contains)
        );
        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FoodCategory> getOne(FoodCategory category) {
        Example<FoodCategory> example = Example.of(category, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FoodCategory> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public FoodCategory regist(FoodCategory category) {
        FoodCategory entity = repository.save(category);
        return em.flushAndRefresh(entity);
    }

    @Override
    public FoodCategory update(FoodCategory category) {
        FoodCategory entity = repository.findById(category.getCategoryNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        map(category, entity);

        return em.flushAndRefresh(entity);
    }

    private void map(Category source, Category destination) {
        modelMapper.map(source, destination);

        if (Objects.isNull(source.getIconImgNo())) {
            destination.setIconImgNo(null);
        }
    }

    @Override
    public void deleteById(Long categoryNo) {
        FoodCategory entity = repository.findById(categoryNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        if (isChildExists(entity)) {
            throw new BusinessException(RstCode.CHILD_CATEGORY_EXISTS);
        }

        if (isFoodExists(entity)) {
            throw new BusinessException(RstCode.FOOD_EXISTS);
        }

        repository.delete(entity);
    }

    private boolean isChildExists(Category category) {
        FoodCategory temp = FoodCategory.builder().parentCategoryNo(category.getCategoryNo()).build();
        Example<FoodCategory> example = Example.of(temp, ExampleMatcher.matchingAll().withIgnoreNullValues());

        return repository.count(example) > 0;
    }

    private boolean isFoodExists(Category category) {
        if (!CategoryTypeCd.FOOD.equals(category.getCategoryTypeCd())) {
            return false;
        }

        Food cond = Food.builder()
                .foodCategoryNo(category.getCategoryNo())
                .build();

        return foodService.count(cond) > 0;
    }

}
