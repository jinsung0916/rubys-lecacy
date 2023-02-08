package com.benope.apple.api.theme.serviceImpl;

import com.benope.apple.api.feed.service.FeedService;
import com.benope.apple.api.theme.service.ThemeFeedService;
import com.benope.apple.api.theme.service.ThemeService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import com.benope.apple.domain.theme.bean.ThemeFeedSpecification;
import com.benope.apple.domain.theme.repository.ThemeFeedJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeFeedServiceImpl implements ThemeFeedService {

    private final ThemeFeedJpaRepository repository;

    private final ThemeService themeService;
    private final FeedService feedService;

    @Transactional(readOnly = true)
    @Override
    public Page<ThemeFeed> getList(ThemeFeed themeFeed, Pageable pageable) {
        Theme theme = getTheme(themeFeed);

        if (theme.displayAll()) {
            return displayAll(pageable);
        } else {
            return doGetList(themeFeed, pageable);
        }
    }

    private Theme getTheme(ThemeFeed themeFeed) {
        return themeService.getOne(
                        Theme.builder()
                                .themeNo(themeFeed.getThemeNo())
                                .build()
                )
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    private Page<ThemeFeed> displayAll(Pageable pageable) {
        Feed cond = Feed.builder()
                .build();

        PageRequest converted = PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("feedNo"))
        );

        return feedService.getList(cond, converted)
                .map(this::toThemeFeed);
    }

    private ThemeFeed toThemeFeed(Feed feed) {
        return ThemeFeed.builder()
                .feed(feed)
                .build();
    }

    private Page<ThemeFeed> doGetList(ThemeFeed themeFeed, Pageable pageable) {
        Specification<ThemeFeed> spec = ThemeFeedSpecification.toSpec(themeFeed)
                .and(ThemeFeedSpecification.toSpecFeedExists());

        return repository.findAll(spec, pageable);
    }

}
