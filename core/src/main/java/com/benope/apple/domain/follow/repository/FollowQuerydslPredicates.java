package com.benope.apple.domain.follow.repository;

import com.benope.apple.domain.follow.bean.Follow;
import com.benope.apple.domain.follow.bean.QFollow;
import com.benope.apple.domain.member.bean.QMember;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

import java.time.LocalDate;
import java.util.Objects;


public class FollowQuerydslPredicates {

    private static final QFollow qFollow = QFollow.follow;
    private static final QMember qMember = QMember.member;

    public static Predicate eq(Follow follow) {
        return ExpressionUtils.allOf(
                eqFollowNo(follow.getFollowNo()),
                eqMbNo(follow.getMbNo()),
                eqFollowMbNo(follow.getFollowMbNo()),
                eqFollowDate(follow.getFollowDate())
        );
    }

    private static Predicate eqFollowNo(Long followNo) {
        return Objects.nonNull(followNo) ? qFollow.followNo.eq(followNo) : null;
    }

    private static Predicate eqMbNo(Long mbNo) {
        return Objects.nonNull(mbNo) ? qFollow.mbNo.eq(mbNo) : null;
    }

    private static Predicate eqFollowMbNo(Long followMbNo) {
        return Objects.nonNull(followMbNo) ? qFollow.followMbNo.eq(followMbNo) : null;
    }

    private static Predicate eqFollowDate(LocalDate followDate) {
        return Objects.nonNull(followDate) ? qFollow.followDate.eq(followDate) : null;
    }

    public static Predicate followerExists() {
        return JPAExpressions.select().from(qMember).where(qMember.mbNo.eq(qFollow.followMbNo)).exists();
    }

    public static Predicate followeeExists() {
        return JPAExpressions.select().from(qMember).where(qMember.mbNo.eq(qFollow.mbNo)).exists();
    }

}
