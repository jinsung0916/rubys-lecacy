package com.benope.apple.api.food.event;

import com.benope.apple.api.food.service.FoodService;
import com.benope.apple.api.food.service.TagHelperService;
import com.benope.apple.api.food.service.TagService;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedDetail;
import com.benope.apple.domain.feed.bean.UploadImgFoodRelation;
import com.benope.apple.domain.feed.event.FeedDeleteEvent;
import com.benope.apple.domain.feed.event.FeedRegistEvent;
import com.benope.apple.domain.feed.event.FeedUpdateEvent;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class FoodFeedEventListener {

    private final FoodService foodService;
    private final TagService tagService;
    private final TagHelperService tagHelperService;

    @EventListener
    @Transactional
    public void handleEvent(FeedRegistEvent e) {
        Feed feed = e.getSource();

        tag(feed);
        countTag(feed);
    }

    @EventListener
    @Transactional
    public void handleEvent(FeedUpdateEvent e) {
        Feed feed = e.getSource();

        unTag(feed);
        tag(feed);
        countTag(feed);
    }

    @EventListener
    @Transactional
    public void handleEvent(FeedDeleteEvent e) {
        Feed feed = e.getSource();

        unTag(feed);
        countTag(feed);
    }

    private void tag(Feed feed) {
        for (UploadImgFoodRelation relation : toRelations(feed)) {
            tagHelperService.tag(relation.getFoodNo(), feed.getMbNo(), feed.getFeedNo());
        }
    }

    private List<UploadImgFoodRelation> toRelations(Feed feed) {
        return DomainObjectUtil.nullSafeStream(feed.getFeedDetails())
                .map(FeedDetail::getUploadImgFoodRelations)
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    private void unTag(Feed feed) {
        tagHelperService.unTag(feed.getMbNo(), feed.getFeedNo());
    }

    private void countTag(Feed feed) {
        for (UploadImgFoodRelation relation : toRelations(feed)) {
            doCountTag(relation.getFoodNo());
        }
    }

    private void doCountTag(Long foodNo) {
        Optional<Food> optional = foodService.getById(foodNo, false);

        if (optional.isPresent()) {
            foodService.update(
                    Food.builder()
                            .foodNo(foodNo)
                            .tagCount(
                                    tagService.countDistinct(
                                            FoodTag.builder()
                                                    .foodNo(foodNo)
                                                    .build()
                                    )
                            )
                            .build()
            );
        }
    }

}
