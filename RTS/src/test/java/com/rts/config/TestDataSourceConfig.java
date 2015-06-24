package com.rts.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class TestDataSourceConfig {

    @Bean
    public DataSource dataSource(Environment e) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(e.getProperty("test.mysql.driver"));
        dataSource.setUrl(e.getProperty("test.mysql.url"));
        dataSource.setUsername(e.getProperty("test.mysql.username"));
        dataSource.setPassword(e.getProperty("test.mysql.password"));
        return dataSource;
    }
}
