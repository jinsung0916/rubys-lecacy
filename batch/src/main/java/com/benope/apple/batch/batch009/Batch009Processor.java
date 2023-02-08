package com.benope.apple.batch.batch009;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSpecification;
import com.benope.apple.domain.feed.repository.FeedJpaRepository;
import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.bean.ThemeCondition;
import com.benope.apple.domain.theme.bean.ThemeConditionCd;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import com.benope.apple.domain.theme.repository.ThemeFeedJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch009Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch009Processor implements ItemProcessor<Theme, Theme> {

    private final ThemeFeedJpaRepository themeFeedJpaRepository;
    private final FeedJpaRepository feedJpaRepository;

    @Override
    public Theme process(Theme item) throws Exception {
        if (!item.batchCollected()) {
            return null;
        }

        // 1. 등록된 피드를 모두 삭제한다.
        themeFeedJpaRepository.deleteByThemeNo(item.getThemeNo());

        // 2. 자동 생성 조건에 따라 피드를 수집한다.
        for (ThemeCondition condition : item.getThemeConditions()) {
            iterateThemeConditions(item, condition);
        }

        return null;
    }

    private void iterateThemeConditions(Theme item, ThemeCondition condition) {
        if (ThemeConditionCd.EXPERT.equals(condition.getThemeConditionCd())) {
            handleExpertCondition(item, condition);
        }
    }

    private void handleExpertCondition(Theme item, ThemeCondition condition) {
        List<Feed> feeds = getExpertFeed(condition);

        for (int i = 0; i < feeds.size(); i++) {
            ThemeFeed themeFeed = ThemeFeed.builder()
                    .themeNo(item.getThemeNo())
                    .feedNo(feeds.get(i).getFeedNo())
                    .reorderNo((long) i)
                    .build();

            themeFeedJpaRepository.save(themeFeed);
        }
    }

    private List<Feed> getExpertFeed(ThemeCondition condition) {
        return feedJpaRepository.findAll(
                        FeedSpecification.toSpecExpertCd(condition.getExpertCd()),
                        PageRequest.ofSize(condition.getBatchSize()).withSort(Sort.by(Sort.Order.desc("feedNo")))
                )
                .toList();
    }

}
