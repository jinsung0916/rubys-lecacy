package com.benope.apple.admin.comment.bean;

import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.domain.food.bean.Food;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCommentRequestV2 implements IdRequest {

    private List<Long> id;

    private Long mbNo;
    private Long foodNo;
    private String brand;

    public FoodComment toEntity() {
        return FoodComment.builder()
                .mbNo(mbNo)
                .foodNo(foodNo)
                .food(
                        Food.builder()
                                .brand(brand)
                                .build()
                )
                .build();
    }

}
