package com.aditya.tasktracker.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        // If DATABASE_URL is provided (Render PostgreSQL format)
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            // Convert postgres:// to jdbc:postgresql://
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
            
            HikariDataSource dataSource = DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .url(databaseUrl)
                .driverClassName("org.postgresql.Driver")
                .build();
            
            return dataSource;
        }
        
        // Otherwise use Spring Boot's default DataSource configuration
        // (reads from application.properties for local MySQL)
        return DataSourceBuilder
            .create()
            .type(HikariDataSource.class)
            .build();
    }
}
