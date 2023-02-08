package com.benope.apple.batch.batch008;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.UniqueRunIdIncrementer;
import com.benope.apple.domain.category.bean.RankingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;


/**
 * BATCH008
 * <p>
 * 랭킹을 생성한다.
 */
@Configuration
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch008Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch008Config {

    public static final String BATCH_ID = "BATCH008";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;

    private final Batch008StepOneTasklet batch008StepOneTasklet;
    private final Batch008StepTwoProcessor batch008StepTwoProcessor;

    @Bean
    public Job batch008() {
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
                .tasklet(batch008StepOneTasklet)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<RankingCategory, RankingCategory>chunk(1)
                .reader(reader())
                .processor(batch008StepTwoProcessor)
                .writer(it -> {
                    // bypass
                })
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<RankingCategory> reader() {
        return new JpaPagingItemReaderBuilder<RankingCategory>()
                .queryString("SELECT rc FROM RankingCategory rc ORDER BY rc.categoryNo")
                .pageSize(1)
                .entityManagerFactory(entityManagerFactory)
                .saveState(false)
                .build();
    }

}
