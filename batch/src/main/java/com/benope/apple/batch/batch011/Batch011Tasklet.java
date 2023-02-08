package com.benope.apple.batch.batch011;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.DateTimeParams;
import com.benope.apple.domain.ranking.repository.RankingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch011Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch011Tasklet implements Tasklet {

    private final DateTimeParams params;

    private final RankingJpaRepository rankingJpaRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LocalDate monthAgo = params.getDate().minusMonths(1);

        // 만료된 랭킹 데이터 삭제
        rankingJpaRepository.deleteByRankDateLessThan(monthAgo);

        return RepeatStatus.FINISHED;
    }

}
