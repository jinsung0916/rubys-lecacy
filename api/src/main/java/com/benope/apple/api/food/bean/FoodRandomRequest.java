package com.benope.apple.api.food.bean;

import com.benope.apple.domain.food.bean.Food;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRandomRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    @NotNull
    private Long foodCategoryNo;
    @NotNull
    @Min(0)
    @Max(1000)
    private Integer randNum;

    public Food toEntity() {
        return Food.builder()
                .foodCategoryNo(foodCategoryNo)
                .randNum(randNum)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage);
    }

}
