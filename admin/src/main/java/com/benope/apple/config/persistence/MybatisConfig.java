package com.benope.apple.config.persistence;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class MybatisConfig {

    @Qualifier("rubysSqlSessionFactory")
    private final SqlSessionFactory sqlSessionFactory;

    @Bean
    @Primary
    public SqlSessionTemplate rubysSqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
