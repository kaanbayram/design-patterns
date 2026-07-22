package com.example.patterns.outbox.processor;

import com.example.patterns.outbox.models.KafkaEvent;
import com.example.patterns.outbox.models.dto.CheckoutDto;
import com.example.patterns.outbox.models.entity.Checkout;
import com.example.patterns.outbox.models.entity.CheckoutOutboxEvent;
import com.example.patterns.outbox.models.event.CheckoutEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class CompletedCheckoutProcessor extends Processor<CheckoutEvent> {

    private ObjectMapper objectMapper;

    public CompletedCheckoutProcessor(ObjectMapper objectMapper) {
        super(objectMapper);
        this.objectMapper = objectMapper;
    }

    @Override
    protected CheckoutEvent convertToObject(String event) {
        KafkaEvent eventObject = this.convertToEventObject(event);
        CheckoutOutboxEvent outboxEvent = objectMapper.readValue(eventObject.getAfter(), CheckoutOutboxEvent.class);
        return objectMapper.readValue(outboxEvent.getPayload(), CheckoutEvent.class);
    }

    @Override
    protected void processEvent(CheckoutEvent event) {

    }
}
