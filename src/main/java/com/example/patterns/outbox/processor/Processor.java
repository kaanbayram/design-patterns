package com.example.patterns.outbox.processor;

import com.example.patterns.outbox.models.KafkaEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
}
