package com.example.patterns;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebeziumConnectorInitializer {

    private final GenericContainer debeziumConnectContainer;
    private final MongoDBContainer mongoDBContainer;
    private final ObjectMapper objectMapper;

    @Order(1)
    @EventListener(ApplicationReadyEvent.class)
    public void registerConnector() throws Exception {
        String connectUrl = "http://"
                + debeziumConnectContainer.getHost()
                + ":"
                + debeziumConnectContainer.getMappedPort(8083)
                + "/connectors";

        Map<String, Object> body = Map.of(
                "name", "patterns-mongo-outbox-connector",
                "config", Map.ofEntries(
                        Map.entry(
                                "connector.class",
                                "io.debezium.connector.mongodb.MongoDbConnector"
                        ),
                        Map.entry("mongodb.connection.string", "mongodb://mongo:27017/?replicaSet=rs0"),
                        Map.entry("topic.prefix", "cdc"),
                        Map.entry("collection.include.list", "test.outbox_checkout"),
                        Map.entry("snapshot.mode", "no_data"),
                        Map.entry("tombstones.on.delete", "false"),
                        Map.entry("capture.mode", "change_streams_update_full"),
                        Map.entry("tasks.max", "1"),
                        Map.entry("include.schema.changes", "true"),
                        Map.entry("mongodb.members.auto.discover", "true"),
                        Map.entry("key.converter", "org.apache.kafka.connect.json.JsonConverter"),
                        Map.entry("key.converter.schemas.enable", "false"),
                        Map.entry("value.converter", "org.apache.kafka.connect.json.JsonConverter"),
                        Map.entry("value.converter.schemas.enable", "false"),
                        Map.entry("producer.override.max.request.size", "8388608"),
                        Map.entry("topic.creation.default.partitions", "-1"),
                        Map.entry("topic.creation.default.replication.factor", "-1")
                )
        );

        String json = objectMapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(connectUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() != 201 && response.statusCode() != 409) {
            throw new IllegalStateException(
                    "Debezium connector couldnt created. status="
                            + response.statusCode()
                            + ", body="
                            + response.body()
            );
        }

        waitForConnectorRunning(connectUrl);
    }

    private void waitForConnectorRunning(String connectorsUrl) throws Exception {
        String statusUrl = connectorsUrl + "/patterns-mongo-outbox-connector/status";
        HttpClient client = HttpClient.newHttpClient();

        for (int i = 0; i < 30; i++) {
            HttpRequest statusRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statusUrl))
                    .GET()
                    .build();

            HttpResponse<String> statusResponse = client.send(statusRequest, HttpResponse.BodyHandlers.ofString());
            String body = statusResponse.body();

            if (body.contains("\"state\":\"RUNNING\"")) {
                log.info("Debezium connector is RUNNING, change stream active");
                return;
            }

            log.info("Waiting for Debezium connector to reach RUNNING state... (attempt {})", i + 1);
            Thread.sleep(1000);
        }

        throw new IllegalStateException("Debezium connector did not reach RUNNING state in time");
    }


}
