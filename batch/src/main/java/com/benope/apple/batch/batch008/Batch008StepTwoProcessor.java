package com.benope.apple.batch.batch008;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.DateTimeParams;
import com.benope.apple.domain.category.bean.RankingCategory;
import com.benope.apple.domain.ranking.bean.FoodRanking;
import com.benope.apple.domain.ranking.bean.Ranking;
import com.benope.apple.domain.ranking.mapper.RankingMapper;
import com.benope.apple.domain.ranking.repository.FoodRankingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch008Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch008StepTwoProcessor implements ItemProcessor<RankingCategory, RankingCategory> {

    private final DateTimeParams dateTimeParams;

    private final FoodRankingJpaRepository foodRankingJpaRepository;
    private final RankingMapper rankingMapper;

    @Override
    public RankingCategory process(RankingCategory item) throws Exception {
        FoodRanking cond = FoodRanking.builder()
                .categoryNo(item.getCategoryNo())
                .rankDate(dateTimeParams.getDate())
                .build();

        List<FoodRanking> foodRankings = rankingMapper.generateFoodRanking(cond);

        setRankNum(foodRankings);

        foodRankingJpaRepository.saveAll(foodRankings);

        return null;
    }

    private void setRankNum(List<? extends Ranking> rankings) {
        for (int i = 0; i < rankings.size(); i++) {
            Ranking ranking = rankings.get(i);
            ranking.setRankNum((long) i + 1);
        }
    }

}
