package com.benope.apple.api.food.bean;

import com.benope.apple.domain.food.bean.FoodTag;
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
public class FoodTagRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    @NotNull
    private Long searchMbNo;

    public FoodTag toEntity() {
        return FoodTag.builder()
                .mbNo(searchMbNo)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo -1 , recordsPerPage, Sort.by(Sort.Order.desc("foodTagNo")));
    }

}
