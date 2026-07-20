package com.example.patterns;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

@TestConfiguration(proxyBeanMethods = false)
public class DevContainersConfiguration {

    static final Network NETWORK = Network.newNetwork();

    @Bean
    KafkaContainer kafkaContainer() {
        KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.9.1"))
                .withNetwork(NETWORK)
                .withNetworkAliases("kafka");

        kafka.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
        return kafka;
    }

    @Bean
    Network testcontainersNetwork() {
        return NETWORK;
    }

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgres() {
        return new PostgreSQLContainer("postgres:16-alpine").withNetwork(NETWORK);
    }

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer("mongo:7").withNetwork(NETWORK).withNetworkAliases("mongo");
    }

    @Bean
    GenericContainer debeziumConnectContainer(
            KafkaContainer kafkaContainer,
            MongoDBContainer mongoDBContainer
    ) {
        return new GenericContainer<>(DockerImageName.parse("quay.io/debezium/connect:3.6"))
                .withNetwork(NETWORK)
                .withExposedPorts(8083)
                .withEnv("BOOTSTRAP_SERVERS", "kafka:9092")
                .withEnv("GROUP_ID", "patterns-debezium-connect")
                .withEnv("CONFIG_STORAGE_TOPIC", "patterns-connect-configs")
                .withEnv("OFFSET_STORAGE_TOPIC", "patterns-connect-offsets")
                .withEnv("STATUS_STORAGE_TOPIC", "patterns-connect-status")
                .withEnv("CONFIG_STORAGE_REPLICATION_FACTOR", "1")
                .withEnv("OFFSET_STORAGE_REPLICATION_FACTOR", "1")
                .withEnv("STATUS_STORAGE_REPLICATION_FACTOR", "1")
                .dependsOn(kafkaContainer, mongoDBContainer)
                .waitingFor(
                        Wait.forHttp("/connector-plugins")
                                .forPort(8083)
                                .forStatusCode(200)
                                .withStartupTimeout(Duration.ofMinutes(3))
                );
    }
}
