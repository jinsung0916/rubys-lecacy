package com.benope.apple.domain.comment.repository;

import com.benope.apple.config.domain.RowStatCd;
import com.benope.apple.domain.comment.bean.*;
import com.benope.apple.domain.score.bean.QScore;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommentScoreViewJpaRepositorySupport extends QuerydslRepositorySupport {

    private static final QComment qComment = QComment.comment;
    private static final QScore qScore = QScore.score;

    public CommentScoreViewJpaRepositorySupport(EntityManager em) {
        super(Comment.class);
        setEntityManager(em);
    }

    public Page<CommentScoreView> findAll(Predicate predicate, Pageable pageable) {
        JPQLQuery<CommentScoreView> query = query(predicate);

        long totalCount = query.fetchCount();
        List<CommentScoreView> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    public CommentScoreView findOne(Predicate predicate) {
        return query(predicate).fetchOne();
    }

    private JPQLQuery<CommentScoreView> query(Predicate predicate) {
        return getQuerydsl().createQuery()
                .select(new QCommentScoreView(qComment, qScore))
                .from(qComment)
                .leftJoin(qScore).on(ExpressionUtils.allOf(
                        qScore.foodNo.eq(qComment.objectNo),
                        qScore.mbNo.eq(qComment.mbNo),
                        qScore.rowStatCd.ne(RowStatCd.D)
                ))
                .where(ExpressionUtils.allOf(
                        predicate,
                        qComment.commentTypeCd.eq(CommentTypeCd.FOOD),
                        CommentQuerydslPredicates.foodExists()
                ));
    }

}
