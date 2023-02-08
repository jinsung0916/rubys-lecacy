package com.benope.apple.batch.batch002;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.benope.apple.domain.food.repository.FoodSolrRepository;
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

@SpringBootTest(args = "--spring.batch.job.names=BATCH002")
@Sql("Batch002Test.sql")
public class Batch002Test extends AppleBatchTest {

    @MockBean
    private FoodSolrRepository foodSolrRepository;

    @Test
    public void 식품_DB_를_검색_엔진과_동기화한다() throws Exception {
        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        then(foodSolrRepository).should(atLeastOnce()).deleteAll(); // TODO 테스트 환경에서 tasklet 이 두 번 호출되는 원인 찾기
        then(foodSolrRepository).should(atMostOnce()).save(any(FoodSolrEntity.class));
    }

}
