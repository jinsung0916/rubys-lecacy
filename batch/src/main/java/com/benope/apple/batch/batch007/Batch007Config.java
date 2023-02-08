package com.benope.apple.batch.batch007;

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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;


/**
 * BATCH007
 * <p>
 * 식품 DB 난수를 갱신한다.
 */
@Configuration
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch007Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch007Config {

    public static final String BATCH_ID = "BATCH007";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;

    private final Batch007Processor processor;

    @Bean
    public Job batch007() {
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
                .<Food, Food>chunk(100)
                .reader(reader())
                .processor(processor)
                .writer(writer())
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
                .saveState(false)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<Food> writer() {
        return new JpaItemWriterBuilder<Food>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
