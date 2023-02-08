package com.benope.apple.domain.comment.repository;

import com.benope.apple.domain.comment.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment>, QuerydslPredicateExecutor<Comment> {

    @Modifying
    @Query("UPDATE Comment SET subCommentCnt = subCommentCnt + 1 WHERE commentNo = :commentNo")
    void plusSubCommentCnt(Long commentNo);

    @Modifying
    @Query("UPDATE Comment SET subCommentCnt = subCommentCnt - 1 WHERE commentNo = :commentNo")
    void minusSubCommentCnt(Long commentNo);

    // SecondaryTable 삭제 방지
    @Modifying
    @Query("UPDATE Comment SET rowStatCd = 'D' WHERE commentNo = :#{#comment.commentNo}")
    void delete(Comment comment);

}
