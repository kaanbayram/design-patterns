package com.example.patterns.outbox.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CompletedCheckoutConsumer {


    @KafkaListener(
            topics = "${spring.kafka.consumer.topic-name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(@Payload(required = false) String event, @Header(KafkaHeaders.RECEIVED_KEY) String key, Acknowledgment ack) {
        try {
            log.info(String.format("outbox message started to processing key %s", key));

        } catch (Exception e) {
            log.error(String.format("Error occurred while processing event key: %s", key));
        }
    }
}
