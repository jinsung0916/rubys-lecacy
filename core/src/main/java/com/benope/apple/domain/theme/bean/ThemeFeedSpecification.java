package com.benope.apple.domain.theme.bean;

import com.benope.apple.domain.feed.bean.Feed;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThemeFeedSpecification {

    public static Specification<ThemeFeed> toSpec(ThemeFeed themeFeed) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(themeFeed.getThemeFeedNo())) {
                predicates.add(cb.equal(root.get("themeFeedNo"), themeFeed.getThemeFeedNo()));
            }

            if (Objects.nonNull(themeFeed.getThemeNo())) {
                predicates.add(cb.equal(root.get("themeNo"), themeFeed.getThemeNo()));
            }

            if (Objects.nonNull(themeFeed.getFeedNo())) {
                predicates.add(cb.equal(root.get("feedNo"), themeFeed.getFeedNo()));
            }

            if (Objects.nonNull(themeFeed.getReorderNo())) {
                predicates.add(cb.equal(root.get("reorderNo"), themeFeed.getReorderNo()));
            }

            final Predicate[] predicateArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicateArray));
        };
    }

    public static Specification<ThemeFeed> toSpecFeedExists() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Subquery<Feed> feedSubQuery = query.subquery(Feed.class);
            Root<Feed> feed = feedSubQuery.from(Feed.class);

            predicates.add(
                    cb.exists(
                            feedSubQuery.select(feed).where(
                                    cb.equal(feed.get("feedNo"), root.get("feedNo"))
                            )
                    )
            );

            final Predicate[] predicateArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicateArray));
        };
    }

}
