package com.benope.apple.batch.batch005;

import com.benope.apple.batch.AppleBatchTest;
import com.benope.apple.domain.member.bean.MemberSolrEntity;
import com.benope.apple.domain.member.repository.MemberSolrRepository;
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

@SpringBootTest(args = "--spring.batch.job.names=BATCH005")
@Sql("Batch005Test.sql")
public class Batch005Test extends AppleBatchTest {

    @MockBean
    private MemberSolrRepository memberSolrRepository;

    @Test
    public void 회원을_검색_엔진과_동기화한다() throws Exception {
        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        then(memberSolrRepository).should(atLeastOnce()).deleteAll();
        then(memberSolrRepository).should(atMostOnce()).save(any(MemberSolrEntity.class));
    }

}
