package com.benope.apple.batch.batch003;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.feed.repository.FeedSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.benope.apple.batch.batch003.Batch003Config.BATCH_ID;


@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = BATCH_ID)
@RequiredArgsConstructor
public class Batch003Tasklet implements Tasklet {

    private final FeedSolrRepository feedSolrRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        feedSolrRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
