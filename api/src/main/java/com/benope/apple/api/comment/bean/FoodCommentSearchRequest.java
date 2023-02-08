package com.benope.apple.api.comment.bean;

import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.FoodComment;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCommentSearchRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    private Long foodNo;
    private Long searchMbNo;

    public FoodComment toEntity() {
        return FoodComment.builder()
                .foodNo(foodNo)
                .mbNo(searchMbNo)
                .parentCommentNo(Comment.INITIAL_PARENT_COMMENT_NO)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.desc("commentNo")));
    }

}
