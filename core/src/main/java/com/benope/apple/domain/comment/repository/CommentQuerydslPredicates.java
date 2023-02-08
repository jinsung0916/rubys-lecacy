package com.benope.apple.domain.comment.repository;

import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.CommentTypeCd;
import com.benope.apple.domain.comment.bean.QComment;
import com.benope.apple.domain.food.bean.QFood;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class CommentQuerydslPredicates {

    private static final QComment qComment = QComment.comment;
    private static final QFood qFood = QFood.food;

    public static Predicate eq(Comment comment) {
        return ExpressionUtils.allOf(
                eqCommentNo(comment.getCommentNo()),
                eqCommentTypeCd(comment.getCommentTypeCd()),
                eqMbNo(comment.getMbNo()),
                eqObjectNo(comment.getObjectNo())
        );
    }

    private static Predicate eqCommentNo(Long commentNo) {
        return Objects.nonNull(commentNo) ? qComment.commentNo.eq(commentNo) : null;
    }

    private static Predicate eqCommentTypeCd(CommentTypeCd commentTypeCd) {
        return qComment.commentTypeCd.eq(commentTypeCd);
    }

    private static Predicate eqMbNo(Long mbNo) {
        return Objects.nonNull(mbNo) ? qComment.mbNo.eq(mbNo) : null;
    }

    private static Predicate eqObjectNo(Long objectNo) {
        return Objects.nonNull(objectNo) ? qComment.objectNo.eq(objectNo) : null;
    }

    public static Predicate createdAtBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = Objects.requireNonNullElse(startDate, LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDateTime = Objects.requireNonNullElse(endDate, LocalDate.MAX).atTime(LocalTime.MAX);

        return qComment.createdAt.between(startDateTime, endDateTime);
    }

    public static Predicate foodExists() {
        return JPAExpressions.select()
                .from(qFood)
                .where(ExpressionUtils.allOf(
                        qComment.commentTypeCd.eq(CommentTypeCd.FOOD),
                        qFood.foodNo.eq(qComment.objectNo)
                ))
                .exists();
    }

}
