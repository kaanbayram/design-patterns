package com.example.patterns.outbox.consumer;

import com.example.patterns.outbox.processor.CompletedCheckoutProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@AllArgsConstructor
public class CompletedCheckoutConsumer {

    private final CompletedCheckoutProcessor completedCheckoutProcessor;

    @KafkaListener(
            topics = "${spring.kafka.outbox.consumer.topic-name}",
            groupId = "${spring.kafka.outbox.consumer.group-id}"
    )
    public void consume(
            @Payload(required = false) String event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            Acknowledgment ack) {
        try {
            log.info("Outbox event received — key: {}, payload: {}", key, event);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing outbox event key: {}", key, e);
        }
    }
}
