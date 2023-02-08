package com.benope.apple.batch.batch007;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.food.repository.FoodJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(args = "--spring.batch.job.names=BATCH007")
@Sql("Batch007Test.sql")
public class Batch007Test extends AppleBatchTest {

    @Autowired
    private FoodJpaRepository foodJpaRepository;

    @Test
    public void 식품_DB_난수를_갱신한다() throws Exception {
        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        foodJpaRepository.findAll()
                .forEach(it -> assertThat(it.getRandNum()).isNotNull());
    }

}
