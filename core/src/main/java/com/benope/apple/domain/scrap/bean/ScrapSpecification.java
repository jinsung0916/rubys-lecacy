package com.benope.apple.domain.scrap.bean;

import com.benope.apple.domain.feed.bean.Feed;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScrapSpecification {

    public static Specification<Scrap> toSpec(Scrap scrap) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(scrap.getScrapNo())) {
                predicates.add(cb.equal(root.get("scrapNo"), scrap.getScrapNo()));
            }

            if (Objects.nonNull(scrap.getScrapTypeCd())) {
                predicates.add(cb.equal(root.get("scrapTypeCd"), scrap.getScrapTypeCd()));
            }

            if (Objects.nonNull(scrap.getParentDirectoryNo())) {
                predicates.add(cb.equal(root.get("parentDirectoryNo"), scrap.getParentDirectoryNo()));
            }

            if (Objects.nonNull(scrap.getMbNo())) {
                predicates.add(cb.equal(root.get("mbNo"), scrap.getMbNo()));
            }

            if (Objects.nonNull(scrap.getFeedNo())) {
                predicates.add(cb.equal(root.get("feedNo"), scrap.getFeedNo()));
            }

            if (Objects.nonNull(scrap.getDirectoryName())) {
                predicates.add(cb.equal(root.get("directoryName"), scrap.getDirectoryName()));
            }

            final Predicate[] predicateArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicateArray));
        };
    }

    public static Specification<Scrap> toSpecFeedExists() {
        return (root, query, cb) -> {
            Subquery<Feed> feedSubQuery = query.subquery(Feed.class);
            Root<Feed> feed = feedSubQuery.from(Feed.class);

            return cb.or(
                    cb.equal(root.get("scrapTypeCd"), ScrapTypeCd.DIRECTORY),
                    cb.and(
                            cb.equal(root.get("scrapTypeCd"), ScrapTypeCd.SCRAP),
                            cb.exists(
                                    feedSubQuery.select(feed).where(
                                            cb.equal(feed.get("feedNo"), root.get("feedNo"))
                                    )
                            )
                    )
            );
        };
    }

}
