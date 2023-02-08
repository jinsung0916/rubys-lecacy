package com.benope.apple.batch.batch002;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.UniqueRunIdIncrementer;
import com.benope.apple.domain.food.bean.Food;
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
 * BATCH002
 * <p>
 * 식품 DB 를 검색 엔진 동기화한다.
 */
@Configuration
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch002Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch002Config {

    public static final String BATCH_ID = "BATCH002";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;

    private final Batch002Tasklet tasklet;
    private final Batch002Writer writer;

    @Bean
    public Job batch002() {
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
                .tasklet(tasklet)
                .build();
    }

    @Bean
    @JobScope
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<Food, Food>chunk(100)
                .reader(reader())
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Food> reader() {
        return new JpaPagingItemReaderBuilder<Food>()
                .queryString("SELECT f FROM Food f ORDER BY f.foodNo")
                .pageSize(100)
                .entityManagerFactory(entityManagerFactory)
                .name("reader")
                .build();
    }

}
