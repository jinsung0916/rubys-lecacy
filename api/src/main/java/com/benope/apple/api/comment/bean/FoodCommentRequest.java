package com.benope.apple.api.comment.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.utils.SessionUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCommentRequest {

    @NotNull(groups = {Update.class, Delete.class})
    private Long commentNo;
    @NotNull(groups = Create.class)
    private Long foodNo;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String contents;

    public FoodComment toEntity() {
        return FoodComment.builder()
                .commentNo(commentNo)
                .mbNo(SessionUtil.getSessionMbNo())
                .foodNo(foodNo)
                .contents(contents)
                .reorderNo(0)
                .depth(Byte.valueOf("0"))
                .build();
    }

}
