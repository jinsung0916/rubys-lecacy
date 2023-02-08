package com.benope.apple.batch.batch010;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.DateTimeParams;
import com.benope.apple.domain.ranking.repository.FoodRealtimeRankingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch010Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch010StepOneTasklet implements Tasklet {

    private final DateTimeParams dateTimeParams;

    private final FoodRealtimeRankingJpaRepository foodRankingJpaRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        foodRankingJpaRepository.deleteByRankDate(dateTimeParams.getDate());

        return RepeatStatus.FINISHED;
    }

}
