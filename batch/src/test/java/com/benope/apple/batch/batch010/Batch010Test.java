package com.benope.apple.batch.batch010;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.food.bean.FoodAccessDocument;
import com.benope.apple.domain.food.repository.FoodAccessSearchRepository;
import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import com.benope.apple.domain.ranking.repository.FoodRealtimeRankingJpaRepository;
import com.benope.apple.utils.DateTimeUtil;
import org.junit.jupiter.api.BeforeAll;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(args = "--spring.batch.job.names=BATCH010")
@Sql("Batch010Test.sql")
public class Batch010Test extends AppleBatchTest {

    private final static LocalDate TODAY = LocalDate.of(2023, 1, 3);
    private final static LocalDate YESTERDAY = TODAY.minusDays(1);

    @Autowired
    private FoodRealtimeRankingJpaRepository foodRealtimeRankingJpaRepository;

    @BeforeAll
    public static void setUp(
            @Autowired FoodAccessSearchRepository foodAccessSearchRepository
    ) {
        foodAccessSearchRepository.save(
                FoodAccessDocument.builder()
                        .foodNo(1L)
                        .createdAt(YESTERDAY.atTime(LocalTime.MIN))
                        .build()
        );
    }

    @Test
    public void 실시간_랭킹을_생성한다() throws Exception {
        //Given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("dateTime", ZonedDateTime.of(TODAY, LocalTime.MIN, DateTimeUtil.ZONE_ID).toString())
                .toJobParameters();

        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        FoodRealtimeRanking cond = FoodRealtimeRanking.builder()
                .rankDate(TODAY)
                .build();
        Example<FoodRealtimeRanking> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        List<FoodRealtimeRanking> rankings = foodRealtimeRankingJpaRepository.findAll(example);
        assertThat(rankings.size()).isEqualTo(3);
    }

}
