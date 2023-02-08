package com.benope.apple.domain.ranking.bean;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode // https://stackoverflow.com/questions/47285684/hibernate-springdata-incorrect-dirty-check-on-field-with-attributeconverter
public class FoodRankingData {

    private BigDecimal totalScore;
    private Long scoreCount;

    private Long dailyViewCount;
    private Long dailyScoreCount;
    private Long dailyCommentCount;
    private Long dailyTagCount;

    public BigDecimal toAverageScore() {
        if (Objects.requireNonNullElse(scoreCount, 0L) == 0) {
            return null;
        }

        return totalScore.divide(BigDecimal.valueOf(scoreCount), 1, RoundingMode.HALF_UP)
                .stripTrailingZeros();
    }

}
