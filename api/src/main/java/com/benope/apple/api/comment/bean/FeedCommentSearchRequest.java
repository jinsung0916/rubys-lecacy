package com.benope.apple.api.comment.bean;

import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.FeedComment;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedCommentSearchRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;
    @NotNull
    private Long feedNo;
    private Long parentCommentNo;

    public FeedComment toEntity() {
        return FeedComment.builder()
                .feedNo(feedNo)
                .parentCommentNo(Objects.requireNonNullElse(parentCommentNo, Comment.INITIAL_PARENT_COMMENT_NO))
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.asc("commentNo")));
    }

}
