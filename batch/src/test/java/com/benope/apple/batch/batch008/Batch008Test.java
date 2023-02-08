package com.benope.apple.batch.batch008;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.ranking.bean.FoodRanking;
import com.benope.apple.domain.ranking.repository.FoodRankingJpaRepository;
import com.benope.apple.utils.DateTimeUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(args = "--spring.batch.job.names=BATCH008")
@Sql("Batch008Test.sql")
public class Batch008Test extends AppleBatchTest {

    private final static LocalDate TODAY = LocalDate.of(2023, 1, 3);

    @Autowired
    private FoodRankingJpaRepository foodRankingJpaRepository;

    @Test
    public void 랭킹을_생성한다() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("dateTime", ZonedDateTime.of(TODAY, LocalTime.MIN, DateTimeUtil.ZONE_ID).toString())
                .toJobParameters();

        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        FoodRanking cond = FoodRanking.builder()
                .rankDate(TODAY)
                .build();
        Example<FoodRanking> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        List<FoodRanking> rankings = foodRankingJpaRepository.findAll(example);
        Assertions.assertThat(rankings.size()).isEqualTo(9);
    }

}
