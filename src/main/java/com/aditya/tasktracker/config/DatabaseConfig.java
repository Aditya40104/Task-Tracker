package com.aditya.tasktracker.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url:#{null}}")
    private String datasourceUrl;
    
    @Value("${spring.datasource.username:#{null}}")
    private String datasourceUsername;
    
    @Value("${spring.datasource.password:#{null}}")
    private String datasourcePassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        HikariConfig config = new HikariConfig();
        
        // If DATABASE_URL is provided (Render PostgreSQL format)
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            try {
                URI dbUri = new URI(databaseUrl);
                
                // Convert postgres:// to jdbc:postgresql://
                String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
                
                if (dbUri.getUserInfo() != null) {
                    String[] credentials = dbUri.getUserInfo().split(":");
                    config.setUsername(credentials[0]);
                    if (credentials.length > 1) {
                        config.setPassword(credentials[1]);
                    }
                }
                
                config.setJdbcUrl(jdbcUrl);
                config.setDriverClassName("org.postgresql.Driver");
                
                // Set Hibernate properties for PostgreSQL
                config.addDataSourceProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                
            } catch (URISyntaxException e) {
                throw new RuntimeException("Invalid DATABASE_URL format", e);
            }
        } else {
            // Use configuration from application.properties (local MySQL)
            config.setJdbcUrl(datasourceUrl);
            config.setUsername(datasourceUsername);
            config.setPassword(datasourcePassword);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        }
        
        return new HikariDataSource(config);
    }
}
