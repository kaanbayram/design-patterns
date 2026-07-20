package com.example.patterns;

import org.springframework.boot.SpringApplication;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class DevApplication {

    private static final KafkaContainer KAFKA =
            new KafkaContainer(
                    DockerImageName.parse("apache/kafka-native:3.9.1")
            );

    public static void main(String[] args) {
        KAFKA.start();

        System.setProperty(
                "spring.kafka.bootstrap-servers",
                KAFKA.getBootstrapServers()
        );

        SpringApplication.from(PatternsApplication::main)
                .with(DevContainersConfiguration.class)
                .run(args);
    }
}
