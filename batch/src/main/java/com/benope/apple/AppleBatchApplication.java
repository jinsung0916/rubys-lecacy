package com.benope.apple;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@EnableBatchProcessing
@SpringBootApplication
public class AppleBatchApplication extends DefaultBatchConfigurer {

    public static void main(String[] args) {
        int exitCode = SpringApplication.exit(SpringApplication.run(AppleBatchApplication.class, args));
        System.exit(exitCode);
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        // Do nothing
    }
}
