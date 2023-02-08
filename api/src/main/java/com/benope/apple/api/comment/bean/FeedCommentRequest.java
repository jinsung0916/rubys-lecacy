package com.benope.apple.api.comment.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.domain.comment.bean.FeedComment;
import com.benope.apple.utils.SessionUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedCommentRequest {

    @NotNull(groups = {Update.class, Delete.class})
    private Long commentNo;
    @NotNull(groups = Create.class)
    private Long feedNo;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String contents;
    private Long parentCommentNo;
    @NotNull(groups = Create.class)
    @Min(0)
    private Integer reorderNo;
    @NotNull(groups = Create.class)
    @Min(0)
    private Byte depth;

    public FeedComment toEntity() {
        return FeedComment.builder()
                .commentNo(commentNo)
                .mbNo(SessionUtil.getSessionMbNo())
                .feedNo(feedNo)
                .contents(contents)
                .parentCommentNo(parentCommentNo)
                .reorderNo(reorderNo)
                .depth(depth)
                .build();
    }

}
