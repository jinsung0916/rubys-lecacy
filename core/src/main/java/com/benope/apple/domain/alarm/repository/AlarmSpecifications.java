package com.benope.apple.domain.alarm.repository;

import com.benope.apple.domain.alarm.bean.Alarm;
import com.benope.apple.domain.alarm.bean.AlarmTargetCd;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.member.bean.Member;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlarmSpecifications {

    public static Specification<Alarm> toSpec(Alarm alarm) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(alarm.getAlarmNo())) {
                predicates.add(cb.equal(root.get("alarmNo"), alarm.getAlarmNo()));
            }

            if (Objects.nonNull(alarm.getAlarmTypeCd())) {
                predicates.add(cb.equal(root.get("alarmTypeCd"), alarm.getAlarmTypeCd()));
            }

            if (Objects.nonNull(alarm.getAlarmTargetCd())) {
                predicates.add(cb.equal(root.get("alarmTargetCd"), alarm.getAlarmTargetCd()));
            }

            if (Objects.nonNull(alarm.getFromMbNo())) {
                predicates.add(cb.equal(root.get("fromMbNo"), alarm.getFromMember()));
            }

            if (Objects.nonNull(alarm.getToMbNo())) {
                predicates.add(cb.equal(root.get("toMbNo"), alarm.getToMbNo()));
            }

            if (Objects.nonNull(alarm.getObjectNo())) {
                predicates.add(cb.equal(root.get("objectNo"), alarm.getObjectNo()));
            }

            if (Objects.nonNull(alarm.getReadYn())) {
                predicates.add(cb.equal(root.get("readYn"), alarm.getReadYn()));
            }

            if (Objects.nonNull(alarm.getPushYn())) {
                predicates.add(cb.equal(root.get("pushYn"), alarm.getPushYn()));
            }

            final Predicate[] predicateArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicateArray));
        };
    }

    public static Specification<Alarm> toSpecRelatedEntityExists() {
        return (root, query, cb) -> {
            Subquery<Feed> feedSubQuery = query.subquery(Feed.class);
            Root<Feed> feed = feedSubQuery.from(Feed.class);

            Subquery<Member> memberSubQuery = query.subquery(Member.class);
            Root<Member> member = memberSubQuery.from(Member.class);

            return cb.or(
                    cb.and(
                            cb.equal(root.get("alarmTargetCd"), AlarmTargetCd.FEED),
                            cb.exists(
                                    feedSubQuery.select(feed).where(
                                            cb.equal(feed.get("feedNo"), root.get("objectNo"))
                                    )
                            )
                    ),
                    cb.and(
                            cb.equal(root.get("alarmTargetCd"), AlarmTargetCd.MEMBER),
                            cb.exists(
                                    memberSubQuery.select(member).where(
                                            cb.equal(member.get("mbNo"), root.get("objectNo"))
                                    )
                            )
                    )
            );
        };
    }

}
