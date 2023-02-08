package com.benope.apple.config.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@EnableJpaRepositories(basePackages = "com.benope.apple", transactionManagerRef = "rubysTransactionManager")
@Configuration
@RequiredArgsConstructor
public class RubysDataSourceConfig {

    private final ApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
    public HikariConfig primaryHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource primaryDataSource() {
        return new HikariDataSource(primaryHikariConfig());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.replica")
    public HikariConfig replicaHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource replicaDataSource() {
        return new HikariDataSource(replicaHikariConfig());
    }

    @Bean
    public DataSource routingDataSource() {
        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(
                Map.of(
                        DatabaseType.PRIMARY, primaryDataSource(),
                        DatabaseType.REPLICA, replicaDataSource()
                )
        );
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource rubysDataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    @Primary
    public SqlSessionFactory rubysSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(rubysDataSource());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.benope.apple.**.bean");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    public JpaTransactionManager rubysTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
