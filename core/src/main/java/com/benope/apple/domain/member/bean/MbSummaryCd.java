package com.benope.apple.domain.member.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import com.benope.apple.domain.comment.bean.CommentTypeCd;
import com.benope.apple.domain.comment.bean.QComment;
import com.benope.apple.domain.comment.repository.CommentQuerydslPredicates;
import com.benope.apple.domain.feed.bean.QFeed;
import com.benope.apple.domain.follow.bean.QFollow;
import com.benope.apple.domain.follow.repository.FollowQuerydslPredicates;
import com.benope.apple.domain.food.bean.QFoodTag;
import com.benope.apple.domain.food.repository.FoodTagQuerydslPredicates;
import com.benope.apple.domain.score.bean.QScore;
import com.benope.apple.domain.score.repository.ScoreQuerydslPredicates;
import com.benope.apple.domain.scrap.bean.QScrap;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import com.benope.apple.utils.BeanUtil;
import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public enum MbSummaryCd implements EnumDescribed {

    FOLLOWER {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qFollow.count()))
                    .from(qFollow)
                    .where(ExpressionUtils.allOf(
                            qFollow.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate),
                            FollowQuerydslPredicates.followerExists()
                    ));
        }
    },
    FOLLOWING {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qFollow.count()))
                    .from(qFollow)
                    .where(ExpressionUtils.allOf(
                            qFollow.followMbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate),
                            FollowQuerydslPredicates.followeeExists()
                    ));
        }
    },
    POST {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qFeed.count()))
                    .from(qFeed)
                    .where(ExpressionUtils.allOf(
                            qFeed.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate)
                    ));
        }
    },
    SCRAP {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qScrap.count()))
                    .from(qScrap)
                    .where(ExpressionUtils.allOf(
                            qScrap.scrapTypeCd.eq(ScrapTypeCd.SCRAP),
                            qScrap.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate)
                    ));
        }
    },
    SCORE {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qScore.count()))
                    .from(qScore)
                    .where(ExpressionUtils.allOf(
                            qScore.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate),
                            ScoreQuerydslPredicates.foodExists()
                    ));
        }
    },
    COMMENT {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qComment.count()))
                    .from(qComment)
                    .where(ExpressionUtils.allOf(
                            qComment.commentTypeCd.ne(CommentTypeCd.FOOD),
                            qComment.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate)
                    ));
        }
    },
    FOOD_COMMENT {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qComment.count()))
                    .from(qComment)
                    .where(ExpressionUtils.allOf(
                            qComment.commentTypeCd.eq(CommentTypeCd.FOOD),
                            qComment.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate),
                            CommentQuerydslPredicates.foodExists()
                    ));
        }
    },
    TAG {
        @Override
        public JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate) {
            return new JPAQuery<>(BeanUtil.getBean(EntityManager.class))
                    .select(new QMemberSummary(Expressions.asSimple(mbNo), Expressions.asSimple(this), qFoodTag.count()))
                    .from(qFoodTag)
                    .where(ExpressionUtils.allOf(
                            qFoodTag.mbNo.eq(mbNo),
                            createdAtBetween(startDate, endDate),
                            FoodTagQuerydslPredicates.foodExists()
                    ));
        }
    };

    private static final QFollow qFollow = QFollow.follow;
    private static final QMember qMember = QMember.member;
    private static final QFeed qFeed = QFeed.feed;
    private static final QScrap qScrap = QScrap.scrap;
    private static final QScore qScore = QScore.score;
    private static final QComment qComment = QComment.comment;
    private static final QFoodTag qFoodTag = QFoodTag.foodTag;

    public abstract JPQLQuery<MemberSummary> query(Long mbNo, LocalDate startDate, LocalDate endDate);

    private static Predicate createdAtBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = Objects.nonNull(startDate) ? startDate.atTime(LocalTime.MIN) : null;
        LocalDateTime endDateTime = Objects.nonNull(endDate) ? endDate.atTime(LocalTime.MAX) : null;

        List<Predicate> predicates = Lists.newArrayList();

        if (Objects.nonNull(startDateTime)) {
            Expressions.booleanTemplate("createdAt >= {0}", startDateTime);
        }
        if (Objects.nonNull(endDateTime)) {
            Expressions.booleanTemplate("createdAt <= {0}", endDateTime);
        }

        return ExpressionUtils.allOf(predicates);
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDesc() {
        return null;
    }

}
