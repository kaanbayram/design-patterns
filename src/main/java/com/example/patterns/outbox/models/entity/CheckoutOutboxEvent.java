package com.example.patterns.outbox.models.entity;

import com.example.patterns.outbox.models.enums.OutboxStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@Document(collection = "outbox_checkout")
public class CheckoutOutboxEvent extends BaseDocument {
    private String aggregateId;
    private String aggregateType;
    private String eventType;
    private String payload;
    private OutboxStatus status;
}
