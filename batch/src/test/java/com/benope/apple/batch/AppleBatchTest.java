package com.benope.apple.batch;

import com.benope.apple.config.ElasticsearchConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBatchTest
@Import(ElasticsearchConfig.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppleBatchTest {

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    @AfterEach
    public void cleanUp() {
        // 동일 배치 테스트를 여러 번 수행 가능하도록 컨텍스트를 초기화한다.
        jobRepositoryTestUtils.removeJobExecutions();
    }

}
