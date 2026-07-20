package com.example.patterns;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DebeziumConnectorInitializer {

    private final GenericContainer debeziumConnectContainer;
    private final MongoDBContainer mongoDBContainer;
    private final ObjectMapper objectMapper;

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
                        Map.entry("topic.prefix", "patterns"),
                        Map.entry(
                                "collection.include.list",
                                "patterns.outbox_orders"
                        ),
                        Map.entry("snapshot.mode", "initial"),
                        Map.entry("tombstones.on.delete", "false")
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

    }


}
