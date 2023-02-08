package com.benope.apple.batch.batch011;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.ranking.repository.RankingJpaRepository;
import com.benope.apple.utils.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(args = "--spring.batch.job.names=BATCH011")
@Sql("Batch011Test.sql")
public class Batch011Test extends AppleBatchTest {

    private final static LocalDate TODAY = LocalDate.of(2023, 1, 22);

    @Autowired
    private RankingJpaRepository rankingJpaRepository;

    @Test
    public void 만료된_데이터를_삭제한다() throws Exception {
        //Given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("dateTime", ZonedDateTime.of(TODAY, LocalTime.MIN, DateTimeUtil.ZONE_ID).toString())
                .toJobParameters();

        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        assertThat(rankingJpaRepository.count()).isEqualTo(1);
    }

}
