package com.benope.apple.batch.batch011;

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
 * BATCH011
 * <p>
 * Cleanup
 */
@Configuration
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch011Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch011Config {

    public static final String BATCH_ID = "BATCH011";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final PlatformTransactionManager transactionManager;

    private final Batch011Tasklet batch011Tasklet;

    @Bean
    public Job batch009() {
        return jobBuilderFactory.get(BATCH_ID)
                .incrementer(new UniqueRunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(batch011Tasklet)
                .transactionManager(transactionManager)
                .build();
    }

}
