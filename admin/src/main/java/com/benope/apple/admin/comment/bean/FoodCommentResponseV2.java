package com.benope.apple.admin.comment.bean;

import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCommentResponseV2 {

    private Long id;
    private Long mbNo;
    private Long foodNo;
    private String contents;
    private ZonedDateTime createdAtOrUpdatedAt;

    public static FoodCommentResponseV2 fromEntity(FoodComment entity) {
        return FoodCommentResponseV2.builder()
                .id(entity.getCommentNo())
                .mbNo(entity.getMbNo())
                .foodNo(entity.getFoodNo())
                .contents(entity.getContents())
                .createdAtOrUpdatedAt(ZonedDateTime.of(entity.getCreatedAtOrUpdatedAt(), DateTimeUtil.ZONE_ID))
                .build();
    }

}
