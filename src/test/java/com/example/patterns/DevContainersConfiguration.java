package com.example.patterns;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ContainerProperties;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;
import java.util.Map;

@TestConfiguration(proxyBeanMethods = false)
public class DevContainersConfiguration {

    static final Network NETWORK = Network.newNetwork();

    @Bean
    KafkaContainer kafkaContainer() {
        KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.9.1"))
                .withNetwork(NETWORK)
                .withNetworkAliases("kafka")
                .withListener("kafka:19092");
        kafka.start();
        return kafka;
    }

    @Bean
    ConsumerFactory<Object, Object> consumerFactory(KafkaContainer kafkaContainer) {
        return new DefaultKafkaConsumerFactory<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false
        ));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConsumerFactory<Object, Object> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    KafkaAdmin kafkaAdmin(KafkaContainer kafkaContainer) {
        return new KafkaAdmin(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers()
        ));
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
        MongoDBContainer mongo = new MongoDBContainer("mongo:7")
                .withNetwork(NETWORK)
                .withNetworkAliases("mongo")
                .withCommand("--replSet rs0 --bind_ip_all");
        mongo.start();
        try {
            var result = mongo.execInContainer("mongosh", "--quiet", "--eval",
                    "rs.initiate({_id: 'rs0', members: [{_id: 0, host: 'mongo:27017'}]}); " +
                    "var attempts = 0; " +
                    "while (rs.status().myState != 1 && attempts < 30) { sleep(1000); attempts++; } " +
                    "print('final state: ' + rs.status().myState);");
            System.out.println("MongoDB replica set init: " + result.getStdout());
            if (result.getExitCode() != 0) {
                throw new RuntimeException("MongoDB init failed: " + result.getStderr());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize MongoDB replica set", e);
        }
        return mongo;
    }

    @Bean
    GenericContainer debeziumConnectContainer(
            KafkaContainer kafkaContainer,
            MongoDBContainer mongoDBContainer
    ) {
        return new GenericContainer<>(DockerImageName.parse("quay.io/debezium/connect:3.6"))
                .withNetwork(NETWORK)
                .withExposedPorts(8083)
                .withEnv("BOOTSTRAP_SERVERS", "kafka:19092")
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
