package com.apirest.finanzaspersonales.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {
    private static final Logger log = LogManager.getLogger(DataSourceConfig.class);
    @Value("${app.datasource.mode}")

    private String mode;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        if ("file".equalsIgnoreCase(mode)) {
            log.info("Usando H2 en archivo...");
            dataSource.setUrl("jdbc:h2:file:./data/database;AUTO_SERVER=TRUE");
        } else {
            log.info("Usando H2 en memoria...");
            dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        }

        return dataSource;
    }

}
