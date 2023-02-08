package com.benope.apple.admin.category.serviceImpl;

import com.benope.apple.admin.category.service.RankingCategoryService;
import com.benope.apple.admin.food.service.FoodService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.category.bean.Category;
import com.benope.apple.domain.category.bean.RankingCategory;
import com.benope.apple.domain.category.repository.RankingCategoryJpaRepository;
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
public class RankingCategoryServiceImpl implements RankingCategoryService {

    private final RankingCategoryJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ModelMapper modelMapper;

    private final FoodService foodService;

    @Transactional(readOnly = true)
    @Override
    public Page<RankingCategory> getList(RankingCategory category, Pageable pageable) {
        Example<RankingCategory> example = Example.of(
                category,
                ExampleMatcher.matchingAll().withIgnoreNullValues()
                        .withMatcher("categoryNm", ExampleMatcher.GenericPropertyMatcher::contains)
        );
        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RankingCategory> getOne(RankingCategory category) {
        Example<RankingCategory> example = Example.of(category, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RankingCategory> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public RankingCategory regist(RankingCategory category) {
        RankingCategory entity = repository.save(category);
        return em.flushAndRefresh(entity);
    }

    @Override
    public RankingCategory update(RankingCategory category) {
        RankingCategory entity = repository.findById(category.getCategoryNo())
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
        RankingCategory entity = repository.findById(categoryNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
    }

}
