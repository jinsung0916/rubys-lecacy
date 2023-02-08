package com.benope.apple.domain.feed.bean;

import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.member.bean.Member;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedSpecification {

    private static final float DEFAULT_BOOST = 100;

    // JPA

    public static Specification<Feed> toSpec(Feed feed) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(feed.getFeedNo())) {
                predicates.add(cb.equal(root.get("feedNo"), feed.getFeedNo()));
            }

            if (Objects.nonNull(feed.getMbNo())) {
                predicates.add(cb.equal(root.get("mbNo"), feed.getMbNo()));
            }

            if (Objects.nonNull(feed.getFeedTypeCd())) {
                predicates.add(cb.equal(root.get("feedTypeCd"), feed.getFeedTypeCd()));
            }

            if (Objects.nonNull(feed.getFeedTitle())) {
                predicates.add(cb.like(root.get("feedTitle"), "%" + feed.getFeedTitle() + "%"));
            }

            if (Objects.nonNull(feed.getRpstImgNo())) {
                predicates.add(cb.equal(root.get("rpstImgNo"), feed.getRpstImgNo()));
            }

            // ...

            final Predicate[] predicateArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicateArray));
        };
    }

    public static Specification<Feed> toSpecCreatedAtBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) ->
                cb.between(root.get("createdAt"), startDate.atTime(LocalTime.MIN), endDate.atTime(LocalTime.MAX));
    }

    public static Specification<Feed> toSpecExpertCd(ExpertCd expertCd) {
        return (root, query, cb) -> {
            Subquery<Member> memberSubQuery = query.subquery(Member.class);
            Root<Member> member = memberSubQuery.from(Member.class);

            return cb.exists(
                    memberSubQuery.select(member).where(
                            cb.equal(member.get("mbNo"), root.get("mbNo")),
                            cb.equal(member.get("expertCd"), expertCd)
                    )
            );
        };
    }

    // Solr

    public static Criteria toCriteria(FeedSolrEntity entity) {
        Criteria criteria = new Criteria();

        if (Objects.nonNull(entity.getId())) {
            criteria = criteria.and("id").is(entity.getId());
        }

        if (Objects.nonNull(entity.getFeedTypeCd())) {
            criteria = criteria.and("feedTypeCd").is(entity.getFeedTypeCd());
        }

        if (!CollectionUtils.isEmpty(entity.getText())) {
            criteria = criteria
                    .and(
                            new Criteria("text").is(entity.getText())
                                    .or("textStr").contains(entity.getText()).boost(DEFAULT_BOOST)
                    );
        }

        if (!CollectionUtils.isEmpty(entity.getTaggedFoods())) {
            criteria = criteria.and("taggedFoods").is(entity.getTaggedFoods());
        }

        if (Objects.nonNull(entity.getMbNo())) {
            criteria = criteria.and("mbNo").is(entity.getMbNo());
        }

        return criteria;
    }

    public static Criteria toCriteriaCreatedAtBetween(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = new Criteria();

        LocalDateTime startDateTime = Objects.requireNonNullElse(startDate, LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDateTime = Objects.requireNonNullElse(endDate, LocalDate.MAX).atTime(LocalTime.MAX);

        String s = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(startDateTime) + "Z";
        String e = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(endDateTime) + "Z";

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            criteria = criteria.and("createdAt").between(s, e);
        } else if (Objects.nonNull(startDate)) {
            criteria = criteria.and("createdAt").greaterThanEqual(s);
        } else if (Objects.nonNull(endDate)) {
            criteria = criteria.and("createdAt").lessThanEqual(e);
        }

        return criteria;
    }

}
