package com.benope.apple.batch.batch009;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.UniqueRunIdIncrementer;
import com.benope.apple.domain.theme.bean.Theme;
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
 * BATCH009
 * <p>
 * 테마 피드 목록을 생성한다.
 */
@Configuration
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch009Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch009Config {

    public static final String BATCH_ID = "BATCH009";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;

    private final Batch009Processor batch009Processor;

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
                .<Theme, Theme>chunk(10)
                .reader(reader())
                .processor(batch009Processor)
                .writer(it -> {
                    // bypass
                })
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Theme> reader() {
        return new JpaPagingItemReaderBuilder<Theme>()
                .queryString("SELECT t FROM Theme t ORDER BY t.themeNo")
                .pageSize(10)
                .entityManagerFactory(entityManagerFactory)
                .saveState(false)
                .build();
    }

}
