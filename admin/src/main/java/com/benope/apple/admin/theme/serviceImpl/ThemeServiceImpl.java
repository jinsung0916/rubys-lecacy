package com.benope.apple.admin.theme.serviceImpl;

import com.benope.apple.admin.theme.service.ThemeService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.repository.ThemeJpaRepository;
import com.benope.apple.domain.theme.repository.ThemeQuerydslPredicates;
import com.google.common.collect.Streams;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeJpaRepository repository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Theme> getList(Theme theme, Pageable pageable) {
        Example<Theme> example = Example.of(
                theme,
                ExampleMatcher.matchingAll().withIgnoreNullValues().withMatcher("themeNm", ExampleMatcher.GenericPropertyMatcher::contains)
        );
        return repository.findAll(example, pageable);
    }

    @Override
    public Optional<Theme> getOne(Theme theme) {
        Example<Theme> example = Example.of(theme, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Theme> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Theme> getByFeedNo(Long feedNo) {
        Predicate predicate = ThemeQuerydslPredicates.feedExists(feedNo);

        return Streams.stream(repository.findAll(predicate))
                .filter(it -> !it.batchCollected())
                .findAny();
    }

    @Override
    public Theme regist(Theme theme) {
        return repository.save(theme);
    }

    @Override
    public Theme update(Theme theme) {
        Theme entity = repository.findById(theme.getThemeNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(theme, entity);
        return entity;
    }

    @Override
    public void deleteById(Long themeNo) {
        Theme entity = repository.findById(themeNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
    }

}
