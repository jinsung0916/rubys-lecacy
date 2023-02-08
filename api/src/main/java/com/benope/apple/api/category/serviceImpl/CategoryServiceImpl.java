package com.benope.apple.api.category.serviceImpl;

import com.benope.apple.api.category.service.CategoryService;
import com.benope.apple.domain.category.bean.Category;
import com.benope.apple.domain.category.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<Category> getList(Category category, Pageable pageable) {
        Example<Category> example = Example.of(category, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

}
