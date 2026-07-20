package com.example.patterns;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class DevContainersConfiguration {
    @Bean
    @ServiceConnection
    PostgreSQLContainer postgres() {
        return new PostgreSQLContainer("postgres:16-alpine");
    }

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer("mongo:7");
    }
}
