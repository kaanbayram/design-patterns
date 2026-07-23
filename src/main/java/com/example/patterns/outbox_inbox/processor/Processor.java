package com.example.patterns.outbox_inbox.processor;

import com.example.patterns.outbox_inbox.models.KafkaEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@AllArgsConstructor
public abstract class Processor<T> {

    private final ObjectMapper objectMapper;

    protected abstract T convertToObject(String event);

    protected abstract void processEvent(T event);

    protected KafkaEvent convertToEventObject(String kafkaEvent) {
        return objectMapper.readValue(kafkaEvent, KafkaEvent.class);
    }

    protected abstract void handleEvent(String event);
}
