package com.benope.apple.domain.feed.bean;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode // https://stackoverflow.com/questions/47285684/hibernate-springdata-incorrect-dirty-check-on-field-with-attributeconverter
public class UploadImgFoodRelation {

    private Long foodNo;
    private Double userInput;

    protected TaggedFood toTaggedFood() {
        return TaggedFood.builder()
                .foodNo(foodNo)
                .userInput(userInput)
                .build();
    }

}
