package com.benope.apple.batch.batch002;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.food.repository.FoodSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.benope.apple.batch.batch002.Batch002Config.BATCH_ID;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = BATCH_ID)
@RequiredArgsConstructor
public class Batch002Tasklet implements Tasklet {

    private final FoodSolrRepository foodSolrRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        foodSolrRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }

}
