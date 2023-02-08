package com.benope.apple.admin.score.bean;

import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreResponse {

    private Long id;
    private Long mbNo;
    private Long foodNo;
    private BigDecimal score;
    private ZonedDateTime createdAtOrUpdatedAt;

    public static ScoreResponse fromEntity(Score entity) {
        return ScoreResponse.builder()
                .id(entity.getScoreNo())
                .mbNo(entity.getMbNo())
                .foodNo(entity.getFoodNo())
                .score(entity.getScoreValue())
                .createdAtOrUpdatedAt(ZonedDateTime.of(entity.getCreatedAtOrUpdatedAt(), DateTimeUtil.ZONE_ID))
                .build();
    }

}
