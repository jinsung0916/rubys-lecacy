package com.benope.apple.domain.feed.repository;

import com.benope.apple.domain.feed.bean.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FeedJpaRepository extends JpaRepository<Feed, Long>, JpaSpecificationExecutor<Feed> {

    @Modifying
    @Query("UPDATE Feed SET viewCnt = viewCnt + 1 WHERE feedNo = :feedNo")
    void plusViewCnt(Long feedNo);

    @Modifying
    @Query("UPDATE Feed SET likeCnt = likeCnt + 1 WHERE feedNo = :feedNo")
    void plusLikeCnt(Long feedNo);

    @Modifying
    @Query("UPDATE Feed SET likeCnt = likeCnt - 1 WHERE feedNo = :feedNo")
    void minusLikeCnt(Long feedNo);

    @Modifying
    @Query("UPDATE Feed SET commentCnt = commentCnt + 1 WHERE feedNo = :feedNo")
    void plusCommentCnt(Long feedNo);

    @Modifying
    @Query("UPDATE Feed SET commentCnt = commentCnt - 1 WHERE feedNo = :feedNo")
    void minusCommentCnt(Long feedNo);

    @Modifying
    @Query("UPDATE Feed SET scrapCnt = scrapCnt + 1 WHERE feedNo = :feedNo")
    void plusScrapCnt(Long feedNo);

    @Modifying
    @Query("UPDATE Feed SET scrapCnt = scrapCnt - 1 WHERE feedNo = :feedNo")
    void minusScrapCnt(Long feedNo);

    // ElementCollection 삭제 방지
    @Modifying
    @Query("UPDATE Feed SET rowStatCd = 'D' WHERE feedNo = :#{#feed.feedNo}")
    void delete(Feed feed);

}
