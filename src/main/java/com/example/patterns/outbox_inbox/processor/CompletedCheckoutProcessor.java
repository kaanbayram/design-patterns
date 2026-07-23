package com.example.patterns.outbox_inbox.processor;

import com.example.patterns.outbox_inbox.models.KafkaEvent;
import com.example.patterns.outbox_inbox.models.entity.CheckoutOutboxEvent;
import com.example.patterns.outbox_inbox.models.event.CheckoutEvent;
import com.example.patterns.outbox_inbox.service.CompletedCheckoutService;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class CompletedCheckoutProcessor extends Processor<CheckoutEvent> {

    private final CompletedCheckoutService completedCheckoutService;

    private ObjectMapper objectMapper;

    public CompletedCheckoutProcessor(CompletedCheckoutService completedCheckoutService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.completedCheckoutService = completedCheckoutService;
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
        completedCheckoutService.sendOutboxRecord(event);
    }

    @Override
    public void handleEvent(String event) {
        var checkoutEvent = convertToObject(event);
        processEvent(checkoutEvent);
    }
}
