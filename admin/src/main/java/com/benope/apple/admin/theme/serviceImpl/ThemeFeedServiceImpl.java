package com.benope.apple.admin.theme.serviceImpl;

import com.benope.apple.admin.theme.service.ThemeFeedService;
import com.benope.apple.admin.theme.service.ThemeService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import com.benope.apple.domain.theme.bean.ThemeFeedSpecification;
import com.benope.apple.domain.theme.repository.ThemeFeedJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeFeedServiceImpl implements ThemeFeedService {

    private final ThemeFeedJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ThemeService themeService;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<ThemeFeed> getList(ThemeFeed themeFeed, Pageable pageable) {
        Specification<ThemeFeed> spec = ThemeFeedSpecification.toSpec(themeFeed)
                .and(ThemeFeedSpecification.toSpecFeedExists());

        return repository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ThemeFeed> getOne(ThemeFeed themeFeed) {
        Specification<ThemeFeed> spec = ThemeFeedSpecification.toSpec(themeFeed)
                .and(ThemeFeedSpecification.toSpecFeedExists());

        return repository.findOne(spec);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ThemeFeed> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public ThemeFeed regist(ThemeFeed themeFeed) {
        if (isAlreadyRegistered(themeFeed)) {
            throw new BusinessException(RstCode.FEED_ALREADY_REGISTERED);
        }

        ThemeFeed entity = repository.save(themeFeed);
        return em.flushAndRefresh(entity);
    }

    private boolean isAlreadyRegistered(ThemeFeed themeFeed) {
        return Objects.nonNull(themeFeed.getFeedNo())
                && themeService.getByFeedNo(themeFeed.getFeedNo()).isPresent();
    }

    @Override
    public ThemeFeed update(ThemeFeed themeFeed) {
        if (isAlreadyRegistered(themeFeed)) {
            throw new BusinessException(RstCode.FEED_ALREADY_REGISTERED);
        }

        ThemeFeed entity = repository.findById(themeFeed.getThemeFeedNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(themeFeed, entity);
        return em.flushAndRefresh(entity);
    }

    @Override
    public void deleteById(Long themeFeedNo) {
        ThemeFeed entity = repository.findById(themeFeedNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
    }

}
