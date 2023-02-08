package com.benope.apple.admin.feed.controller;

import com.benope.apple.admin.feed.bean.FeedRequestV2;
import com.benope.apple.admin.feed.bean.FeedResponseV2;
import com.benope.apple.admin.feed.service.FeedSearchService;
import com.benope.apple.admin.feed.service.FeedService;
import com.benope.apple.admin.theme.service.ThemeService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.theme.bean.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/feed", headers = V2_HEADER)
@RequiredArgsConstructor
public class FeedControllerV2 extends AbstractRestController<FeedRequestV2, FeedResponseV2> {

    private final FeedSearchService feedSearchService;
    private final FeedService feedService;
    private final ThemeService themeService;

    @Override
    protected Page<FeedResponseV2> findAll(FeedRequestV2 input, Pageable pageable) {
        return feedSearchService.search(input.toSolrEntity(), input.getStartDate(), input.getEndDate(), pageable)
                .map(FeedResponseV2::fromEntity);
    }

    @Override
    protected FeedResponseV2 findById(Long id) {
        Feed entity = feedService.getOne(
                        Feed.builder()
                                .feedNo(id)
                                .build()
                )
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        return FeedResponseV2.fromEntityToDetail(entity)
                .setThemeNo(getThemeNo(entity));
    }

    private Long getThemeNo(Feed feed) {
        return themeService.getByFeedNo(feed.getFeedNo())
                .map(Theme::getThemeNo)
                .orElse(null);
    }

    @Override
    protected List<FeedResponseV2> findByIds(List<Long> ids) {
        return feedService.getByIds(ids)
                .stream()
                .map(FeedResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected FeedResponseV2 create(FeedRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected FeedResponseV2 update(Long id, FeedRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

}
