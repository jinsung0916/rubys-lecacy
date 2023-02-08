package com.benope.apple.api.score.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodScoreRequest {

    @NotNull
    private Long foodNo;
    @NotNull(groups = Create.class)
    @DecimalMin("0.5")
    @DecimalMax("5")
    private BigDecimal score;

    public Score toEntity() {
        return Score.builder()
                .mbNo(SessionUtil.getSessionMbNo())
                .foodNo(foodNo)
                .scoreValue(score)
                .build();
    }

}
