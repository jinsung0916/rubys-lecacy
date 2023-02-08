package com.benope.apple.api.theme.serviceImpl;

import com.benope.apple.api.theme.service.ThemeService;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.repository.ThemeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<Theme> getList(Theme theme, Pageable pageable) {
        Example<Theme> example = Example.of(theme, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

    @Override
    public Optional<Theme> getOne(Theme theme) {
        Example<Theme> example = Example.of(theme, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

}
