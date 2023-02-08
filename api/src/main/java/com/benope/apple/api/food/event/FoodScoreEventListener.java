package com.benope.apple.api.food.event;

import com.benope.apple.api.food.service.FoodService;
import com.benope.apple.api.score.service.ScoreHelperService;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreSummaryView;
import com.benope.apple.domain.score.event.ScoreEvent;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FoodScoreEventListener {

    private final ScoreHelperService scoreHelperService;
    private final FoodService foodService;

    private final EntityManagerWrapper em;

    @EventListener
    @Transactional
    public void handleEvent(ScoreEvent e) {
        Score score = e.getSource();
        Long foodNo = score.getFoodNo();

        ScoreSummaryView summary = scoreHelperService.getSummary(
                Score.builder()
                        .foodNo(foodNo)
                        .build()
        );

        Food food = Food.builder()
                .foodNo(foodNo)
                .totalScore(summary.getTotalScore())
                .scoreCount(summary.getScoreCount())
                .build();

        foodService.update(food);
    }

}
