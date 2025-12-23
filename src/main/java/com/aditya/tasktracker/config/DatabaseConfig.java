package com.aditya.tasktracker.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        // If DATABASE_URL is provided (Render PostgreSQL format)
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            // Convert postgres:// to jdbc:postgresql://
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
            
            return DataSourceBuilder
                .create()
                .url(databaseUrl)
                .build();
        }
        
        // Otherwise use Spring Boot's default DataSource configuration
        // (reads from application.properties for local MySQL)
        return DataSourceBuilder
            .create()
            .url(System.getProperty("spring.datasource.url", 
                 System.getenv("SPRING_DATASOURCE_URL") != null ? 
                 System.getenv("SPRING_DATASOURCE_URL") : 
                 "jdbc:mysql://localhost:3306/tasktracker?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"))
            .username(System.getProperty("spring.datasource.username", 
                     System.getenv("SPRING_DATASOURCE_USERNAME") != null ? 
                     System.getenv("SPRING_DATASOURCE_USERNAME") : "root"))
            .password(System.getProperty("spring.datasource.password", 
                     System.getenv("SPRING_DATASOURCE_PASSWORD") != null ? 
                     System.getenv("SPRING_DATASOURCE_PASSWORD") : "Adit@2004"))
            .build();
    }
}
