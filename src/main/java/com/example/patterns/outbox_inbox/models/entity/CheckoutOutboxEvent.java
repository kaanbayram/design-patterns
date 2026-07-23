package com.example.patterns.outbox_inbox.models.entity;

import com.example.patterns.outbox_inbox.models.enums.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "outbox_checkout")
public class CheckoutOutboxEvent extends BaseDocument {
    private String aggregateId;
    private String aggregateType;
    private String eventType;
    private String payload;
    private OutboxStatus status;
}
