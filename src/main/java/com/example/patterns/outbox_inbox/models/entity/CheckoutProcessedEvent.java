package com.example.patterns.outbox_inbox.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Getter
@Setter
@Document(collection = "checkout_processed_events")
public class CheckoutProcessedEvent extends BaseDocument {
}
