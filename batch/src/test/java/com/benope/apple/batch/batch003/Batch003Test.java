package com.benope.apple.batch.batch003;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.repository.FeedSolrRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;

@SpringBootTest(args = "--spring.batch.job.names=BATCH003")
@Sql("Batch003Test.sql")
public class Batch003Test extends AppleBatchTest {

    @MockBean
    private FeedSolrRepository feedSolrRepository;

    @Test
    public void 피드를_검색_엔진과_동기화한다() throws Exception {
        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        then(feedSolrRepository).should(atLeastOnce()).deleteAll();
        then(feedSolrRepository).should(atMostOnce()).save(any(FeedSolrEntity.class));
    }

}
