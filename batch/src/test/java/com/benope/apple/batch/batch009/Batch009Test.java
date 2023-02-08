package com.benope.apple.batch.batch009;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import com.benope.apple.domain.theme.repository.ThemeFeedJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(args = "--spring.batch.job.names=BATCH009")
@Sql("Batch009Test.sql")
public class Batch009Test extends AppleBatchTest {

    @Autowired
    private ThemeFeedJpaRepository themeFeedJpaRepository;


    @Test
    public void 테마_피드_목록을_생성한다() throws Exception {
        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        List<ThemeFeed> themeFeeds = themeFeedJpaRepository.findAll();
        assertThat(themeFeeds.size()).isEqualTo(2);
    }

}
