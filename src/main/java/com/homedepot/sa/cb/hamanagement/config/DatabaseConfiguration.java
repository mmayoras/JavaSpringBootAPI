package com.homedepot.sa.cb.hamanagement.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * Created by n84qvs on 1/16/17.
 */

@Configuration
public class DatabaseConfiguration {


    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.maximumPoolSize}")
    private int maxPoolSize;
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;

    @Bean(destroyMethod = "close")
    public DataSource primaryDataSource(){

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(this.driverClassName);
        hikariDataSource.setJdbcUrl(this.url);
        hikariDataSource.setUsername(this.userName);
        hikariDataSource.setPassword(this.password);
        hikariDataSource.setMaximumPoolSize(this.maxPoolSize);

        hikariDataSource.setConnectionTestQuery(this.validationQuery);
        return hikariDataSource;
    }


}