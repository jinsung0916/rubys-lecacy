package com.benope.apple.batch.batch010;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.UniqueRunIdIncrementer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * BATCH010
 * <p>
 * 실시간 랭킹을 생성한다.
 */
@Configuration
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch010Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch010Config {

    public static final String BATCH_ID = "BATCH010";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final PlatformTransactionManager transactionManager;

    private final Batch010StepOneTasklet batch010StepOneTasklet;
    private final Batch010StepTwoTasklet batch010StepTwoTasklet;

    @Bean
    public Job batch010() {
        return jobBuilderFactory.get(BATCH_ID)
                .incrementer(new UniqueRunIdIncrementer())
                .flow(step1())
                .next(step2())
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(batch010StepOneTasklet)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Step step2() {
        return stepBuilderFactory.get("step1")
                .tasklet(batch010StepTwoTasklet)
                .transactionManager(transactionManager)
                .build();
    }

}
